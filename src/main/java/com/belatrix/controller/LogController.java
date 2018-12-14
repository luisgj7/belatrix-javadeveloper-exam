package com.belatrix.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.belatrix.service.JobLoggerService;

@Controller
@RequestMapping("/")
public class LogController {
	
	private static final Logger LOGGER = LogManager.getLogger(LogController.class);
	
	
	@Autowired
	@Qualifier("jobLoggerService")
	private JobLoggerService jobLoggerService;
	
	@GetMapping("/")
	public String redirect() {
		return "redirect:/index";
	}
	
	
	@GetMapping("/index")
	public String index() {
		try {
			jobLoggerService.LogMessage("Testing BelatrixLog from SpringBoot Controller", true, true, true);
		} catch (Exception e) {
			LOGGER.error("hello",e);
		}
		return "index";
	}
	
	
	
}
