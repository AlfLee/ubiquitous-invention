package com.telecom.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.telecom.entity.DataInfo;
import com.telecom.model.ConnectReturn;
import com.telecom.service.DataService;
import com.telecom.utils.QResponseVo;
import com.telecom.utils.QResponseVo.CODE;
import com.telecom.utils.QString;

@Controller
public class DataController {
	Logger logger = LoggerFactory.getLogger(DataController.class);
	
	@Autowired
	private DataService dataService;
	
	/**
	 * 数据导入页面
	 * @return
	 */
	@RequestMapping("datainput")
	public String datainput() {
		return "datainput";  
	}
	
	/**
	 * 上传文件
	 * @param file
	 * @param name
	 * @param dataUseWay
	 * @param dataTarget
	 * @param equipment
	 * @param importWay
	 * @return
	 */
	@RequestMapping("/uploadFile")
	@ResponseBody //该注解表示 当前方法返回值要传给页面，而不是打开一个页面
	public QResponseVo<String> uploadFile(@RequestParam(required=false,value="file") MultipartFile file,
			@RequestParam(value="name",defaultValue="") String name,
			@RequestParam(value="dataUseWay",defaultValue="") int dataUseWay,
			@RequestParam(value="dataTarget",defaultValue="") int dataTarget,
			@RequestParam(value="equipment",defaultValue="") String equipment,
			@RequestParam(value="importWay", defaultValue="") int importWay) {
		
		//定义一个对象 用于返回信息给页面
		QResponseVo<String> result = new QResponseVo<String>();
		boolean flag = false;
		try {
			dataService.saveFile(name, dataUseWay, dataTarget, importWay, equipment, file);
			flag = true;
		}catch(Throwable th) {
			//异常堆栈信息
			logger.error("uploadFile due to:", th);
		}
		if(flag) {
			result.setCode(CODE.Success);
		}else {
			result.setCode(CODE.Fail);
		}
		return result;  
	}
	
	@RequestMapping("/saveDatabase")
	@ResponseBody //该注解表示 当前方法返回值要传给页面，而不是打开一个页面
	public QResponseVo<String> saveDatabase(@RequestParam(required=true,value="currDatabase",defaultValue="") String currDatabase,
			@RequestParam(value="name",defaultValue="") String name,
			@RequestParam(value="selectWay",defaultValue="1") int selectWay,
			@RequestParam(value="tableOrSql",defaultValue="") String tableOrSql,
			@RequestParam(value="dataUseWay",defaultValue="") int dataUseWay,
			@RequestParam(value="dataTarget",defaultValue="") int dataTarget,
			@RequestParam(value="databaseId",defaultValue="") String databaseId,
			@RequestParam(value="equipment",defaultValue="") String equipment,
			@RequestParam(value="importWay", defaultValue="") int importWay) {
		
		//定义一个对象 用于返回信息给页面
		QResponseVo<String> result = new QResponseVo<String>();
		boolean flag = false;
		try {
			//保存至数据库
			dataService.saveByDatabase(name, dataUseWay, dataTarget, importWay, equipment, 
					currDatabase, selectWay, tableOrSql, databaseId);
			flag = true;
		}catch(Throwable th) {
			//异常堆栈信息
			logger.error("uploadFile due to:", th);
		}
		if(flag) {
			result.setCode(CODE.Success);
		}else {
			result.setCode(CODE.Fail);
		}
		return result;  
	}
	
