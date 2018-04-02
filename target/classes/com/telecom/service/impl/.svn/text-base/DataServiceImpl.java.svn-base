package com.telecom.service.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.telecom.entity.DataBaseInfo;
import com.telecom.entity.DataColumnDetail;
import com.telecom.entity.DataInfo;
import com.telecom.entity.DataRowDetail;
import com.telecom.mapper.DataMapper;
import com.telecom.model.ConnectReturn;
import com.telecom.service.DataService;
import com.telecom.utils.ConnectDatabase;
import com.telecom.utils.Constants;
import com.telecom.utils.QString;

@Transactional //确保事物
@Service("dataServiceImpl")
public class DataServiceImpl implements DataService {

	//引入dataMapper实例
	@Autowired
	private DataMapper dataMapper;
	

	/**
	 * 处理文件数据 并保存至数据库中
	 */
	@Override
	@SuppressWarnings({ "resource", "unused" })
	public void insert(MultipartFile file, String dataId) throws Exception {
		InputStream in = file.getInputStream();
		String fileName = file.getOriginalFilename();
		if (fileName.endsWith(".xlsx")) {
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(in);
			// 循环工作表Sheet //xssfWorkbook.getNumberOfSheets(),只读一个sheet表格
			KO1:for (int numSheet = 0; numSheet < 1; numSheet++) {
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
				if (xssfSheet == null) {
					continue;
				}
				XSSFRow first = xssfSheet.getRow(0);
				//先读取第一行 且第一行必须是遍变量名 并存放至firstList中
				List<String> firstList = new ArrayList<>();
				for (int i = 0; i <= first.getLastCellNum(); i++)
				{
					XSSFCell brandIdXSSFCell = first.getCell(i);
					String content = QString.EMPTY;
					if (brandIdXSSFCell != null) {// CELL_TYPE_STRING 字符串型 1
						brandIdXSSFCell.setCellType(Cell.CELL_TYPE_STRING);
						content = brandIdXSSFCell.getStringCellValue();
					}
					firstList.add(content);
				}
				int columns = firstList.size() - 1;
				int rows = xssfSheet.getLastRowNum();
				int maxSize = rows > columns ? rows : columns;
				int index = 0;
				// 循环行Row 从第一行开始读数据
				KO2:for (int i = 0; i <= maxSize; i++) {
					
					String rowdata = "";
					String columnData = "";
					KO3:for (int j = 0; j <= maxSize; j++)// 读取列、行
					{
						if(i < columns && j < rows-1) {
							index = i;//当前为第i列的数据
							XSSFRow changeRow = xssfSheet.getRow(j+1);
							XSSFCell fixedColumn = changeRow.getCell(i);
							String content1 = QString.EMPTY;
							if (fixedColumn != null) {// CELL_TYPE_STRING 字符串型 1
								fixedColumn.setCellType(Cell.CELL_TYPE_STRING);
								content1 = fixedColumn.getStringCellValue();
							}
							columnData = columnData + (StringUtils.isEmpty(content1) ? " " : content1) + ",,";
						}
						if(i < rows && j < columns) {//第i行数据
							XSSFRow xssfRow = xssfSheet.getRow(i+1);
							XSSFCell brandIdXSSFCell = xssfRow.getCell(j);
							String content2 = QString.EMPTY;
							if (brandIdXSSFCell != null) {// CELL_TYPE_STRING 字符串型 1
								brandIdXSSFCell.setCellType(Cell.CELL_TYPE_STRING);
								content2 = brandIdXSSFCell.getStringCellValue();
							}
							rowdata = rowdata + (StringUtils.isEmpty(content2) ? " " : content2) + ",,";
						}
					}
					DataRowDetail drd = new DataRowDetail();
					DataColumnDetail dcd = new DataColumnDetail();
					if(!QString.IsNullOrEmpty(columnData)) {
						//保存列数据
						dcd.setDataId(dataId);
						dcd.setColumnDatas(columnData);
						dcd.setDataColumnName(firstList.get(index));
						dataMapper.insertColumn(dcd);
					}
					if(!QString.IsNullOrEmpty(rowdata)) {
						//保存行数据
						drd.setDataId(dataId);
						drd.setRowDatas(rowdata);
						dataMapper.insertRow(drd);
					}
				}
			}
		} else {
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(in);
			// 循环工作表Sheet //hssfWorkbook.getNumberOfSheets() 只读一个sheet表格
			for (int numSheet = 0; numSheet < 1; numSheet++) {
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
				if (hssfSheet == null) {
					continue;
				}
				HSSFRow first = hssfSheet.getRow(0);
				//先读取第一行 且第一行必须是遍变量名 并存放至firstList中
				List<String> firstList = new ArrayList<>();
				for (int i = 0; i <= first.getLastCellNum(); i++)
				{
					HSSFCell brandIdXSSFCell = first.getCell(i);
					String content = QString.EMPTY;
					if (brandIdXSSFCell != null) {// CELL_TYPE_STRING 字符串型 1
						brandIdXSSFCell.setCellType(Cell.CELL_TYPE_STRING);
						content = brandIdXSSFCell.getStringCellValue();
					}
					firstList.add(content);
				}
				int columns = firstList.size() - 1;
				int rows = hssfSheet.getLastRowNum();
				int maxSize = rows > columns ? rows : columns;
				int index = 0;
				OK:
				// 循环行Row 
				for (int i = 0; i <= maxSize; i++) {
					String rowdata = "";
					String columnData = "";
					DataRowDetail drd = new DataRowDetail();
					for (int j = 0; i <= maxSize; j++) {
						if(i < columns && j < rows-1) {
							index = i;//当前为第i列的数据
							HSSFRow changeRow = hssfSheet.getRow(j+1);
							HSSFCell fixedColumn = changeRow.getCell(i);
							String content1 = QString.EMPTY;
							if (fixedColumn != null) {// CELL_TYPE_STRING 字符串型 1
								fixedColumn.setCellType(Cell.CELL_TYPE_STRING);
								content1 = fixedColumn.getStringCellValue();
							}
							columnData = columnData + (StringUtils.isEmpty(content1) ? " " : content1) + ",,";
						}
						if(i < rows && j < columns) {//第i行数据
							HSSFRow hssfRow = hssfSheet.getRow(i+1);
							HSSFCell brandIdHSSFCell = hssfRow.getCell(j);
							String content = QString.EMPTY;
	
							if (brandIdHSSFCell != null) {
								brandIdHSSFCell.setCellType(Cell.CELL_TYPE_STRING);
								content = brandIdHSSFCell.getStringCellValue();
							}
							rowdata = rowdata + (StringUtils.isEmpty(content) ? " " : content) + ",,";
						}
					}
					DataRowDetail drd2 = new DataRowDetail();
					DataColumnDetail dcd2 = new DataColumnDetail();
					if(!QString.IsNullOrEmpty(columnData)) {
						//保存列数据
						dcd2.setDataId(dataId);
						dcd2.setColumnDatas(columnData);
						dcd2.setDataColumnName(firstList.get(index));
						dataMapper.insertColumn(dcd2);
					}
					if(!QString.IsNullOrEmpty(rowdata)) {
						//保存行数据
						drd2.setDataId(dataId);
						drd2.setRowDatas(rowdata);
						dataMapper.insertRow(drd2);
					}
				}
			}
		}
	}


