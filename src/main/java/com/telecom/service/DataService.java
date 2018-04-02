package com.telecom.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.telecom.entity.DataInfo;
import com.telecom.model.ConnectReturn;

public interface DataService {

	void insert(MultipartFile file, String dataId) throws Exception;
	
	void saveDataInfo(DataInfo data) throws Exception;
	
	List<DataInfo> getDataInfo(String equipId, String conditions, int current, int pageSize);
	
	int getDataInfoCount(String equipId, String conditions);
	
	void update(DataInfo data);
	
	void delete(String id);
	
	DataInfo getOne(String dataId);
	
	Map<String, String[]> getDataDetail(String dataId, int current, int pageSize);
	
	List<Object> getDataVariables(String dataId);
	
	int getDataDetailCount(String dataId);
	
	void updateData(String id, String currColumn, String text, int row, int column) throws Exception;
	
	ConnectReturn getDataBaseByConnect(String ip, String dataSource, 
			String port, String userName, String password, String nameOrSID, 
			String serverName, int getTables, String choosedDatabase) throws Exception;
	
	void saveFile(String name, int dataUseWay, int dataTarget, int importWay, String equipment, MultipartFile file) throws Exception;
	
	void saveByDatabase(String name, int dataUseWay, int dataTarget, int importWay, String equipment, String currDatabase,
			int selectWay, String tableOrSql, String databaseId) throws Exception;
	
	void saveDataInfoByConnect(String ip, String dataSource, String port, String userName,
			String password, String nameOrSID, String serverName, String currDatabase,int selectWay, String tableOrSql, String dataId) throws Exception;
	
}