	/**
	 * 查询或点击树节点
	 * @param dataName
	 * @param dataUseWay
	 * @param dataTarget
	 * @param importWay
	 * @param current
	 * @param limit
	 * @param equipId
	 * @return
	 */
	@RequestMapping("/getDataInfo")
	@ResponseBody //该注解表示 当前方法返回值要传给页面，而不是打开一个页面
	public QResponseVo<List<DataInfo>> getDataInfo(
			@RequestParam(value="dataName", defaultValue="", required=false) String dataName,
			@RequestParam(value="dataUseWay", defaultValue="", required=false) String dataUseWay,
			@RequestParam(value="dataTarget", defaultValue="", required=false) String dataTarget,
			@RequestParam(value="importWay", defaultValue="", required=false) String importWay,
			@RequestParam(value="current", defaultValue="1", required=false) int current,
			@RequestParam(value="limit", defaultValue="5", required=false) int limit,
			@RequestParam(value="equipId", defaultValue="", required=true) String equipId) {
		//定义一个对象 用于返回信息给页面
		QResponseVo<List<DataInfo>> result = new QResponseVo<List<DataInfo>>();
		boolean flag = false;
		//查询条件
		String conditions = "";
		if(!QString.IsNullOrEmpty(dataName)) conditions = conditions + " and data_name like '%" + dataName + "%' ";
		if(!QString.IsNullOrEmpty(dataUseWay)) conditions = conditions + " and data_use_way = " +dataUseWay;
		if(!QString.IsNullOrEmpty(dataTarget)) conditions = conditions + " and data_target = " +dataTarget;
		if(!QString.IsNullOrEmpty(importWay)) conditions = conditions + " and import_way = " +importWay;
		List<DataInfo> dataInfos = new ArrayList<>();
		int allCount = 0;
		try {
			dataInfos = dataService.getDataInfo(equipId, conditions, (current - 1) * limit, limit);
			allCount = dataService.getDataInfoCount(equipId, conditions);
			flag = true;
		}catch(Throwable th) {
			//异常堆栈信息
			logger.error("uploadFile due to:", th);
		}
		if(flag) {
			result.setCode(CODE.Success);
			result.setData(dataInfos);
		}else {
			result.setCode(CODE.Fail);
		}
		result.setCount(allCount);
		return result;  
	}
	
	/**
	 * 编辑
	 * @param dataId
	 * @param model
	 * @return
	 */
	@RequestMapping("editDataInfo")
	public String dataInfo(@RequestParam(required=true,value="dataId") String dataId, ModelMap model) {
		DataInfo dataInfo = dataService.getOne(dataId);
		model.addAttribute("dataInfo", dataInfo);
		return "editdatainfo";  
	}
	
	/**
	 * 查看明细
	 * @param dataId
	 * @param model
	 * @return
	 */
	@RequestMapping("lookUp")
	public String lookUp(@RequestParam(required=true,value="dataId") String dataId, ModelMap model) {
		List<Object> datas = dataService.getDataVariables(dataId);
		model.addAttribute("datas", datas);
		model.addAttribute("dataId", dataId);
		return "datadetail";  
	}
	
	/**
	 * 获取表格数据
	 * @param dataId
	 * @param model
	 * @return
	 */
	@RequestMapping("getDatas")
	@ResponseBody
	public QResponseVo<Map<String, String[]>> getDatas(@RequestParam(required=true,value="dataId") String dataId,
			@RequestParam(value="current", defaultValue="1", required=false) int current,
			@RequestParam(value="limit", defaultValue="5", required=false) int pageSize) {
		QResponseVo<Map<String, String[]>> result = new QResponseVo<Map<String, String[]>>();
		//得到所有行数据 id+行数组（由于不知道有几行，用集合或者数组存储行数据）
		Map<String, String[]> datas = dataService.getDataDetail(dataId, (current - 1) * pageSize, pageSize);
		result.setData(datas);
		result.setCode(CODE.Success);
		result.setCount(dataService.getDataDetailCount(dataId));
		return result;  
	}
	
