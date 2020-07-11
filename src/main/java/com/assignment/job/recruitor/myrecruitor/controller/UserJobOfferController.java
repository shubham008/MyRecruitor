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

import com.assignment.job.recruitor.myrecruitor.entities.Offer;
import com.assignment.job.recruitor.myrecruitor.exception.CustomException;
import com.assignment.job.recruitor.myrecruitor.exception.RecordNotAvailableException;
import com.assignment.job.recruitor.myrecruitor.exception.RecordNotFoundException;
import com.assignment.job.recruitor.myrecruitor.service.MyrecruiterService;

@RestController
@RequestMapping("/user")
public class UserJobOfferController {
	
	@Autowired
	private MyrecruiterService myrecruiterService;
	
	@GetMapping("/offers")
	public List<Offer> getAllOffers(){
		List<Offer> offers = myrecruiterService.getAllOffers();
		if(offers == null || offers.isEmpty()) {
			throw new RecordNotAvailableException("No Job Offers Added.");
		}
		return offers;
	}
	
	@GetMapping("/offers/id/{jobId}")
	public Offer getOfferById(@PathVariable Long jobId) {
		Offer offer = myrecruiterService.getOfferById(jobId);
		if(offer == null) {
			throw new RecordNotFoundException("Id: " + jobId+" Not Found");
		}
		return offer;
	}
	
	@GetMapping("/offers/title/{jobTitle}")
	public Offer getOfferByTitle(@PathVariable String jobTitle) {
		Offer offer = myrecruiterService.getOfferByTitle(jobTitle);
		if(offer == null) {
			throw new RecordNotFoundException("Title: " + jobTitle+" Not Found");
		}
		return offer;
	}
	
	@PostMapping("/offers")
	public ResponseEntity<Object> createOffer(@RequestBody Offer newOffer) throws CustomException{
		try {
			Offer offer = myrecruiterService.createOffer(newOffer);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{jobId}")
					.buildAndExpand(offer.getJobId())
					.toUri();
			
			return ResponseEntity.created(location).build();
		} catch(CustomException e) {
			throw e;
		}
	}

}
