package com.telecom.controller;



import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.ibatis.annotations.Case;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.telecom.entity.DataInfo;
import com.telecom.entity.GetModel;
import com.telecom.entity.SvmModel_entity;

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
				datass = modeltrainservise.adapt(svmdatas);
				result.setData(datass);
				result.setCount(count);
				return result;
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
				List<String> temp = new ArrayList<>();
				temp = modeltrainservise.SVM(dataSelect, svm_type, svm_kernel, cache_size, eps, c, equipid);
				modeltrainservise.insert(equipid,"svm",temp.get(0),temp.get(1));
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
			
			@RequestMapping("/selfadapt")
			@ResponseBody
			public QResponseVo<List<String>> selfadapt(@RequestParam(value="dataSelect",defaultValue="1") String dataSelect,
					@RequestParam(value="equipId") String equipid) {
				QResponseVo<List<String>> result=new QResponseVo<>();
				List<String> temp = new ArrayList<>();
				int MaxSample = 10;
				svm_parameter sample[] = new svm_parameter[MaxSample];
				for(int i=0;i<MaxSample;i++)
				{
					sample[i] = modeltrainservise.SelectParam();
				}
				temp  = modeltrainservise.svmtrainselectbest(sample,dataSelect,equipid);
				result.setData(temp);
				result.setCode(CODE.Success);
			    return result;
			}
			
	
			
			

			
}