	/**
	 * 保存数据的基本信息
	 * @param dataId
	 * @param dataName
	 * @param dataUseWay
	 * @param dataTarget
	 * @return
	 */
	@RequestMapping("saveData")
	@ResponseBody
	public QResponseVo<String> saveData(@RequestParam(required=true,value="dataId",defaultValue="") String dataId,
			@RequestParam(required=true,value="dataName",defaultValue="") String dataName,
			@RequestParam(required=true,value="dataUseWay",defaultValue="1") int dataUseWay,
			@RequestParam(required=true,value="dataTarget",defaultValue="1") int dataTarget) {
		QResponseVo<String> result = new QResponseVo<String>();
		DataInfo dataInfo = new DataInfo();
		dataInfo.setId(dataId);
		dataInfo.setDataName(dataName);
		dataInfo.setDataUseWay(dataUseWay);
		dataInfo.setDataTarget(dataTarget);
		dataService.update(dataInfo);
		result.setCode(CODE.Success);
		return result;  
	}
	
	/**
	 * 保存编辑的这条数据 需同时更新行表与列表
	 * @param dataId
	 * @param dataName
	 * @param dataUseWay
	 * @param dataTarget
	 * @return
	 */
	@RequestMapping("saveOne")
	@ResponseBody
	public QResponseVo<String> saveOne(@RequestParam(required=true,value="id",defaultValue="") String id,
			@RequestParam(required=true,value="currColumn",defaultValue="") String currColumn,
			@RequestParam(required=true,value="column",defaultValue="0") int column,
			@RequestParam(required=true,value="row",defaultValue="0") int row,
			@RequestParam(required=true,value="currentPage",defaultValue="1") int currentPage,
			@RequestParam(required=true,value="pageSize",defaultValue="8") int pageSize,
			@RequestParam(required=true,value="text",defaultValue="") String text) {
		QResponseVo<String> result = new QResponseVo<String>();
		try {
			//dataService.updateData(id, currColumn, text, row, column);
			result.setCode(CODE.Success);
		} catch (Exception e) {
			logger.error("update data error due to:",e);
			result.setCode(CODE.Fail);
		}
		return result;  
	}
	
	/**
	 * 删
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteData")
	@ResponseBody
	public QResponseVo<String> delete(@RequestParam(required=true,value="dataIds") String dataIds){
		QResponseVo<String> result = new QResponseVo<String>();
		String dataids[] = dataIds.split(",");
		try {
			for(String dataId : dataids) {
				if(!QString.IsNullOrEmpty(dataId))
					dataService.delete(dataId);
			}
			result.setCode(CODE.Success);
		}catch(Throwable th) {
			logger.error("delete data has due to:",th);
			result.setCode(CODE.Fail);
		}
		return result;
	}
	
	/**
	 * 数据库连接
	 * @return
	 */
	@RequestMapping("connectdatabase")
	public String goToConnect() {
		return "connectdatabase";  
	}
	
	/**
	 * 连接 并查出所有的库名
	 */
	@RequestMapping("/connectDatabase")
	@ResponseBody
	public QResponseVo<List<String>> connectDatabase(@RequestParam(required=true,value="ip") String ip,
			@RequestParam(required=true,value="port") String port,
			@RequestParam(required=true,value="dataBase") String dataSource,
			@RequestParam(required=true,value="userName") String userName,
			@RequestParam(required=true,value="password") String password,
			@RequestParam(required=false,value="nameOrSID",defaultValue="") String nameOrSID,
			@RequestParam(required=false,value="getTables",defaultValue="0") int getTables,
			@RequestParam(required=false,value="choosedDatabase",defaultValue="") String choosedDatabase,
			@RequestParam(required=false,value="serverName",defaultValue="") String serverName){
		QResponseVo<List<String>> result = new QResponseVo<List<String>>();
		try {
			ConnectReturn cr = dataService.getDataBaseByConnect(ip.trim(), dataSource, 
					port.trim(), userName.trim(), password, nameOrSID, serverName.trim(), getTables,choosedDatabase);
			result.setData(cr.getDatabases());
			result.setAction(cr.getId() == null ? "" : cr.getId());
			result.setCode(CODE.Success);
		}catch(Throwable th) {
			logger.error("connect database has due to:",th);
			result.setCode(CODE.Fail);
		}
		return result;
	}
}


	

