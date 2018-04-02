package com.telecom.service.impl;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.telecom.entity.DataInfo;
import com.telecom.entity.GetModel;
import com.telecom.entity.SvmModel_entity;
import com.telecom.mapper.ModelTrainMapper;
import com.telecom.service.ModelTrainService;


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
	public void insert(String equipid, String modeltype,String svmmodelparam)
	{	
		modeltrainmapper.insertmodelparam(equipid, modeltype, svmmodelparam);
	}
	
	public String GetEquipName(String id)
	{
		System.out.println(id);
		String name = modeltrainmapper.getequipname(id);
		return name;
	}
	
	public List<DataInfo> getdataselect(String equipId)
	{
		List<DataInfo> data = new ArrayList<>();
		List<String> ids = new ArrayList<>();
		List<String> ids1 = new ArrayList<>();
		ids1.add(equipId);
		List<String> ids2 = getAllChildren(ids1, ids);
		ids2.addAll(ids1);
		System.out.println(ids2+"22222222222222222222222");
		data = modeltrainmapper.getdataselect(ids2);
		return data;
	}
	
	public void deletemodel(String id)
	{
		System.out.println("22222222222222222222222222222222"+id);
		modeltrainmapper.deletemodel(id);
	}

}
