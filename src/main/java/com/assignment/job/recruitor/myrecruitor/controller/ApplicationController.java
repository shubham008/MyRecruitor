package com.assignment.job.recruitor.myrecruitor.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.assignment.job.recruitor.myrecruitor.entities.Application;
import com.assignment.job.recruitor.myrecruitor.entities.ApplicationStatus;
import com.assignment.job.recruitor.myrecruitor.exception.CustomException;
import com.assignment.job.recruitor.myrecruitor.exception.RecordNotAvailableException;
import com.assignment.job.recruitor.myrecruitor.exception.RecordNotFoundException;
import com.assignment.job.recruitor.myrecruitor.service.MyrecruiterService;

@RestController
@RequestMapping("/candidate")
public class ApplicationController {

	
	@Autowired
	private MyrecruiterService myrecruiterService;
	
	@GetMapping("/applications")
	public List<Application> getAllApplications(){
		List<Application> applications = myrecruiterService.getAllApplications();
		if(applications == null || applications.isEmpty()) {
			throw new RecordNotAvailableException("No Applications Applied.");
		}
		return applications;
		
	}
	@GetMapping("/applications/{applicationId}")
	public Application getApplicationById(@PathVariable Long applicationId){
		Application application = myrecruiterService.getApplicationById(applicationId);
		if(application == null) {
			throw new RecordNotFoundException("Application Id: "+ applicationId + " not Found");
		}
		return application;
		
	}
	
	@PostMapping("/applications")
	public ResponseEntity<Object> submitApplication(@RequestBody Application newApplication) throws CustomException{
		try {
			Application application = myrecruiterService.submitApplication(newApplication);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{applicationId}")
					.buildAndExpand(application.getApplicationId())
					.toUri();
			
			return ResponseEntity.created(location).build();
		} catch (CustomException e) {
			throw e;
		}
	}

	@GetMapping("/applications/offers/id/{jobId}")
	public List<Application> getAllApplicationsByJobId(@PathVariable Long jobId)  throws RecordNotFoundException{
		try {
			List<Application> applications = myrecruiterService.getApplicationByJobId(jobId);
			if(applications == null || applications.isEmpty()) {
				throw new RecordNotAvailableException("No Application For this Job Id Available");
			}
			return applications;
			
		} catch (RecordNotFoundException e) {
			throw e;
		}
		
	}
	 
	@GetMapping("/applications/offers/title/{jobTitle}")
	public List<Application> getAllApplicationsByJobTitle(@PathVariable String jobTitle){
		List<Application> applications = myrecruiterService.getApplicationByJobTitle(jobTitle);
		if(applications == null || applications.isEmpty()) {
			throw new RecordNotAvailableException("No Application For this Job Title Available");
		}
		return applications;
		
	}
	
}
