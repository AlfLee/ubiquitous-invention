package com.telecom.service.impl;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.telecom.entity.DataInfo;
import com.telecom.entity.GetModel;
import com.telecom.entity.SvmModel_entity;
import com.telecom.mapper.ModelTrainMapper;
import com.telecom.service.ModelTrainService;

import svm.svm;
import svm.svm_model;
import svm.svm_node;
import svm.svm_parameter;
import svm.svm_problem;


@Transactional //确保事物
@Service(value="ModelTrainServise")
public class ModelTrainServiceImpl implements ModelTrainService{

	@Autowired
	private ModelTrainMapper modeltrainmapper;
	@Override
	public List<GetModel> Showsvmmodel(String equipId, int current, int pagesize) {
		// TODO Auto-generated method stub
		List<String> ids = new ArrayList<>();
		List<String> ids1 = new ArrayList<>();
		ids1.add(equipId);
		List<String> ids2 = getAllChildren(ids1, ids);
		ids2.addAll(ids1);
		System.out.println(ids2);
		return modeltrainmapper.getmodelInfo(ids2, current,pagesize);
	}
	
	
	public List<String> getAllChildren(List<String> ids1, List<String> ids) {
		List<String> newIds = modeltrainmapper.getAllChildren(ids1);
		if(!newIds.isEmpty()) {
			ids.addAll(newIds);
			getAllChildren(newIds,ids);
		}
		
		return ids;
	}

/*	public List<String> getsvmChildren(List<String> ids1)
	{
		List<String> newIds = modeltrainmapper.getsvmChildren(ids1);
		return newIds;
	}*/
	@Override
	public int svmmodelcount(String equipId) {
		// TODO Auto-generated method stub
		List<String> ids = new ArrayList<>();
		List<String> ids1 = new ArrayList<>();
		ids1.add(equipId);
		List<String> ids2 = getAllChildren(ids1, ids);
		ids2.addAll(ids1);
		return modeltrainmapper.svmmodelcount(ids2);
	}
	
	@Override
	public void insert(String equipid, String modeltype,String svmmodelparam,String model)
	{	
		modeltrainmapper.insertmodelparam(equipid, modeltype, svmmodelparam,model);
	}
	@Override
	public String GetEquipName(String id)
	{
		System.out.println(id);
		String name = modeltrainmapper.getequipname(id);
		return name;
	}
	@Override
	public List<DataInfo> getdataselect(String equipId)
	{
		List<DataInfo> data = new ArrayList<>();
		List<String> ids = new ArrayList<>();
		List<String> ids1 = new ArrayList<>();
		ids1.add(equipId);
		List<String> ids2 = getAllChildren(ids1, ids);
		ids2.addAll(ids1);
		data = modeltrainmapper.getdataselect(ids2);
		return data;
	}
	@Override
	public void deletemodel(String id)
	{
		modeltrainmapper.deletemodel(id);
	}
	@Override
	public List<String> getsqldata(String dataid)
	{
		List<String> data = new ArrayList<>();
		System.out.println(dataid);
		data = modeltrainmapper.getsqldata(dataid);
		return data;
	}
	
