package com.telecom.controller;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.doubleThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.telecom.entity.DataInfo;
import com.telecom.entity.GetModel;
import com.telecom.entity.SvmModel_entity;
import com.telecom.service.DataService;
import com.telecom.service.ModelTrainService;
import com.telecom.utils.QResponseVo;
import com.telecom.utils.QString;
import com.telecom.utils.QResponseVo.CODE;

import svm.svm;
import svm.svm_model;
import svm.svm_node;
import svm.svm_parameter;
import svm.svm_problem;


@Controller
public class ModelController {
	
	
			@Autowired
			private  ModelTrainService modeltrainservise;
			
			@RequestMapping("/modelTrain")
			public String modelTrain() {
				System.out.println("hello");
				return "modelTrain";  
			}
			
			@RequestMapping("/showsvmmodel")
			@ResponseBody
			public QResponseVo<List<SvmModel_entity>> showsvmmodel(@RequestParam(value="current") int current,
					@RequestParam(value="limit") int limit,
					@RequestParam(value="equipId") String equipId){
				QResponseVo<List<SvmModel_entity>> result = new QResponseVo<List<SvmModel_entity>>();
				List<SvmModel_entity> datass = new ArrayList<>(); 

				List<GetModel> svmdatas = new ArrayList<>();

				svmdatas = modeltrainservise.Showsvmmodel(equipId, (current-1)*limit, limit);
				System.out.println(svmdatas);
				int count = modeltrainservise.svmmodelcount(equipId);
				System.out.println(count+"");
				datass = adapt(svmdatas);
				result.setData(datass);
				result.setCount(count);
				return result;
			}
			
			public List<SvmModel_entity> adapt(List<GetModel> svmdatas)
			{
				int i;
				List<SvmModel_entity> adaptdatas = new ArrayList<>();
				GetModel svmdata = new GetModel();
				SvmModel_entity datas = new SvmModel_entity();
				for(i = 0 ; i<svmdatas.size();i++)
				{
					svmdata = svmdatas.get(i);
					datas = svmonedata(svmdata);
					adaptdatas.add(datas);
					System.out.println(datas);
				}
				return adaptdatas;
				
			}
			
			public SvmModel_entity svmonedata(GetModel svmdata)
			{
				SvmModel_entity one = new SvmModel_entity();
				String temp = svmdata.getParam();
				String[] a = temp.split(",");
				one.setSvm_type(a[0]);
				one.setKernel_type(a[1]);
				one.setCache_size(a[2]);
				one.setEps(a[3]);
				one.setC(a[4]);
				one.setTime(a[5]);
				one.setAccuracy(a[6]);
				one.setAlgorithm_name("svm");
				one.setId(svmdata.getId());
				System.out.println(svmdata.getEquipid());
				String equipname = modeltrainservise.GetEquipName(svmdata.getEquipid());
				one.setEquip_name(equipname);
				System.out.println(one);
				//one.setEquip_name(equip_name);
				return one;
				
			}
			
