package com.data.extraction.contorller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.data.extraction.service.BuiltinDirectoryService;
import com.data.extraction.service.SmallBusinessAmericanDirectoryService;

@Controller
public class MainController {
	
	@Autowired
    private SmallBusinessAmericanDirectoryService smallBusinessAmericanDirectoryService;
	
	@Autowired
    private BuiltinDirectoryService builtinDirectoryService;
	
	
	@GetMapping("/start")
	@ResponseBody
	public String servcieStart() {
		//smallBusinessAmericanDirectoryService.runService();
		builtinDirectoryService.runService();
		return "started:";
	}
}
