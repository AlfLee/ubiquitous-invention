package com.telecom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LifetimeController {
	
	@RequestMapping("/predict")
	public String predict() {
		return "predict";  
	}

}

