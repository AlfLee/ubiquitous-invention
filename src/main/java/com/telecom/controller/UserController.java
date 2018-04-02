package com.telecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.telecom.entity.user_entity;
import com.telecom.service.user_service;
import com.telecom.utils.QResponseVo;

import net.sf.json.JSONObject;


@Controller
public class UserController {

	@RequestMapping("/user")
	public String user() {
		return "user";  
	}
	@RequestMapping("/edituser")
	public String edituser() {
		return "edituser";  
	}
	
}
