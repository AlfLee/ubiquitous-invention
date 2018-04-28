package com.telecom.service;

import java.util.List;


import com.telecom.entity.DataInfo;
import com.telecom.entity.GetModel;
import com.telecom.entity.SvmModel_entity;

import svm.svm_parameter;

public interface ModelTrainService {
	public void insert(String equipid, String modeltype,String svmmodelparam,String model);
	public List<GetModel> Showsvmmodel(String equipId,int current,int limit);
	public int svmmodelcount(String equipId);
	public String GetEquipName(String id);
	public List<DataInfo> getdataselect(String equipId);
	public void deletemodel(String id);
	public List<String> getsqldata(String dataid);
	public List<String> SVM(String dataSelect,String svm_type,String svm_kernel,String cache_size,String eps,String c,String equipid);
	public List<SvmModel_entity> adapt(List<GetModel> svmdatas);
	public svm_parameter SelectParam();
	public List<String> svmtrainselectbest(svm_parameter param[],String dataSelect,String equipid);
}
