package com.telecom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.telecom.entity.Fault_entity;
import com.telecom.entity.GetModel;

public interface FaultMapper {

	@Select("<script>"
            + "select id from equipment where parent_id in "
           + "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>"
                + "#{item}"
            + "</foreach>"
        + "</script>")
	List<String> getAllChildren(@Param("ids") List<String> ids);
	
	@Select("<script>"
            + "select ModelId,EquipmentId,ModelType,Modelparam "
			+ "from model where ModelType=#{algType} and EquipmentId in "
           + "<foreach item='item' index='index' collection='equipIds' open='(' separator=',' close=')'>"
                + "#{item}"
            + "</foreach>"
        + "</script>")
	@Results({
        @Result(property = "id",  column = "ModelId"),
        @Result(property = "equipid",  column = "EquipmentId"),
        @Result(property = "modeltype",  column = "ModelType"),
        @Result(property = "param",  column = "Modelparam"),
    })
	List<GetModel> getmodel(@Param("equipIds") List<String> equipIds,@Param("algType") String algType);
	
	@Select("select name from equipment where id=#{equipIds}")
	String getequipname(String equipIds);
	
	@Select("<script>"
            + "select FaultId,IsFault,EquipName,ModelParam,Time,EquipId,AlgType,FaultData "
			+ "from fault where EquipId in "
           + "<foreach item='item' index='index' collection='equipIds' open='(' separator=',' close=')'>"
                + "#{item}"
            + "</foreach>"
                +" limit ${current},${pageSize}"//${}传参不加引号 #{}要加
        + "</script>")
	@Results({
        @Result(property = "id",  column = "FaultId"),
        @Result(property = "faultresult",  column = "IsFault"),
        @Result(property = "equipname",  column = "EquipName"),
        @Result(property = "time",  column = "Time"),
        @Result(property = "algparam",  column = "ModelParam"),
        @Result(property = "equipid",  column = "EquipId"),
        @Result(property = "algtype",  column = "AlgType"),
        @Result(property = "faultdata",  column = "FaultData"),
    })
	List<Fault_entity> getallfault(@Param("equipIds") List<String> equipIds,@Param("current") int current, @Param("pageSize") int pageSize);
	
	@Select("<script>"
            + "select count(*) "
			+ "from fault where EquipId in "
           + "<foreach item='item' index='index' collection='equipIds' open='(' separator=',' close=')'>"
                + "#{item}"
            + "</foreach>"//${}传参不加引号 #{}要加
        + "</script>")
	int getfaultcount(@Param("equipIds") List<String> equipIds);
	
	@Select("select row_datas from data_rows_detail where data_id=#{dataid}")
	List<String> getsqldata(@Param("dataid") String dataid);
	
	@Select("select Model from model where ModelId=#{modelid}")
	String modelfault(@Param("modelid") String modelid);
	
	@SelectKey(keyProperty = "FaultId", resultType = String.class, before = true,keyColumn="FaultId",   
		    statement = "select replace(uuid(), '-', '') as FaultId from dual")//用于自动生成id
    @Insert("INSERT INTO fault(FaultId,IsFault,EquipName,ModelParam,Time,AlgType,EquipId,FaultData)"
    		+ " VALUES(#{FaultId},#{faultresult}, #{EquipName}, #{faultparam}, #{time}, #{alg_type}, #{equipid},#{faultdata})")
	public void insertresult(@Param("faultresult") String faultresult,@Param("EquipName") String EquipName,@Param("faultparam") String faultparam,@Param("alg_type") String alg_type,@Param("equipid") String equipid,@Param("time") String time,@Param("faultdata") String faultdata);
	
	@Select("select FaultData from fault where FaultId=#{FaultDataId}")
	String getsimpledata(@Param("FaultDataId") String FaultDataId);
}
