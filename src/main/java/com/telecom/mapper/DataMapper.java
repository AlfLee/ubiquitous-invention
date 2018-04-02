package com.telecom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.telecom.entity.DataBaseInfo;
import com.telecom.entity.DataColumnDetail;
import com.telecom.entity.DataInfo;
import com.telecom.entity.DataRowDetail;

public interface DataMapper {

	/**
	 * 保存数据基本信息
	 * @param dataInfo
	 */
	@SelectKey(keyProperty = "id", resultType = String.class, before = true,keyColumn="id",   
		    statement = "select replace(uuid(), '-', '') as id from dual")//用于自动生成id
    @Insert("INSERT INTO data_info(id,data_name,data_use_way,data_target,import_way,import_time,equipment)"
    		+ " VALUES(#{id},#{dataName}, #{dataUseWay}, #{dataTarget}, #{importWay}, #{importTime},#{equipment})")
    void insertDataInfo(DataInfo dataInfo);
	
	/**
	 * 保存列数据
	 * @param dcd
	 */
	@SelectKey(keyProperty = "id", resultType = String.class, before = true,keyColumn="id",   
		    statement = "select replace(uuid(), '-', '') as id from dual")//用于自动生成id
	@Insert("insert into data_column_detail(id,data_id,data_column_name,column_datas)"
			+ " values(#{id},#{dataId},#{dataColumnName},#{columnDatas})")
	void insertColumn(DataColumnDetail dcd);
	
	/**
	 * 保存行数据
	 * @param dcd
	 */
	@SelectKey(keyProperty = "id", resultType = String.class, before = true,keyColumn="id",   
		    statement = "select replace(uuid(), '-', '') as id from dual")//用于自动生成id
	@Insert("insert into data_rows_detail(id,data_id,row_datas) values(#{id},#{dataId},#{rowDatas})")
	void insertRow(DataRowDetail drd);
	
	/**
	 * 根据设备id查找 该设备下的所有数据 script写法
	 * @param equipId
	 * @return
	 */
	@Select("<script>"
            + "select id,data_name,data_use_way,data_target,"
			+ "import_way,(SELECT FROM_UNIXTIME(import_time/1000,'%Y-%m-%d %H:%i')) as importTimeS "
			+ "from data_info where equipment in "
           + "<foreach item='item' index='index' collection='equipIds' open='(' separator=',' close=')'>"
                + "#{item}"
            + "</foreach>"
                +" ${conditions} order by import_time desc limit ${current},${pageSize}"//${}传参不加引号 #{}要加
        + "</script>")
	@Results({
        @Result(property = "id",  column = "id"),
        @Result(property = "dataName",  column = "data_name"),
        @Result(property = "dataUseWay",  column = "data_use_way"),
        @Result(property = "dataTarget",  column = "data_target"),
        @Result(property = "importWay",  column = "import_way"),
        @Result(property = "import_time",  column = "importTimeS")
    })
	List<DataInfo> getDataInfo(@Param("equipIds") List<String> equipIds, @Param("conditions") String conditions, @Param("current") int current, @Param("pageSize") int pageSize);
	

	/**
	 * 根据设备id查找 该设备下的数据个数 script写法
	 * @param equipId
	 * @return
	 */
	@Select("<script>"
            + "select count(id)"
			+ "from data_info where equipment in "
           + "<foreach item='item' index='index' collection='equipIds' open='(' separator=',' close=')'>"
                + "#{item}"
            + "</foreach>"
                +" ${conditions}"//${}传参不加引号 #{}要加
        + "</script>")
	int getDataInfoCount(@Param("equipIds") List<String> equipIds, @Param("conditions") String conditions);
	
	
	@Select("<script>"
            + "select id from equipment where parent_id in "
           + "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>"
                + "#{item}"
            + "</foreach>"
        + "</script>")
	List<String> getAllChildren(@Param("ids") List<String> ids);

	/**
	 * 同时更新  name  modify_time  detail三个字段
	 * @param equip
	 */
	@Update("update data_info set data_name=#{dataName},modify_time=#{modifyTime},data_use_way=#{dataUseWay},data_target=#{dataTarget} where id=#{id}")
	void update(DataInfo data);