	@Override
	public void saveDataInfo(DataInfo data) throws Exception {
		dataMapper.insertDataInfo(data);
	}


	/**
	 * 递归设备 找出当前设备的所有子节点并查出所有节点的数据
	 */
	@Override
	public List<DataInfo> getDataInfo(String equipId, String conditions, int current, int pageSize) {
		List<String> ids = new ArrayList<>();
		List<String> ids1 = new ArrayList<>();
		ids1.add(equipId);
		List<String> ids2 = getAllChildren(ids1, ids);
		ids2.addAll(ids1);
		return dataMapper.getDataInfo(ids2, conditions, current, pageSize);
	}
	
	/**
	 * 递归找子节点
	 * @param ids1
	 * @param ids
	 * @return
	 */
	public List<String> getAllChildren(List<String> ids1, List<String> ids) {
		List<String> newIds = dataMapper.getAllChildren(ids1);
		if(!newIds.isEmpty()) {
			ids.addAll(newIds);
			getAllChildren(newIds,ids);
		}
		return ids;
	}
	
	/**
	 * 改
	 */
	@Override
	public void update(DataInfo data) {
		dataMapper.update(data);
	}

	/**
	 * 删
	 */
	@Override
	public void delete(String id) {
		dataMapper.delete(id);
	}


	@Override
	public DataInfo getOne(String dataId) {
		return dataMapper.getOne(dataId);
	}


	@Override
	public int getDataInfoCount(String equipId, String conditions) {
		List<String> ids = new ArrayList<>();
		List<String> ids1 = new ArrayList<>();
		ids1.add(equipId);
		List<String> ids2 = getAllChildren(ids1, ids);
		ids2.addAll(ids1);
		return dataMapper.getDataInfoCount(ids2, conditions);
	}


