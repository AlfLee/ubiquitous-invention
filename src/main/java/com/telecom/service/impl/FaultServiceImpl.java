package com.telecom.service.impl;

import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.awt.Label;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mysql.fabric.xmlrpc.base.Struct;
import com.telecom.entity.Fault_entity;
import com.telecom.entity.GetModel;
import com.telecom.entity.SvmModel_entity;
import com.telecom.mapper.FaultMapper;
import com.telecom.service.FaultService;

import svm.svm;
import svm.svm_model;
import svm.svm_node;
@Transactional //确保事物
@Service(value="FaultService")
public class FaultServiceImpl implements FaultService{

	@Autowired
	private FaultMapper faultmapper;
	
	public List<String> getAllChildren(List<String> ids1, List<String> ids) {
		List<String> newIds = faultmapper.getAllChildren(ids1);
		if(!newIds.isEmpty()) {
			ids.addAll(newIds);
			getAllChildren(newIds,ids);
		}
		return ids;
	}
	
	@Override
	public String GetModelid(String equipId,String algType)
	{
		int i,lable=0;
		double temp1=0,temp2,accuracy=0;
		String result="";
		List<String> ids = new ArrayList<>();
		List<String> ids1 = new ArrayList<>();
		ids1.add(equipId);
		List<String> ids2 = getAllChildren(ids1, ids);
		ids2.addAll(ids1);
		List<GetModel> modelss = new ArrayList<>();
		
		switch (algType) {
		case "SVM":
			List<SvmModel_entity> adaptdatas = new ArrayList<>();
			SvmModel_entity adapt=new SvmModel_entity();
			modelss = faultmapper.getmodel(ids2,"svm");
			for(i = 0;i<modelss.size();i++)
			{
				String temp = modelss.get(i).getParam();
				String[] a = temp.split(",");
				temp2 = Double.parseDouble(a[6]);
				if(temp1<temp2)
				{
					temp1 = temp2;
					lable = i;
					accuracy = temp1;
				}
			}
			//String b[]=modelss.get(lable).getParam().split(",");
			//result = "SVM类型:"+b[0]+";核类型:"+b[1]+";缓存大小:"+b[2]+";终止条件:"+b[3]+";惩罚系数:"+b[4]+";精度:"+b[6];
			break;
		}
		return modelss.get(lable).getId()+","+accuracy;
		
	}
	
	@Override
	public String getequipname(String equipId)
	{
		return faultmapper.getequipname(equipId);
	}
	
	@Override
	public List<Fault_entity> GetModel(String equipId,int current,int pageSize)
	{
		List<String> ids = new ArrayList<>();
		List<String> ids1 = new ArrayList<>();
		ids1.add(equipId);
		List<String> ids2 = getAllChildren(ids1, ids);
		ids2.addAll(ids1);
		List<Fault_entity> faultss = new ArrayList<>();
		faultss = faultmapper.getallfault(ids2,current,pageSize);
		return faultss;
		
	}
	
	@Override
	public int getfaultcount(String equipId)
	{
		List<String> ids = new ArrayList<>();
		List<String> ids1 = new ArrayList<>();
		ids1.add(equipId);
		List<String> ids2 = getAllChildren(ids1, ids);
		ids2.addAll(ids1);
		int count = faultmapper.getfaultcount(ids2);
		return count;
		
	}
	@Override
	public List<String> getsqldata(String dataid)
	{
		List<String> data = new ArrayList<>();
		System.out.println(dataid);
		data = faultmapper.getsqldata(dataid);
		return data;
	}
	
	@Override
	public String GetModel(String modelid)
	{
		String modelparam;
		modelparam = faultmapper.modelfault(modelid);
		return modelparam;
	}
	
	@Override
	public void insertresult(String faultresult,String EquipName,String faultparam,String alg_type,String equipid,String time,String faultdata)
	{
		faultmapper.insertresult(faultresult,EquipName,faultparam,alg_type,equipid,time,faultdata);
	}
	
