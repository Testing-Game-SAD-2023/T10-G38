package com.T07.compExec.api.controller;

import java.util.logging.Logger;
import com.T07.compExec.api.model.CompExecResults;
import com.T07.compExec.api.model.UrlClassToTest;
import com.T07.compExec.service.CompExecService;

import exceptions.RandoopException;
import randoop.RandoopConnector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class CompExecController {
	
	private static final Logger logger = Logger.getLogger(CompExecController.class.getName());
	
    private CompExecService compExecService;

    @Autowired
    public CompExecController (CompExecService compExecService) {
        this.compExecService = compExecService;
    }

    @GetMapping("/compexec")
    public CompExecResults getCompExec(@RequestParam String ClassName, @RequestParam String StudentLogin, @RequestParam String GameId) {
        
    	return compExecService.getCompExecResults(ClassName, StudentLogin, GameId);
    }

    @PostMapping("/compexecurl")
    public CompExecResults getCompExecUrl(@RequestBody UrlClassToTest urls, @RequestParam String ClassName, @RequestParam String StudentLogin, @RequestParam String GameId) {
        return compExecService.getCompExecResultsUrl(urls.getUrlClass(), urls.getUrlTestClass(), ClassName, StudentLogin, GameId);
    }
    
    @GetMapping("/Randoop")
    public String Randoopest(@RequestParam String ClassName){
    	
    	logger.info("Get Randoop Received");
    	
    	RandoopConnector con = new RandoopConnector();
    	double coverage = -2;
    	try {
			coverage = con.generateRandoopTest(ClassName, 3);
		} catch (RandoopException e) {
			e.printStackTrace();
		}
    	
    	return String.valueOf(coverage);
    }

}
