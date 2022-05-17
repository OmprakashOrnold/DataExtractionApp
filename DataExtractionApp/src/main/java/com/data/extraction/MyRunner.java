package com.data.extraction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.data.extraction.service.BuiltinDirectoryService;

@Component
public class MyRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MyRunner.class);

    @Autowired
    private BuiltinDirectoryService builtinDirectoryService;
  
    @Override
    public void run(String... args) throws Exception {
    	logger.info("Started MyRunner of CommandLineRunner");
    	builtinDirectoryService.runService();
    	logger.info("Ended MyRunner of CommandLineRunner");
    }
}