	/**
	 * 得到所有数据
	 */
	@Override
	public Map<String, String[]> getDataDetail(String dataId, int current, int pageSize) {
		List<DataRowDetail> dataRows = dataMapper.getDataRowDetail(dataId, current, pageSize);
		Map<String, String[]> allDatas = new HashMap<>();
		for(DataRowDetail obj : dataRows) {
			String id = obj.getId();
			String datas[] = obj.getRowDatas().split(",,");
			allDatas.put(id, datas);
		}
		return allDatas;
	}


	/**
	 * 得到变量名
	 */
	@Override
	public List<Object> getDataVariables(String dataId) {
		List<Object> variables = dataMapper.getDataVariables(dataId);
		return variables;
	}


	@Override
	public int getDataDetailCount(String dataId) {
		return dataMapper.getDataRowDetailCount(dataId);
	}


	/**
	 * 编辑数据时的保存
	 */
	@Override
	public void updateData(String id, String currColumn, String text, int row, int column) throws Exception {
		String dataRow = dataMapper.getDataRowById(id);
		String[] rowDatas = dataRow.split(",,");
		rowDatas[column] = text;//给当前行第 column 列 赋值
		String newDataRow =  StringUtils.join(rowDatas,",,");
		dataMapper.updateDataRow(id, newDataRow);
		String dataColumn = dataMapper.getDataColumnByColumnName(currColumn);
		String[] columnDatas = dataColumn.split(",,");
		columnDatas[row] = text;
		String newDataColumn =  StringUtils.join(columnDatas,",,");
		dataMapper.updateDataColumn(currColumn, newDataColumn);
	}


	/**
	 * 连接数据库 并返回所有的数据库
	 */
	@Override
	public ConnectReturn getDataBaseByConnect(String ip, String dataSource, String port, String userName,
			String password, String nameOrSID, String serverName, int getTables, String choosedDatabase) throws Exception {
		//获取驱动
		String driver = ConnectDatabase.getDriver(dataSource);
		//获取url
		String url = ConnectDatabase.getUrl(dataSource, ip, port, nameOrSID, serverName);
		
		if(getTables == 1) url = url + "/" + choosedDatabase;//查所有库还是所有表
		//获取连接
		Connection con = ConnectDatabase.connect(driver, userName, password, url);
		//sql：查询所有的数据库
		String sql = "";
		if(Constants.ONE.equals(dataSource) && getTables != 1) {
			//MySql
			sql = "SELECT `SCHEMA_NAME` FROM `information_schema`.`SCHEMATA`";
		}else if(Constants.TWO.equals(dataSource) && getTables != 1) {
			//Oracle
			sql = "select * from v$database";
		}else if(Constants.ONE.equals(dataSource) && getTables == 1) {
			//MySql
			sql = "select table_name from information_schema.tables where table_schema='"+choosedDatabase+"'";
		}else if(Constants.TWO.equals(dataSource) && getTables == 1) {
			
		}
		//准备sql
		PreparedStatement ps = con.prepareStatement(sql);
		//执行sql
		ResultSet rs = ps.executeQuery();
		List<String> dataBase = new ArrayList<>();
		//结果遍历
		while(rs.next()) {
			dataBase.add(rs.getString(1));
		}
		ConnectReturn cr = new ConnectReturn();
		//关闭连接
		ConnectDatabase.close(con, ps, rs);
		if(getTables != 1) {
			DataBaseInfo dbi = new DataBaseInfo(ip, port,
					userName, password, dataSource, nameOrSID, serverName);
			dataMapper.insertDataBase(dbi);
			cr.setId(dbi.getId());
		}
		cr.setDatabases(dataBase);
		return cr;
	}

	/**
	 * 连接数据库 并返回所有的数据库
	 */
	@Override
	public void saveDataInfoByConnect(String ip, String dataSource, String port, String userName,
			String password, String nameOrSID, String serverName, String currDatabase,int selectWay, String tableOrSql, String dataId) throws Exception {
		//获取驱动
		String driver = ConnectDatabase.getDriver(dataSource);
		//获取url
		String url = ConnectDatabase.getUrl(dataSource, ip, port, nameOrSID, serverName);
		
		url = url + "/" + currDatabase;//查所有库还是所有表
		//获取连接
		Connection con = ConnectDatabase.connect(driver, userName, password, url);
		//sql：查询所有的数据库
		String sql1 = "";
		String sql2 = "";
		if(Constants.ONE.equals(dataSource) && selectWay == 1) {
			//MySql 此选择了表 
			sql1 = "select COLUMN_NAME from information_schema.COLUMNS where table_name ='" + tableOrSql +"' and table_schema = '"+currDatabase+"'";
			sql2 = "select * from "+tableOrSql;
			saveRowAndColumnDatas(sql1, sql2, con, dataId);//保存
		}else if(Constants.ONE.equals(dataSource) && selectWay == 2) {
			//MySql 选择了写sql
			saveDataBySql(tableOrSql, con, dataId);
		}
	}
	