	@Override
	public void diagnose(String dataSelect,String alg_type,String equipid)
	{
		String EquipName = "";
		List<String> faultresult = new ArrayList<>();
		List<String> faultdata = new ArrayList<>();
		EquipName = getequipname(equipid);
		if(EquipName ==null)
		{
			EquipName = "";
		}
		String ModelfaultidAndAccuracy = GetModelid(equipid, alg_type);
		String temp[] = ModelfaultidAndAccuracy.split(",");
		String Modelfault = GetModel(temp[0]);
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.serializeSpecialFloatingPointValues();
   	    Gson gson = gsonBuilder.create();		    	
   	    svm_model model = gson.fromJson(Modelfault,svm_model.class);
    	
		 String faultparam = "";
		 faultparam = "svm类型:";
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
	  	
        List<Double> diaglabel = new ArrayList<Double>();
   	    List<svm_node[]> diagnodeSet = new ArrayList<svm_node[]>();
    	faultdata = getsqlData(diagnodeSet,diaglabel,dataSelect);
     	// getData(diagnodeSet, diaglabel, "E:\\eclipse-workspace\\project1\\file\\train.txt");
		 int row = diagnodeSet.size();
		 int column = diagnodeSet.get(0).length;
		 //return train(data, model, dataRange);
		 System.out.println(row + " "+column);
		 double[] trainData = new double[row];
		 svm_node[][] testdatas = new svm_node[row][column];
		 for(int i = 0; i < row; i++){
			 for(int j = 0; j < column; j++){
				 testdatas[i][j] = diagnodeSet.get(i)[j];	
			 }
		 }
		 for (int i = 0; i < testdatas.length; i++) {
	   		  double predictValue = svm.svm_predict(model, testdatas[i]);
	   		  System.out.println(Math.round(predictValue));
	   		  trainData[i] = Math.round(predictValue);
	   		  faultresult.add(gson.toJson(trainData[i]));
	   	  }
		// String faultresult = gson.toJson(trainData[0]);
		//生成日期对象  
		 Date current_date = new Date();  
		 //设置日期格式化样式为：yyyy-MM-dd  
		 SimpleDateFormat  SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		 //格式化当前日期  
		 String time = SimpleDateFormat.format(current_date.getTime());  
		 //输出测试一下  
		 System.out.println("当前的系统日期为：" + SimpleDateFormat.format(current_date.getTime())); 
		// faultresult = "5";
		// EquipName ="5";
		// faultparam ="5";
		// alg_type ="5";
		// equipid = "6";
		// time = "2008-06-06";
		 for(int i= 0;i<testdatas.length;i++)
		 {
			 insertresult(faultresult.get(i),EquipName,faultparam,alg_type,equipid,time,faultdata.get(i));
		 }
	}
	
    public List<String> getsqlData(List<svm_node[]> nodeSet, List<Double> label, String dataSelect)
    {
         List<String> data = new ArrayList<>();
         List<String> datasss = new ArrayList<>();
         data = getsqldata(dataSelect);
         int ii;
         String resultdata="";
         for(ii = 0; ii < data.size(); ii++)
         {
        	 String[] datas = data.get(ii).split(",,");
        	 svm_node[] vector = new svm_node[datas.length];
        	 resultdata="";
        	 for (int i = 0; i < datas.length; i++)
        	 {
        		resultdata = resultdata + datas[i] +",";
    		    svm_node node = new svm_node();
    			node.index = i + 1;
    	        node.value = Double.parseDouble(datas[i]);
    	        vector[i] = node;
        	 }
        	 datasss.add(resultdata);
    		   nodeSet.add(vector);
    		   //double lablevalue = Double.parseDouble(datas[datas.length-1]);
    		   //label.add(lablevalue);
         }
         return datasss;
    }
    
    @Override
    public String getsimpledata(String FaultDataId)
    {
    	String result = faultmapper.getsimpledata(FaultDataId);
    	return result;
    }
    
}