			@RequestMapping("/SVM")
			@ResponseBody
			public QResponseVo<List<SvmModel_entity>> SVM(@RequestParam(value="dataSelect",defaultValue="1") String dataSelect,
				@RequestParam(value="SVM_TYPE",defaultValue="0") String svm_type,
				@RequestParam(value="KERNEL_TYPE",defaultValue="0") String svm_kernel,
				@RequestParam(value="CACHE_SIZE",defaultValue="100") String cache_size,
				@RequestParam(value="EPS",defaultValue="0.0001") String eps,
				@RequestParam(value="C",defaultValue="1.9") String c,
				@RequestParam(value="equipid",defaultValue="1") String equipid) {
				QResponseVo<List<SvmModel_entity>> result = new QResponseVo<List<SvmModel_entity>>();
				String svmmodelparam="";
				System.out.println(dataSelect);
				 List<Double> label = new ArrayList<Double>();
		    	  List<svm_node[]> nodeSet = new ArrayList<svm_node[]>();
		    	  getData(nodeSet, label, "E:\\eclipse-workspace\\project1\\file\\train.txt");
		    	 // getData(nodeSet, label, "E:\\eclipse-workspace\\project1\\project1\\file\\train.txt");
		    	 // getData(nodeSet, label,  "E:\\eclipse-workspace\\EnterpriseRating\\file\\train1.txt");
		    	  int dataRange=nodeSet.get(0).length;
		    	  svm_node[][] datas = new svm_node[nodeSet.size()][dataRange]; // 训练集的向量表
		    	  for (int i = 0; i < datas.length; i++) {
		    	   for (int j = 0; j < dataRange; j++) {
		    	    datas[i][j] = nodeSet.get(i)[j];
		    	   }
		    	  }

		    	  double[] lables = new double[label.size()]; // a,b 对应的lable
		    	  for (int i = 0; i < lables.length; i++) {
		    	   lables[i] = label.get(i);
		    	  }
		    	 
		    	// 定义svm_problem对象
		    	  svm_problem problem = new svm_problem();
		    	  problem.l = nodeSet.size(); // 向量个数
		    	  problem.x = datas; // 训练集向量表
		    	  problem.y = lables; // 对应的lable数组
		    	 
		    	// 定义svm_parameter对象
		    	  svm_parameter param = new svm_parameter();
		    	  switch(svm_type)
		    	  {
		    	  case "4":param.svm_type = svm_parameter.EPSILON_SVR;
		    	  		   svmmodelparam = svmmodelparam + "EPSILON_SVR,";break;
		    	  case "0" :param.svm_type = svm_parameter.C_SVC;
		    	  		   svmmodelparam = svmmodelparam + "C_SVC,";break;
		    	  case "1":param.svm_type = svm_parameter.NU_SVC;
		    	           svmmodelparam = svmmodelparam + "NU_SVC,";break;
		    	  case "2":param.svm_type = svm_parameter.ONE_CLASS;
		    	           svmmodelparam = svmmodelparam + "ONE_CLASS,";break;
		    	  case "3":param.svm_type = svm_parameter.NU_SVR;
		    	           svmmodelparam = svmmodelparam + "NU_SVR,";break;
		    	  }
		    	  
		    	  switch(svm_kernel)
		    	  {
		    	  case "0":param.kernel_type = svm_parameter.LINEAR;
		    	           svmmodelparam = svmmodelparam + "LINEAR,";break;
		    	  case "1" :param.kernel_type = svm_parameter.POLY;
		    	           svmmodelparam = svmmodelparam + "POLY,"; break;
		    	  case "2":param.kernel_type = svm_parameter.RBF;
		    	           svmmodelparam = svmmodelparam + "RBF,";break;
		    	  case "3":param.kernel_type = svm_parameter.SIGMOID;
		    	           svmmodelparam = svmmodelparam + "SIGMOID,";break;
		    	  }
		    	  
		    	  
		    	  param.cache_size = Double.parseDouble(cache_size);
		    	  
		
		    	  svmmodelparam = svmmodelparam + cache_size +",";
		    	 
		    	  param.eps = Double.parseDouble(eps); 
		    	  svmmodelparam = svmmodelparam +eps +",";
		    	  

		    	  param.C = Double.parseDouble(c);
		    	  svmmodelparam = svmmodelparam + c+",";

		    	//  System.out.println(param.svm_type+param.kernel_type+param.cache_size+param.C);
		    	// 训练SVM分类模型
		    	  System.out.println(svm.svm_check_parameter(problem, param));
		    	// 如果参数没有问题，则svm.svm_check_parameter()函数返回null,否则返回error描述。
		    	  long startTime=System.currentTimeMillis();
		    	  svm_model model = svm.svm_train(problem, param);
		    	  // svm.svm_train()训练出SVM分类模型
		    	  long endTime=System.currentTimeMillis();
		          float excTime=(float)(endTime-startTime)/1000;
		          System.out.println("执行时间："+excTime+"s");
		          svmmodelparam = svmmodelparam + Float.toString(excTime) + ",";
		    	  // 获取测试数据
		    	  List<Double> testlabel = new ArrayList<Double>();
		    	  List<svm_node[]> testnodeSet = new ArrayList<svm_node[]>();
		    	  getData(testnodeSet, testlabel, "E:\\eclipse-workspace\\project1\\file\\test.txt");
		    	//  getData(nodeSet, label, "E:\\eclipse-workspace\\project1\\project1\\file\\test.txt");
		    	// getData(testnodeSet, testlabel, "E:\\eclipse-workspace\\EnterpriseRating\\file\\test1.txt");
		    	  
		    	  svm_node[][] testdatas = new svm_node[testnodeSet.size()][dataRange]; // 训练集的向量表
		    	  for (int i = 0; i < testdatas.length; i++) {
		    	   for (int j = 0; j < dataRange; j++) {
		    	    testdatas[i][j] = testnodeSet.get(i)[j];
		    	   }
		    	  }
		    	  double[] testlables = new double[testlabel.size()]; // a,b 锟斤拷应锟斤拷lable
		    	  for (int i = 0; i < testlables.length; i++) {
		    	   testlables[i] = testlabel.get(i);
		    	  }
		    	 
		    	//用来标记当前读取数据的行数
		    	  double err = 0.0 ;
		    	  double count = 0 ;
		    	  for (int i = 0; i < testdatas.length; i++) {
		    	   int truevalue = (int) testlables[i];
		    	   System.out.print(truevalue + " ");
		    	   double predictValue = svm.svm_predict(model, testdatas[i]);
		    	   System.out.println(Math.round(predictValue));
		    	   if(Math.round(predictValue) != truevalue){
		    		   count++; 		   
		    	   }
		    	 //  err += Math.abs(predictValue - truevalue);
		    	   err=count/testdatas.length;
		    	  }
		    	  //System.out.println("err=" + err / datas.length);
		    	  System.out.println("err="+err);
		    	 double acc = 1 - err;
				//return "modelTrain";
					svmmodelparam = svmmodelparam + Double.toString(acc);
					modeltrainservise.insert(equipid,"svm",svmmodelparam);
					result.setCode(CODE.Success);
					return result;
				}
			