	private void saveRowAndColumnDatas(String sql1, String sql2, Connection con, String dataId) throws Exception{
		//准备sql
		PreparedStatement ps1 = con.prepareStatement(sql1);
		PreparedStatement ps2 = con.prepareStatement(sql2);
		//执行sql
		ResultSet rs1 = ps1.executeQuery();
		ResultSet rs2 = ps2.executeQuery();
		//结果遍历
		rs1.last();
		int length = rs1.getRow();//获取结果个数
		rs1.beforeFirst();
		int i = 1;
		while(rs1.next()) {//先遍历列名
			String data_columns = "";
			while(rs2.next()) {//遍历列数据
				data_columns = data_columns +(StringUtils.isEmpty(rs2.getString(i)) ? " ": rs2.getString(i)) + ",,";
			}
			rs2.beforeFirst();//让rs2遍历位回到首位 即没有执行这个方法 下次遍历会从当前索引开始遍历
			DataColumnDetail dcd = new DataColumnDetail();
			dcd.setDataId(dataId);
			dcd.setDataColumnName(rs1.getString(1));
			dcd.setColumnDatas(data_columns);
			dataMapper.insertColumn(dcd);//保存
			i++;
		}
		while(rs2.next()) {//遍历行数据
			String data_rows = "";
			for(int x = 1 ; x <= length; x++) {
				data_rows = data_rows + (StringUtils.isEmpty(rs2.getString(x)) ? " ": rs2.getString(x)) + ",,";
			}
			DataRowDetail drd = new DataRowDetail();
			drd.setDataId(dataId);
			drd.setRowDatas(data_rows);
			dataMapper.insertRow(drd);
		}
		//关闭连接
		ConnectDatabase.close(con, ps1, rs1);
		ConnectDatabase.close(con, ps2, rs2);
	}

	private void saveDataBySql(String sql, Connection con, String dataId) throws Exception{
		//准备sql
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		rs.last();
		int length = rs.getRow();
		rs.beforeFirst();
		while(rs.next()) {//保存行数据
			String data_rows = "";
			for(int x = 1 ; x <= length; x++) {
				data_rows = data_rows + (StringUtils.isEmpty(rs.getString(x)) ? " ": rs.getString(x)) + ",,";
			}
			DataRowDetail drd = new DataRowDetail();
			drd.setDataId(dataId);
			drd.setRowDatas(data_rows);
			dataMapper.insertRow(drd);
		}
		rs.beforeFirst();
		for (int i = 1; i <= length; i++) {
			DataColumnDetail dcd = new DataColumnDetail();
			dcd.setDataId(dataId);
			dcd.setDataColumnName("A"+i);
			String data_columns = "";
			while(rs.next()) {
				data_columns = data_columns +(StringUtils.isEmpty(rs.getString(i)) ? " ": rs.getString(i)) + ",,";
			}
			rs.beforeFirst();
			dcd.setColumnDatas(data_columns);
			dataMapper.insertColumn(dcd);//保存
		}
	}
	//处理保存文件内容
	@Override
	public void saveFile(String name, int dataUseWay, int dataTarget, int importWay, String equipment, MultipartFile file)
			throws Exception {

		//用于保存数据基本信息
		DataInfo data = new DataInfo(name, dataUseWay,dataTarget, importWay, equipment);
		saveDataInfo(data);
		String dataId = data.getId();
		//处理文件 并保存至数据库
		insert(file, dataId);
	}

	//从连接的库中将选择的表或者sql（执行）所有的数据全部保存至本地
	@Override
	public void saveByDatabase(String name, int dataUseWay, int dataTarget, int importWay, String equipment,
			String currDatabase, int selectWay, String tableOrSql, String databaseId) throws Exception {
		//用于保存数据基本信息
		DataInfo data = new DataInfo(name, dataUseWay,dataTarget, importWay, equipment);
		saveDataInfo(data);
		String dataId = data.getId();
		DataBaseInfo databaseInfo = dataMapper.getDatabaseInfoById(databaseId);
		saveDataInfoByConnect(databaseInfo.getIp(), databaseInfo.getDatabaseType(), databaseInfo.getPort(), 
				databaseInfo.getUserName(), databaseInfo.getPassword(),databaseInfo.getNameOrSID(), databaseInfo.getServerName(),
				currDatabase, selectWay, tableOrSql, dataId);
		//删除当前连接的数据库信息
		dataMapper.deleteDatabaseInfo(databaseId);
	}
}
