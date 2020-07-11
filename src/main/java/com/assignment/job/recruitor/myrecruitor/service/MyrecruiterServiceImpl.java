package com.assignment.job.recruitor.myrecruitor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.job.recruitor.myrecruitor.entities.Application;
import com.assignment.job.recruitor.myrecruitor.entities.Offer;
import com.assignment.job.recruitor.myrecruitor.exception.CustomException;
import com.assignment.job.recruitor.myrecruitor.exception.RecordNotFoundException;
import com.assignment.job.recruitor.myrecruitor.repo.ApplicationRepository;
import com.assignment.job.recruitor.myrecruitor.repo.OfferRepository;

@Service
public class MyrecruiterServiceImpl implements MyrecruiterService{

	@Autowired
	private ApplicationRepository applicationRepo;
	
	@Autowired
	private OfferRepository offerRepo;
	
	
	@Override
	public List<Offer> getAllOffers() {
		return offerRepo.findAll();
	}


	@Override
	public Offer getOfferById(Long jobId) {
		return offerRepo.findById(jobId).get();
	}


	@Override
	public Offer getOfferByTitle(String jobTitle) {
		return offerRepo.findByJobTitle(jobTitle);
	}


	@Override
	public Offer createOffer(Offer offer) throws CustomException {
		try {
			offerRepo.save(offer);
		} catch (Exception e) {
			throw new CustomException("Unable to create Dublicate Job Id");
		}
		return offer;
	}


	@Override
	public List<Application> getApplicationByJobId(Long jobId) throws RecordNotFoundException{
		Offer offer = getOfferById(jobId);
		if(offer == null) {
			throw new RecordNotFoundException("Job Id Not Found");
		}
		return applicationRepo.findAllByRelatedOffer(offer.getJobTitle());
	}


	@Override
	public List<Application> getApplicationByJobTitle(String jobTitle) {
		return applicationRepo.findAllByRelatedOffer(jobTitle);
	}


	@Override
	public Application submitApplication(Application application) throws CustomException{
		Offer offer = getOfferByTitle(application.getRelatedOffer());
		if(offer == null) {
			throw new RecordNotFoundException("Title: " + application.getRelatedOffer() +" Not Found"); 	
		}
		try {
			applicationRepo.save(application);
			offer.setNoOfApplications(offer.getNoOfApplications()+1);
			offerRepo.save(offer);
		} catch (Exception e) {
			throw new CustomException("Email Id Already Registered");
		}
		return application;
		
	}




	@Override
	public List<Application> getAllApplications() {
		return applicationRepo.findAll();
	}


	@Override
	public Application getApplicationById(Long applicationId) {
		return applicationRepo.findById(applicationId).get();
	}


}
