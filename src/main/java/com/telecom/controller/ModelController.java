package com.telecom.controller;



import static org.mockito.Matchers.intThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
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

				svmdatas = modeltrainservise.Showmodel(equipId, (current-1)*limit, limit,"svm");
				System.out.println(svmdatas);
				int count = modeltrainservise.modelcount(equipId,"svm");
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
			
			@RequestMapping("/Softmax")
			@ResponseBody
			public QResponseVo<List<String>> Softmax(@RequestParam(value="DataSelectSoftMax",defaultValue="1") String DataSelectSoftMax,
				@RequestParam(value="Iteration",defaultValue="400") int Iteration,
				@RequestParam(value="LearnRate",defaultValue="0.25") double LearnRate,
				@RequestParam(value="equipid",defaultValue="1") String equipid) {
				QResponseVo<List<String>> result = new QResponseVo<List<String>>();
				List<String> temp = new ArrayList<>();
				DataSelectSoftMax = "E:\\eclipse-workspace\\project1\\file\\train.txt";
				temp = modeltrainservise.Softmax(DataSelectSoftMax, Iteration, LearnRate, equipid);
				modeltrainservise.insert(equipid,"softmax",temp.get(1),temp.get(0));
				result.setCode(CODE.Success);
				return result;
				}
			
			
			@RequestMapping("/showsoftmaxmodel")
			@ResponseBody
			public QResponseVo<List<GetModel>> showsoftmaxmodel(@RequestParam(value="current") int current,
					@RequestParam(value="limit") int limit,
					@RequestParam(value="equipId") String equipId){
				QResponseVo<List<GetModel>> result = new QResponseVo<List<GetModel>>();
				//List<SvmModel_entity> datass = new ArrayList<>(); 

				List<GetModel> softmaxdatas = new ArrayList<>();
				softmaxdatas = modeltrainservise.Showmodel(equipId, (current-1)*limit, limit,"softmax");
				//System.out.println(svmdatas);
				int count = modeltrainservise.modelcount(equipId,"softmax");
				System.out.println(count+"");
				for(int i = 0 ; i < softmaxdatas.size();i++)
				{
					String softmaxparam = softmaxdatas.get(i).getParam();
					String equipname = modeltrainservise.GetEquipName(softmaxdatas.get(i).getEquipid());
					softmaxparam = softmaxparam + "," + equipname;
					softmaxdatas.get(i).setParam(softmaxparam);
				}
				result.setData(softmaxdatas);
				result.setCount(count);
				return result;
			}
			
			@RequestMapping("/Bayes")
			@ResponseBody
			public QResponseVo<List<String>> Bayes(@RequestParam(value="DataSelectBayes",defaultValue="1") String DataSelectBayes,
				@RequestParam(value="equipid",defaultValue="1") String equipid) {
				QResponseVo<List<String>> result = new QResponseVo<List<String>>();
				List<String> temp = new ArrayList<>();
				DataSelectBayes = "E:\\大学\\毕业设计\\材料\\NaiveBayes\\NaiveBayes\\train.txt";
				temp = modeltrainservise.Bayes(DataSelectBayes, equipid);
				modeltrainservise.insert(equipid,"bayes",temp.get(1),temp.get(0));
				result.setCode(CODE.Success);
				return result;
				}
			
			@RequestMapping("/showbayesmodel")
			@ResponseBody
			public QResponseVo<List<GetModel>> showbayesmodel(@RequestParam(value="current") int current,
					@RequestParam(value="limit") int limit,
					@RequestParam(value="equipId") String equipId){
				QResponseVo<List<GetModel>> result = new QResponseVo<List<GetModel>>();
				//List<SvmModel_entity> datass = new ArrayList<>(); 

				List<GetModel> bayesdatas = new ArrayList<>();
				bayesdatas = modeltrainservise.Showmodel(equipId, (current-1)*limit, limit,"bayes");
				//System.out.println(svmdatas);
				int count = modeltrainservise.modelcount(equipId,"bayes");
				System.out.println(count+"");
				for(int i = 0 ; i < bayesdatas.size();i++)
				{
					String bayesparam = bayesdatas.get(i).getParam();
					String equipname = modeltrainservise.GetEquipName(bayesdatas.get(i).getEquipid());
					bayesparam = bayesparam + "," + equipname;
					bayesdatas.get(i).setParam(bayesparam);
				}
				result.setData(bayesdatas);
				result.setCount(count);
				return result;
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
