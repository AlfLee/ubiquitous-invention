package com.telecom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FaultController {
	
	@RequestMapping("/analysis")
	public String analysis() {
		return "analysis";  
	}

}