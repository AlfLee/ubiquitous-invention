package com.telecom.service.impl;


import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.telecom.entity.Equipment;
import com.telecom.mapper.EquipMapper;
import com.telecom.model.EquipTreeModel;
import com.telecom.service.EquipmentService;
import com.telecom.utils.Constants;
import com.telecom.utils.JsonTree;

import net.sf.json.JSONArray;

@Transactional
@Service
public class EquipmentServiceImpl implements EquipmentService {

	//引入equipMapper实例
	@Autowired
	private EquipMapper equipMapper;

	/**
	 * 给个默认根节点名称 ：所有设备（在application.properties中配置）
	 */
	@Value("${euipment.tree.root}")
	private String rootName;
	
	/**
	 * 给个默认根节点id ：0（在application.properties中配置）
	 */
	@Value("${euipment.tree.id}")
	private String rootId;
	
	/**
	 * 增
	 */
	@Override
	public void save(Equipment equip) {
		equipMapper.insert(equip);
	}

	/**
	 * 查
	 */
	@Override
	public Equipment getEquipByid(String id) {
		Equipment equip = equipMapper.getEquipByid(id);
		return equip;
	}

	/**
	 * 改
	 */
	@Override
	public void update(Equipment equip) {
		equipMapper.update(equip);
	}

	/**
	 * 删
	 */
	@Override
	public void delete(String id) {
		equipMapper.delete(id);
	}

	/**
	 * 设备树 业务层
	 */
	@Override
	public String getEquipJsonData() {
		
		//查出所有设备
		List<EquipTreeModel> equips = equipMapper.getAllEquip();
		
		//创建两个默认对象 ---> 用于创建根节点
		EquipTreeModel treeRoot = new EquipTreeModel();
		treeRoot.setId(rootId);
		equips.add(treeRoot);
		EquipTreeModel treeRoot1 = new EquipTreeModel();
		treeRoot1.setId(Constants.ONE);
		treeRoot1.setParentId(rootId);
		try {
			treeRoot1.setName(new String(rootName.getBytes("ISO8859-1"), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			treeRoot1.setName("所有设备");
			e.printStackTrace();
		}
		equips.add(treeRoot1);
		//遍历转换成json字符串
		JSONArray datas = JsonTree.treeMenuList(JSONArray.fromObject(equips), Constants.ZERO);
		return datas.toString();
	}

}
