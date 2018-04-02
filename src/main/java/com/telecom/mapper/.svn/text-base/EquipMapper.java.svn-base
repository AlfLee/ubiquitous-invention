package com.telecom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.telecom.entity.Equipment;
import com.telecom.model.EquipTreeModel;

/**
 * 设备的增删查改
 * @author Administrator
 *
 */
public interface EquipMapper {

	/**
	 * 保存数据
	 * @param equip
	 */
	@SelectKey(keyProperty = "id", resultType = String.class, before = true,keyColumn="id",   
		    statement = "select replace(uuid(), '-', '') as id from dual")//用于自动生成id
    @Insert("INSERT INTO equipment(id,name,parent_id,modify_time,create_time,created_user,detail)"
    		+ " VALUES(#{id},#{name}, #{parentId}, #{modifyTime}, #{createTime}, #{createdUser},#{detail})")
    void insert(Equipment equip);
	
	/**
	 * 同时更新  name  modify_time  detail三个字段
	 * @param equip
	 */
	@Update("update equipment set name=#{name},modify_time=#{modifyTime},detail=#{detail} where id=#{id}")
	void update(Equipment equip);

	/**
	 * 删除
	 * @param id
	 */
	@Delete("delete from equipment where id=#{id}")
	void delete(String id);
	
	/**
	 * 查看
	 * @param id
	 * @return
	 */
	@Select("select name,detail from equipment where id=#{id}")
	@Results({
        @Result(property = "name",  column = "name"),
        @Result(property = "detail",  column = "detail")
    })
	Equipment getEquipByid(String id);
	
	/**
	 * 查看
	 * @param id
	 * @return
	 */
	@Select("select id,name,parent_id from equipment")
	@Results({
        @Result(column="id",property="id"),
        @Result(column="name",property="name"),
        @Result(property="parentId",column="parent_id")
    })
	List<EquipTreeModel> getAllEquip();
}

