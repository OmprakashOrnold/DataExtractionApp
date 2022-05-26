package com.data.extraction.contorller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.data.extraction.service.AIHitdataDirectoryService;
import com.data.extraction.service.BuiltinDirectoryService;
import com.data.extraction.service.Saastrannual2022SponsersService;
import com.data.extraction.service.SmallBusinessAmericanDirectoryService;
import com.data.extraction.service.impl.JmiServiceImpl;

@Controller
public class MainController {
	
	@Autowired
    private SmallBusinessAmericanDirectoryService smallBusinessAmericanDirectoryService;
	
	@Autowired
    private BuiltinDirectoryService builtinDirectoryService;
	
	@Autowired
	private AIHitdataDirectoryService aIHitdataDirectoryService;
	
	@Autowired
	private Saastrannual2022SponsersService saastrannual2022SponsersService;
	
	@Autowired
	private JmiServiceImpl jmiServiceImpl;
	
	@GetMapping("/start")
	@ResponseBody
	public String servcieStart() {
		//smallBusinessAmericanDirectoryService.runService();
		//builtinDirectoryService.runService();
		//aIHitdataDirectoryService.runService();
		//saastrannual2022SponsersService.runService();
		jmiServiceImpl.runService();
		return "started:";
	}
}