	/**
	 * 删除
	 * @param id
	 */
	@Delete("delete from data_info where id=#{id}")
	void delete(String id);
	
	/**
	 * 查找
	 * @param id
	 */
	@Select("select id,data_name,data_use_way,data_target from data_info where id=#{dataId}")
	@Results({
        @Result(property = "id",  column = "id"),
        @Result(property = "dataName",  column = "data_name"),
        @Result(property = "dataUseWay",  column = "data_use_way"),
        @Result(property = "dataTarget",  column = "data_target"),
    })
	DataInfo getOne(String dataId);
	
	/**
	 * 查找所有变量名
	 * @param dataId
	 * @return
	 */
	@Select("select data_column_name from data_column_detail where data_id=#{dataId}")
	List<Object> getDataVariables(String dataId);
	
	/**
	 * 查找所有行数据
	 * @param dataId
	 * @return
	 */
	@Select("select id,row_datas from data_rows_detail where data_id=#{dataId} limit ${current},${pageSize}")
	@Results({
        @Result(property = "id",  column = "id"),
        @Result(property = "rowDatas",  column = "row_datas"),
    })
	List<DataRowDetail> getDataRowDetail(@Param("dataId")String dataId, @Param("current")int current, @Param("pageSize")int pageSize);
	
	/**
	 * 查找所有行数据个数
	 * @param dataId
	 * @return
	 */
	@Select("select count(id) from data_rows_detail where data_id=#{dataId}")
	int getDataRowDetailCount(@Param("dataId")String dataId);
	
	/**
	 * 查找当前行的数据
	 * @param id
	 * @return
	 */
	@Select("select row_datas from data_rows_detail where id=#{id}")
	String getDataRowById(@Param("id")String id);
	
	/**
	 * 查找当前列的数据
	 * @param dataId
	 * @return
	 */
	@Select("select column_datas from data_column_detail where data_column_name=#{columnName}")
	String getDataColumnByColumnName(@Param("columnName")String columnName);
	
	/**
	 * 只更新修改过行
	 * @param 
	 */
	@Update("update data_rows_detail set row_datas=#{rowDatas},modify_time=NOW() where id=#{id}")
	void updateDataRow(@Param("id")String id, @Param("rowDatas")String rowDatas);
	
	/**
	 * 只更新修改过列
	 * @param 
	 */
	@Update("update data_column_detail set column_datas=#{columnDatas},modify_time=NOW() where data_column_name=#{dataColumnName}")
	void updateDataColumn(@Param("dataColumnName")String dataColumnName, @Param("columnDatas")String columnDatas);
	
	/**
	 * 连接成功 保存连接信息
	 * @param dbi
	 */
	@SelectKey(keyProperty = "id", resultType = String.class, before = true,keyColumn="id",   
		    statement = "select replace(uuid(), '-', '') as id from dual")//用于自动生成id
	@Insert("insert into database_info(id,ip,port,user_name,password,database_type,name_or_SID,server_name) "
			+ "values(#{id},#{ip},#{port},#{userName},#{password},#{databaseType},#{nameOrSID},#{serverName})")
	void insertDataBase(DataBaseInfo dbi);
	
	/**
	 * 从库中找到临时保存的数据库连接信息
	 * @param databaseId
	 * @return
	 */
	@Select("select * from database_info where id=#{databaseId}")
	@Results({
        @Result(property = "ip",  column = "ip"),
        @Result(property = "port",  column = "port"),
        @Result(property = "databaseType",  column = "database_type"),
        @Result(property = "password",  column = "password"),
        @Result(property = "userName",  column = "user_name"),
        @Result(property = "nameOrSID",  column = "name_or_SID"),
        @Result(property = "serverName",  column = "server_name"),
    })
	DataBaseInfo getDatabaseInfoById(String databaseId);
	
	/**
	 * 删除
	 */
	@Delete("delete from database_info where id=#{databaseId}")
	void deleteDatabaseInfo(String databaseId);

}

