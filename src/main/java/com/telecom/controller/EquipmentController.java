package com.telecom.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.telecom.entity.Equipment;
import com.telecom.service.EquipmentService;
import com.telecom.utils.Constants;
import com.telecom.utils.QResponseVo;
import com.telecom.utils.QResponseVo.CODE;

@Controller
public class EquipmentController {
	Logger logger = LoggerFactory.getLogger(EquipmentController.class);
	
	@Autowired
	private EquipmentService equipService;
	
	/**
	 * 给个默认根节点名称 ：所有设备（在application.properties中配置）
	 */
	@Value("${euipment.tree.root}")
	private String rootName;
	
	/**
	 * 保存或者更新设备 
	 * @param equip
	 * @return
	 */
	@RequestMapping("/saveEquip")
	@ResponseBody
	public QResponseVo<String> saveEquip(@RequestParam(required=true,name="flag") String flag,
			@RequestParam(required=true,name="id") String id,
			@RequestParam(required=true,name="parentId") String parentId,
			@RequestParam(required=true,name="name") String name,
			@RequestParam(required=true,name="detail", defaultValue="") String detail){
		QResponseVo<String> result = new QResponseVo<String>();
		Equipment equip = new Equipment();
		equip.setDetail(detail);
		equip.setParentId(parentId);
		equip.setName(name);
		try {
			
			if(Constants.ZERO.equals(flag)) {
				equip.setCreateTime(System.currentTimeMillis());
				equipService.save(equip);
			}else {
				equip.setId(id);
				equip.setModifyTime(System.currentTimeMillis());
				equipService.update(equip);
			}
			result.setCode(CODE.Success);
		}catch(Throwable th) {
			logger.error("save equipment has due to:",th);
			result.setCode(CODE.Fail);
		}
		return result;
	}
	
	/**
	 * 查
	 * @param id
	 * @return
	 */
	@RequestMapping("/getEquip")
	@ResponseBody
	public QResponseVo<String> getEquipByid(@RequestParam(required=true) String id,
			@RequestParam(required=true) String parentId){
		QResponseVo<String> result = new QResponseVo<String>();
		try {
			Equipment equip = equipService.getEquipByid(id);
			String parentName = "";
			if(!Constants.ONE.equals(parentId)) {
				Equipment quip = equipService.getEquipByid(parentId);
				if(quip != null) {
					parentName = quip.getName();
				}
			}else {
				parentName = new String(rootName.getBytes("ISO8859-1"), "UTF-8");
			}
			result.setCode(CODE.Success);
			//只返回name detail
			result.setAction(id+"");
			result.setData(parentName);
			result.setMsg(equip == null ? "" :equip.getDetail());
		}catch(Throwable th) {
			logger.error("get equipment has due to:",th);
			result.setCode(CODE.Fail);
		}
		return result;
	}
	
	/**
	 * 删
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteEquip")
	@ResponseBody
	public QResponseVo<String> delete(@RequestParam(required=true) String id){
		QResponseVo<String> result = new QResponseVo<String>();
		try {
			equipService.delete(id);
			result.setCode(CODE.Success);
		}catch(Throwable th) {
			logger.error("delete equipment has due to:",th);
			result.setCode(CODE.Fail);
		}
		return result;
	}
	
	
	@RequestMapping("/equipTree")
	public String equipTree(Model model) {
		String datas = equipService.getEquipJsonData();
		model.addAttribute("datas", datas);
		return "equiptree";
	}
	
	@RequestMapping("/equipment")
	public String equipment() {
		return "equipment";
	}
	@RequestMapping("/editEquipment")
	public String edit() {
		return "editequipment";
	}
}

	

