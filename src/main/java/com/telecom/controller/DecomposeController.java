package com.telecom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class DecomposeController {

	 @RequestMapping("timeDomain")
		public String timeDomain() {
			return "timeDomain";  
		}
		@RequestMapping("frequencyDomain")
		public String frequencyDomain() {
			return "frequencyDomain";  
		}
		@RequestMapping("timeFrequency")
		public String timeFrequency() {
			return "timeFrequency";  
		}
    }
