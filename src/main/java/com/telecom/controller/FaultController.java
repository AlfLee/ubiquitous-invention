package com.telecom.controller;

import static org.mockito.Mockito.timeout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.telecom.entity.Fault_entity;
import com.telecom.entity.GetModel;
import com.telecom.service.FaultService;
import com.telecom.utils.QResponseVo;
import com.telecom.utils.QResponseVo.CODE;

import svm.svm;
import svm.svm_model;
import svm.svm_node;
import svm.svm_parameter;

@Controller
public class FaultController {
	
	
	@Autowired
	private FaultService faultservice;
	
	
	@RequestMapping("/analysis")
	public String analysis() {
		return "analysis";  
	}
	
	
	@RequestMapping("/diagnose")
	@ResponseBody
	public QResponseVo<List<Fault_entity>> Diagnose(@RequestParam(value="dataSelect") String dataSelect,
			@RequestParam(value="algSelect") String alg_type,
			@RequestParam(value="equipid") String equipid){
		QResponseVo<List<Fault_entity>> result = new QResponseVo<List<Fault_entity>>();
		faultservice.diagnose(dataSelect, alg_type, equipid);
	    result.setCode(CODE.Success);
    	return result;
	}
	
	@RequestMapping("/showfaultresult")
	@ResponseBody
	public QResponseVo<List<Fault_entity>> ShowResult(@RequestParam(value="current") int current,
			@RequestParam(value="limit") int limit,
			@RequestParam(value="equipId") String equipid){
		QResponseVo<List<Fault_entity>> result = new QResponseVo<List<Fault_entity>>();
		List<Fault_entity> datass = new ArrayList<>();
		datass = faultservice.GetModel(equipid,(current-1)*limit,limit);
		int count = faultservice.getfaultcount(equipid);
		result.setData(datass);
		result.setCount(count);
		return result;
	}
	 
	@RequestMapping("/getmodelparam")
	@ResponseBody
	public String getmodelparam(@RequestParam(value="modelparam") String modelparam,
			@RequestParam(value="equipId") String equipId)
	{
		String result = "";
		String data = "";
		data = faultservice.GetModelid(equipId, modelparam);
		String temp[]=data.split(",");
		result = faultservice.GetModel(temp[0]);
		 String faultparam = "";
		 faultparam = "svm类型:";
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.serializeSpecialFloatingPointValues();
	   	    Gson gson = gsonBuilder.create();		    	
	   	    svm_model model = gson.fromJson(result,svm_model.class);
	   	  switch(model.getParam().svm_type)
	   	  {
	   	  case 4:
	   		faultparam = faultparam + "EPSILON_SVR;";break;
	   	  case 0:
	   		faultparam = faultparam + "C_SVC;";break;
	   	  case 1:
	   		faultparam = faultparam + "NU_SVC;";break;
	   	  case 2:
	   		faultparam = faultparam + "ONE_CLASS;";break;
	   	  case 3:
	   		faultparam = faultparam + "NU_SVR;";break;
	   	  }
	   	faultparam = faultparam + "核函数类型:";
	  	  switch(model.getParam().kernel_type)
	  	  {
	  	  case 0:
	  		faultparam = faultparam + "LINEAR;";break;
	  	  case 1 :
	  		faultparam = faultparam + "POLY;"; break;
	  	  case 2:
	  		faultparam = faultparam + "RBF;";break;
	  	  case 3:
	  		faultparam = faultparam + "SIGMOID;";break;
	  	  }
	  	faultparam = faultparam + "缓存大小:"+model.getParam().cache_size+";"+"终止条件:"+model.getParam().eps+";"
	  			+"惩罚系数:"+model.getParam().C+";"+"精度:"+temp[1];
		return faultparam;
	}
	
	@RequestMapping("/ShowSimpleData")
	public String ShowSimpleData(@RequestParam(required=true,value="FaultDataId") String FaultDataId, ModelMap model) {
		String data = faultservice.getsimpledata(FaultDataId);
		List<String> datas = new ArrayList<>();
		String[] datass = data.split(",");
		for(int i=0;i<datass.length;i++)
		{
			datas.add(datass[i]);
		}
		//model.addAttribute("FaultDataId", FaultDataId);
		model.addAttribute("datas", datas);
		return "faultDataResult";  
	}
	
	@RequestMapping("/ShowAlgDetail")
	public String ShowAlgDetail(@RequestParam(required=true,value="PARAM") String PARAM,@RequestParam(required=true,value="algtype") String algtype, ModelMap model) {
		model.addAttribute("PARAM", PARAM);
		model.addAttribute("algtype",algtype);
		System.out.println(PARAM+"1111111111111111111111111111111");
		System.out.println(algtype+"1111111111111111111111111111111");
		return "algDetail";  
	}

}
