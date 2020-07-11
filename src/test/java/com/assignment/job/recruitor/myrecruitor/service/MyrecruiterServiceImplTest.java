package com.assignment.job.recruitor.myrecruitor.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.assignment.job.recruitor.myrecruitor.entities.Application;
import com.assignment.job.recruitor.myrecruitor.entities.ApplicationStatus;
import com.assignment.job.recruitor.myrecruitor.entities.Offer;
import com.assignment.job.recruitor.myrecruitor.exception.CustomException;
import com.assignment.job.recruitor.myrecruitor.exception.RecordNotFoundException;
import com.assignment.job.recruitor.myrecruitor.repo.ApplicationRepository;
import com.assignment.job.recruitor.myrecruitor.repo.OfferRepository;

@TestInstance(Lifecycle.PER_CLASS)
public class MyrecruiterServiceImplTest {
	
	@InjectMocks
    private MyrecruiterServiceImpl impl;
	
	@Mock
    private OfferRepository offerRepo;
	
	@Mock
	private ApplicationRepository applicationRepo;
	
	@Autowired 
	private MockMvc mvc;
	
	@BeforeAll
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(impl).build();
        
    }
	
	@Test
	public void testGetOfferByTitleNullCheck() {
		Mockito.when(offerRepo.findByJobTitle("Java")).thenReturn(null);
		Offer offers = impl.getOfferByTitle("Java");
		assertNull(offers);
	}
	@Test
	public void testGetOfferByTitle() {
		Offer offer = new Offer("Java Devloper", new Date(), 0);
		Mockito.when(offerRepo.findByJobTitle("Java Devloper")).thenReturn(offer);
		List<Offer> offers = new ArrayList<Offer>();
		offer.setJobId(2L);
		offers.add(offer);
		Offer resultOffer = impl.getOfferByTitle("Java Devloper");
		assertNotNull(resultOffer);
		assertEquals("Java Devloper", resultOffer.getJobTitle());
		assertEquals(2L, resultOffer.getJobId());
		assertEquals(0, resultOffer.getNoOfApplications());
	}
	
	@Test
	public void testGetAllOffersNullCheck() {
		Mockito.when(offerRepo.findAll()).thenReturn(null);
		List<Offer> offers = impl.getAllOffers();
		assertNull(offers);
	}
	@Test
	public void testGetAllOffersEmptyCheck() {
		Mockito.when(offerRepo.findAll()).thenReturn(new ArrayList<Offer>());
		List<Offer> offers = impl.getAllOffers();
		assertNotNull(offers);
	}
	@Test
	public void testGetAllOffers() {
		List<Offer> offers = new ArrayList<Offer>();
		Offer newOffer = new Offer("Java", new Date(), 0);
		newOffer.setJobId(1L);
		offers.add(newOffer);
		Mockito.when(offerRepo.findAll()).thenReturn(offers);
		List<Offer> resultOffer = impl.getAllOffers();
		assertNotNull(resultOffer);
		assertEquals("Java", resultOffer.get(0).getJobTitle());
		assertEquals(1L, resultOffer.get(0).getJobId());
		assertEquals(0, resultOffer.get(0).getNoOfApplications());
	}
	
	


	@Test
	public void testCreateOffer() {
		Offer offer = new Offer("Java Devloper", new Date(), 0);
		offer.setJobId(1L);
		Mockito.when(offerRepo.save(offer)).thenReturn(null);
		Offer offers = impl.createOffer(offer);
		assertNotNull(offers);
		assertEquals("Java Devloper", offers.getJobTitle());
		assertEquals(1L, offers.getJobId());
		assertEquals(0, offers.getNoOfApplications());
	}
	
	@Test
	public void testCreateOfferNullCheck() {
		Offer offers = impl.createOffer(null);
		assertNull(offers);
	}
	@Test
	public void testCreateOfferException() {
		Mockito.when(offerRepo.save(null)).thenThrow(new CustomException("Unable to create Dublicate Job Id"));
		try{
			Offer offers = impl.createOffer(null);
		}catch (CustomException e) {
			assertEquals("Unable to create Dublicate Job Id", e.getMessage());
		}
		
	}
	
	@Test
	public void testGetApplicationByJobIdNullCheck() {
		Mockito.when(applicationRepo.findAllByRelatedOffer("Java DevOps")).thenReturn(null);
		List<Application> applicationByJobId = impl.getApplicationByJobTitle("Java DevOps");
		assertNull(applicationByJobId);
	}
	
	@Test
	public void testGetApplicationByJobId() {
		List<Application> applications = new ArrayList<Application>();
		Application application = new Application("Java DevOps", "resume", "test.demo@gmil.com", ApplicationStatus.APPLIED);
		application.setApplicationId(6L);
		applications.add(application);
		Mockito.when(applicationRepo.findAllByRelatedOffer("Java DevOps")).thenReturn(applications);
		List<Application> applicationByJobId = impl.getApplicationByJobTitle("Java DevOps");
		assertNotNull(applicationByJobId);
		assertEquals("Java DevOps", applicationByJobId.get(0).getRelatedOffer());
		assertEquals(6L, applicationByJobId.get(0).getApplicationId());
		assertEquals("resume", applicationByJobId.get(0).getResumeText());
		assertEquals("test.demo@gmil.com", applicationByJobId.get(0).getCandidateEmail());
		assertEquals(ApplicationStatus.APPLIED, applicationByJobId.get(0).getStatus());
	}
	
	@Test
	public void testSubmitApplication() {
		Application application = new Application("Java DevOps", "resume", "test.demo@gmil.com", ApplicationStatus.APPLIED);
		application.setApplicationId(6L);
		Offer offer = new Offer("Java DevOps", new Date(), 0);
		offer.setJobId(2L);
		Mockito.when(offerRepo.findByJobTitle("Java DevOps")).thenReturn(offer);
		Mockito.when(applicationRepo.save(application)).thenReturn(application);
		Mockito.when(offerRepo.save(offer)).thenReturn(offer);
		
		Application submitApplication = impl.submitApplication(application );
		assertNotNull(submitApplication);
		assertEquals("Java DevOps", submitApplication.getRelatedOffer());
		assertEquals(6L, submitApplication.getApplicationId());
		assertEquals("resume", submitApplication.getResumeText());
		assertEquals("test.demo@gmil.com", submitApplication.getCandidateEmail());
		assertEquals(ApplicationStatus.APPLIED, submitApplication.getStatus());
	}
	@Test
	public void testSubmitApplicationNoJobTitle() {
		Application application = new Application("Java DevOps", "resume", "test.demo@gmil.com", ApplicationStatus.APPLIED);
		application.setApplicationId(6L);
		Mockito.when(offerRepo.findByJobTitle("Java DevOps")).thenReturn(null);
		Application submitApplication = null;
		try {
			submitApplication = impl.submitApplication(application );
			
		} catch (RecordNotFoundException e) {
			assertEquals("Title: Java DevOps Not Found", e.getMessage());
		}
		
	}
	
	
	@Test
	public void testGetAllApplicationsNullCheck() {
		Mockito.when(applicationRepo.findAll()).thenReturn(null);
		List<Application> offers = impl.getAllApplications();
		assertNull(offers);
	}
	@Test
	public void testGetAllApplicationsEmptyCheck() {
		Mockito.when(applicationRepo.findAll()).thenReturn(new ArrayList<Application>());
		List<Offer> offers = impl.getAllOffers();
		assertNotNull(offers);
	}
	@Test
	public void testGetAllApplications() {
		List<Application> applications = new ArrayList<Application>();
		Application newApp = new Application("Java DevOps", "resume", "test.demo@gmil.com", ApplicationStatus.APPLIED);
		newApp.setApplicationId(1L);
		applications.add(newApp);
		Mockito.when(applicationRepo.findAll()).thenReturn(applications);
		List<Application> resultApp = impl.getAllApplications();
		assertNotNull(resultApp);
		assertEquals("Java DevOps", resultApp.get(0).getRelatedOffer());
		assertEquals(1L, resultApp.get(0).getApplicationId());
		assertEquals("resume", resultApp.get(0).getResumeText());
		assertEquals("test.demo@gmil.com", resultApp.get(0).getCandidateEmail());
		assertEquals(ApplicationStatus.APPLIED, resultApp.get(0).getStatus());
	}

}
