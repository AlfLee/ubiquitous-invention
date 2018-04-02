package com.telecom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.telecom.entity.DataInfo;
import com.telecom.entity.GetModel;


public interface ModelTrainMapper {

	
	@SelectKey(keyProperty = "ModelId", resultType = String.class, before = true,keyColumn="ModelId",   
		    statement = "select replace(uuid(), '-', '') as ModelId from dual")//用于自动生成id
    @Insert("INSERT INTO model(ModelId,EquipmentId,ModelType,Modelparam)"
    		+ " VALUES(#{ModelId},#{equipid}, #{modeltype}, #{svmmodelparam})")
    void insertmodelparam(@Param("equipid") String equipid, @Param("modeltype") String modeltype,@Param("svmmodelparam") String svmmodelparam);
	
	@Select("<script>"
            + "select ModelId,EquipmentId,ModelType,Modelparam "
			+ "from model where ModelType='svm' and EquipmentId in "
           + "<foreach item='item' index='index' collection='equipIds' open='(' separator=',' close=')'>"
                + "#{item}"
            + "</foreach>"
                +" limit ${current},${pageSize}"//${}传参不加引号 #{}要加
        + "</script>")
	@Results({
        @Result(property = "id",  column = "ModelId"),
        @Result(property = "equipid",  column = "EquipmentId"),
        @Result(property = "modeltype",  column = "ModelType"),
        @Result(property = "param",  column = "Modelparam"),
    })
	List<GetModel> getmodelInfo(@Param("equipIds") List<String> equipIds,@Param("current") int current, @Param("pageSize") int pageSize);
	
	@Select("<script>"
            + "select id from equipment where parent_id in "
           + "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>"
                + "#{item}"
            + "</foreach>"
        + "</script>")
	List<String> getAllChildren(@Param("ids") List<String> ids);
	
	@Select("<script>"
            + "select count(*) "
			+ "from model where ModelType='svm' and EquipmentId in "
           + "<foreach item='item' index='index' collection='equipIds' open='(' separator=',' close=')'>"
                + "#{item}"
            + "</foreach>"//${}传参不加引号 #{}要加
        + "</script>")
	int svmmodelcount(@Param("equipIds") List<String> equipIds);
	
	
	@Select("select name from equipment where id=#{equipIds}")
	String getequipname(String equipIds);
	
	@Select("<script>"
            + "select id,data_name from data_info where equipment in "
           + "<foreach item='item' index='index' collection='equipIds' open='(' separator=',' close=')'>"
                + "#{item}"
            + "</foreach>"
        + "</script>")
	@Results({
        @Result(property = "id",  column = "id"),
        @Result(property = "dataName",  column = "data_name"),
    })
	List<DataInfo> getdataselect(@Param("equipIds") List<String> equipIds);
	
	
	@Delete("delete from model where ModelId=#{id}")
	void deletemodel(String id);
	
	
}