	@Override
	public List<String> SVM(String dataSelect,String svm_type,String svm_kernel,String cache_size,String eps,String c,String equipid)
	{
		String svmmodelparam="";
		 List<Double> label = new ArrayList<Double>();
   	  List<svm_node[]> nodeSet = new ArrayList<svm_node[]>();
   	  getData(nodeSet, label, "E:\\eclipse-workspace\\project1\\file\\train.txt");
   	 // getData(nodeSet, label, "E:\\eclipse-workspace\\project1\\project1\\file\\train.txt");
   	 // getData(nodeSet, label,  "E:\\eclipse-workspace\\EnterpriseRating\\file\\train1.txt");
   	 // getsqlData(nodeSet, label,dataSelect);
   	  int dataRange=nodeSet.get(0).length;
   	  svm_node[][] datas = new svm_node[nodeSet.size()][dataRange]; // 训练集的向量表
   	  for (int i = 0; i < datas.length; i++) 
   	  {
   		  for (int j = 0; j < dataRange; j++) 
   		  {
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
   	  case "3":param.svm_type = svm_parameter.EPSILON_SVR;
   	           svmmodelparam = svmmodelparam + "EPSILON_SVR,";break;
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
   	 // getData(testnodeSet, testlabel, "E:\\eclipse-workspace\\project1\\file\\test.txt");
   	//  getData(nodeSet, label, "E:\\eclipse-workspace\\project1\\project1\\file\\test.txt");
   	 getData(testnodeSet, testlabel, "E:\\eclipse-workspace\\EnterpriseRating\\file\\test1.txt");
    	//getsqlData(testnodeSet,testlabel,dataSelect);
   	  GsonBuilder gsonBuilder = new GsonBuilder();
   	  gsonBuilder.serializeSpecialFloatingPointValues();
   	  Gson gson = gsonBuilder.create();		    	
   	  String json = gson.toJson(model);
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
	  List<String> result =new ArrayList<>();
	  result.add(svmmodelparam);
	  result.add(json);
	  result.add(Double.toString(acc));
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
	 
    public void getsqlData(List<svm_node[]> nodeSet, List<Double> label, String dataSelect)
    {
         List<String> data = new ArrayList<>();
         data = getsqldata(dataSelect);
         int ii;
         for(ii = 0; ii < data.size(); ii++)
         {
        	 String[] datas = data.get(ii).split(",,");
        	 svm_node[] vector = new svm_node[datas.length];
        	 for (int i = 0; i < datas.length; i++)
        	 {
    		    svm_node node = new svm_node();
    			node.index = i + 1;
    	        node.value = Double.parseDouble(datas[i]);
    	        vector[i] = node;
        	 }
    		   nodeSet.add(vector);
    		   double lablevalue = Double.parseDouble(datas[datas.length - 1]);
    		   label.add(lablevalue);
         }
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
		String equipname = GetEquipName(svmdata.getEquipid());
		one.setEquip_name(equipname);
		System.out.println(one);
		//one.setEquip_name(equip_name);
		return one;
		
	}
	@Override
	public svm_parameter SelectParam()
	{
		svm_parameter result = new svm_parameter();
		Random random = new Random();
		result.svm_type = random.nextInt(5);
		result.kernel_type = random.nextInt(4);
		switch(random.nextInt(2))
		{
		case 0:
			{
				int min = 1;
				int max= 1000;
				result.C = min + random.nextInt(1000);
				
				System.out.println(result.C+"   0000000000000000000000000000000000");
			}
		case 1:
		    {
				double min = 0.0001;
				double max= 1;
				result.C = min + random.nextDouble() * (max - min);
		    }
		}
		result.cache_size = 10 + random.nextDouble()*100;
		double a=result.C;
		double b=result.cache_size;
		switch(1+random.nextInt(4))
		{
			case 1:result.eps=0.1;break;
			case 2:result.eps=0.01;break;
			case 3:result.eps=0.001;break;
			case 4:result.eps=0.0001;break;
		}
		return result;
	}
	
	@Override
	public List<String> svmtrainselectbest(svm_parameter param[],String dataSelect,String equipid)
	{
		double temp=0;
		List<String> result = new ArrayList<>();
		List<String> result1 = new ArrayList<>();
		for(int k = 0 ; k < param.length;k++)
	    {
		    result1 = RandomTrain(param[k],dataSelect,equipid);
		    if(Double.parseDouble(result1.get(2)) >= temp)
		    {
		    	temp = Double.parseDouble(result1.get(2));
		    	result = result1;
		    }
		}
	    insert(equipid, result.get(3), result.get(0), result.get(1));
   	    return result;
	}
	
	public List<String> RandomTrain(svm_parameter param,String dataSelect,String equipid)
	{
		
		float excTime=0;
		svm_model model2 = new svm_model();
		List<String> result = new ArrayList<>();
		List<Double> label = new ArrayList<Double>();
   	    List<svm_node[]> nodeSet = new ArrayList<svm_node[]>();
        getData(nodeSet, label, "E:\\eclipse-workspace\\project1\\file\\train.txt");
  	 // getData(nodeSet, label, "E:\\eclipse-workspace\\project1\\project1\\file\\train.txt");
  	 // getData(nodeSet, label,  "E:\\eclipse-workspace\\EnterpriseRating\\file\\train1.txt");
  	 // getsqlData(nodeSet, label,dataSelect);
  	    int dataRange=nodeSet.get(0).length;
  	    svm_node[][] datas = new svm_node[nodeSet.size()][dataRange]; // 训练集的向量表
  	    for (int i = 0; i < datas.length; i++) 
  	    {
  		    for (int j = 0; j < dataRange; j++) 
  		    {
  			    datas[i][j] = nodeSet.get(i)[j];
  		    }
  	    }

  	    double[] lables = new double[label.size()]; // a,b 对应的lable
  	    for (int i = 0; i < lables.length; i++) 
  	    {
  		    lables[i] = label.get(i);
  	    }
  	 
  	// 定义svm_problem对象
  	    svm_problem problem = new svm_problem();
  	    problem.l = nodeSet.size(); // 向量个数
  	    problem.x = datas; // 训练集向量表
  	    problem.y = lables; // 对应的lable数组
  	  
  	// 训练SVM分类模型
  	    System.out.println(svm.svm_check_parameter(problem, param));
  	// 如果参数没有问题，则svm.svm_check_parameter()函数返回null,否则返回error描述。
  	    long startTime=System.currentTimeMillis();
  	    svm_model model = svm.svm_train(problem, param);
  	    long endTime=System.currentTimeMillis();
        excTime=(float)(endTime-startTime)/1000;
        System.out.println("执行时间："+excTime+"s");
       
  	  // svm.svm_train()训练出SVM分类模型
  	  // 获取测试数据

  	    List<Double> testlabel = new ArrayList<Double>();
  	    List<svm_node[]> testnodeSet = new ArrayList<svm_node[]>();
  	    getData(testnodeSet, testlabel, "E:\\eclipse-workspace\\project1\\file\\test.txt");
  	//  getData(nodeSet, label, "E:\\eclipse-workspace\\project1\\project1\\file\\test.txt");
  	//  getData(testnodeSet, testlabel, "E:\\eclipse-workspace\\EnterpriseRating\\file\\test1.txt");
  	    svm_node[][] testdatas = new svm_node[testnodeSet.size()][dataRange]; // 训练集的向量表
  	    for (int i = 0; i < testdatas.length; i++) 
  	    {
  		    for (int j = 0; j < dataRange; j++) 
  		    {
  			    testdatas[i][j] = testnodeSet.get(i)[j];
  		    }
  	    }
  	    double[] testlables = new double[testlabel.size()]; // a,b 锟斤拷应锟斤拷lable
  	    for (int i = 0; i < testlables.length; i++) 
  	    {
  		    testlables[i] = testlabel.get(i);
  	    }
  	 
  	//用来标记当前读取数据的行数
  	    double err = 0.0 ;
  	    double count = 0 ;
  	    for (int i = 0; i < testdatas.length; i++) 
  	    {
  		    int truevalue = (int) testlables[i];
  		    System.out.print(truevalue + " ");
  		    double predictValue = svm.svm_predict(model, testdatas[i]);
  		    System.out.println(Math.round(predictValue));
  		    if(Math.round(predictValue) != truevalue)
  		   {
  			   count++; 		   
  		   }
  	 //  err += Math.abs(predictValue - truevalue);
  		   err=count/testdatas.length;
  	    }
  	  //System.out.println("err=" + err / datas.length);
  	    System.out.println("err="+err);
  	    double acc = 1 - err;
		
		String modeltype = "svm";
		String svmmodelparam="";
		switch(model.getParam().svm_type)
  	    {
  	       case 4:svmmodelparam = svmmodelparam + "EPSILON_SVR,";break;
  	       case 0:svmmodelparam = svmmodelparam + "C_SVC,";break;
  	       case 1:svmmodelparam = svmmodelparam + "NU_SVC,";break;
  	       case 2:svmmodelparam = svmmodelparam + "ONE_CLASS,";break;
  	       case 3:svmmodelparam = svmmodelparam + "NU_SVR,";break;
  	    }
  	  
  	    switch(model.getParam().kernel_type)
  	    {
  	       case 0:svmmodelparam = svmmodelparam + "LINEAR,";break;
  	       case 1:svmmodelparam = svmmodelparam + "POLY,"; break;
  	       case 2:svmmodelparam = svmmodelparam + "RBF,";break;
  	       case 3:svmmodelparam = svmmodelparam + "SIGMOID,";break;
  	    }
  	    svmmodelparam = svmmodelparam + model.getParam().cache_size +",";
  	    svmmodelparam = svmmodelparam +model.getParam().eps +",";
  	    svmmodelparam = svmmodelparam + model.getParam().C+",";
  	    svmmodelparam = svmmodelparam + Float.toString(excTime) + ",";
  	    svmmodelparam = svmmodelparam + Double.toString(acc);
		GsonBuilder gsonBuilder = new GsonBuilder();
     	gsonBuilder.serializeSpecialFloatingPointValues();
  	    Gson gson = gsonBuilder.create();		    	
  	    String json = gson.toJson(model);
  	    result.add(svmmodelparam);
  	    result.add(json);
  	    result.add(Double.toString(acc));
  	    result.add(modeltype);
      	return result;
	}

}