			 public static void getData(List<svm_node[]> nodeSet, List<Double> label, String filename) {
				 try { 
			    	   FileReader fr = new FileReader(new File(filename));
			    	   BufferedReader br = new BufferedReader(fr);
			    	   String line = null;
			    	   while ((line = br.readLine()) != null) {
			    		   String[] datas = line.split(",");
			    		   svm_node[] vector = new svm_node[datas.length - 1];
			    		   for (int i = 0; i < datas.length - 1; i++) {
			    			   svm_node node = new svm_node();
			    			   node.index = i + 1;
			    	     node.value = Double.parseDouble(datas[i]);
			    	     vector[i] = node;
			    	     }
			    		   nodeSet.add(vector);
			    		   double lablevalue = Double.parseDouble(datas[datas.length - 1]);
			    		   label.add(lablevalue);
			    		   }
			    	   } catch (Exception e) {
			    		   e.printStackTrace();
			    		   }

				 }

			
			@RequestMapping("/SoftMax")
			public void SoftMax()
			{
				System.out.println("run softmax");
				//return "modelTrain";
			}
			
			
			@RequestMapping("/getdataselect")
			@ResponseBody
			public QResponseVo<List<DataInfo>> getdataselect(@RequestParam(value="equipId") String equipId)
			{
				QResponseVo<List<DataInfo>> result = new QResponseVo<List<DataInfo>>();
				List<DataInfo> datas = new ArrayList<>();
				System.out.println(equipId+"111111111111111111111111111111");
				datas=modeltrainservise.getdataselect(equipId);
				result.setData(datas);
				return result;
			}
			
			@RequestMapping("/deleteModelData")
			@ResponseBody
			public QResponseVo<String> delete(@RequestParam(required=true,value="dataIds") String dataIds){
				QResponseVo<String> result = new QResponseVo<String>();
				String dataids[] = dataIds.split(",");
				try {
					for(String dataId : dataids) {
						if(!QString.IsNullOrEmpty(dataId))
							modeltrainservise.deletemodel(dataId);
					}
					result.setCode(CODE.Success);
				}catch(Throwable th) {
					result.setCode(CODE.Fail);
				}
				return result;
			}
			
			
}
