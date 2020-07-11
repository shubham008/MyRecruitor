package com.assignment.job.recruitor.myrecruitor.service;

import java.util.List;

import com.assignment.job.recruitor.myrecruitor.entities.Application;
import com.assignment.job.recruitor.myrecruitor.entities.Offer;
import com.assignment.job.recruitor.myrecruitor.exception.CustomException;
import com.assignment.job.recruitor.myrecruitor.exception.RecordNotFoundException;


public interface MyrecruiterService {
	
	public List<Offer> getAllOffers();
	
	public Offer getOfferById(Long jobId);
	
	public Offer getOfferByTitle(String jobTitle);
	
	public Offer createOffer(Offer offer) throws CustomException;
	
	public List<Application> getAllApplications();
	
	public List<Application> getApplicationByJobId(Long jobId)  throws RecordNotFoundException;
	
	public List<Application> getApplicationByJobTitle(String jobTitle);
	
	public Application submitApplication(Application application) throws CustomException;

	public Application getApplicationById(Long applicationId);
	
}
