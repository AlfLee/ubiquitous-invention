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
public class LoginController {

	@RequestMapping("/login")
	public String m() {
		return "index";  
	}
	
	@Autowired
	private user_service userService;
	
	@RequestMapping("/index")
	public String login(@RequestParam(value="userName",defaultValue="") String username,
			@RequestParam(value="password",defaultValue="") String password,ModelMap model) {
		   model.addAttribute("username", username);
		boolean flag = userService.getNamePass(username, password);
		if(flag)
			return "manus"; 
	    else
			return "index";
	}
	
	@RequestMapping("/getusers")
	@ResponseBody
	public String getusers() {
		QResponseVo<List<user_entity>> result = new QResponseVo<List<user_entity>>();
		result.setData(userService.getAll());
		result.setCount(1);
		JSONObject json = JSONObject.fromObject(result);
		String a = json.toString();
		return a;
	}
	
}
