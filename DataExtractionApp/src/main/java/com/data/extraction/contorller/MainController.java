package com.data.extraction.contorller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.data.extraction.service.SmallBusinessAmericanDirectoryServiceImpl;

@Controller
public class MainController {
	
	@Autowired
    private SmallBusinessAmericanDirectoryServiceImpl smallBusinessAmericanDirectoryServiceImpl;
	
	@GetMapping("/start")
	@ResponseBody
	public String servcieStart() {
		smallBusinessAmericanDirectoryServiceImpl.runService();
		return "started:";
	}
}
