package com.assignment.job.recruitor.myrecruitor.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.RequestEntity.HeadersBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.assignment.job.recruitor.myrecruitor.entities.Application;
import com.assignment.job.recruitor.myrecruitor.entities.ApplicationStatus;
import com.assignment.job.recruitor.myrecruitor.entities.Offer;
import com.assignment.job.recruitor.myrecruitor.service.MyrecruiterService;
import com.fasterxml.jackson.databind.ObjectMapper;

@TestInstance(Lifecycle.PER_CLASS)
public class ApplicationControllerTest {
	
	@InjectMocks
    private ApplicationController controller;
	
	@Mock
    private MyrecruiterService myrecruiterService;
	
	@Autowired 
	private MockMvc mvc;
	
	private Application newApplication;
	
	
	@BeforeAll
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
        newApplication = new Application("Java Developer", "resume", "test@test.com", ApplicationStatus.APPLIED);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
        
    }
	
	
	@Test
	void testGetAllApplicationsNullCheck() {
		List<Application> applications = null;
		try {
			applications = controller.getAllApplications();
		} catch (Exception e) {
			assertEquals("No Applications Applied.", e.getMessage());
			assertTrue(applications == null);
		}
	}
	@Test
	void testGetAllApplications() {
		List<Application> newApplications = new ArrayList<>();
		Application application = new Application(".Net", "resume", "demo@test.com", ApplicationStatus.APPLIED);
		application.setApplicationId(3L);
		newApplications.add(application);
		Mockito.when(myrecruiterService.getAllApplications()).thenReturn(newApplications);
		try {
			List<Application> applications = controller.getAllApplications();
			assertNotNull(applications);
			assertEquals(3L, applications.get(0).getApplicationId());
			assertEquals("demo@test.com",applications.get(0).getCandidateEmail());
			assertEquals(".Net",applications.get(0).getRelatedOffer());
			assertEquals("resume",applications.get(0).getResumeText());
			assertEquals(ApplicationStatus.APPLIED,applications.get(0).getStatus());
		} catch (Exception e) {
			System.out.println("fail");
		}
	}
	@Test
	void testgetApplicationByIdNullCheck() {
		Application application = null;
		try {
			application = controller.getApplicationById(1L);
		} catch (Exception e) {
			assertEquals("Application Id: 1 not Found", e.getMessage());
			assertTrue(application == null);
		}
	}
	@Test
	void testgetApplicationById() {
		Application application = null;
		newApplication.setApplicationId(2L);
		Mockito.when(myrecruiterService.getApplicationById(2L)).thenReturn(newApplication);
		try {
			application = controller.getApplicationById(2L);
			assertEquals(2L, application.getApplicationId());
			assertEquals("test@test.com",application.getCandidateEmail());
			assertEquals("Java Developer",application.getRelatedOffer());
			assertEquals("resume",application.getResumeText());
			assertEquals(ApplicationStatus.APPLIED,application.getStatus());
		} catch (Exception e) {
			System.out.println("fail");
		}
	}
	
	@Test
	void testSubmitApplication() {
		Mockito.when(myrecruiterService.submitApplication(Matchers.any(Application.class))).thenReturn(newApplication);
		try {
			newApplication.setApplicationId(1L);
			ResultActions perform = mvc.perform(MockMvcRequestBuilders.post("/candidate/applications")
	                .content(asJsonString(newApplication))
	                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
			
			assertEquals("http://localhost/candidate/applications/1",perform.andReturn().getResponse().getHeader("location"));
			
		} catch (Exception e) {
			System.out.println("fail");
		}
	}
	@Test
	void testGetAllApplicationsByJobIdNullCheck() {
		List<Application> applications = new ArrayList<>();
		try {
			applications = controller.getAllApplicationsByJobId(2L);
			
		} catch (Exception e) {
			assertEquals("No Application For this Job Id Available", e.getMessage());
		}
	}
	@Test
	void testGetAllApplicationsByJobId() {
		List<Application> newApplications = new ArrayList<>();
		newApplication.setApplicationId(1L);
		newApplications.add(newApplication);
		Mockito.when(myrecruiterService.getApplicationByJobId(1L)).thenReturn(newApplications);
		try {
			List<Application> applications = controller.getAllApplicationsByJobId(1L);
			assertNotNull(applications);
			assertEquals(1L,applications.get(0).getApplicationId());
			assertEquals("test@test.com",applications.get(0).getCandidateEmail());
			assertEquals("Java Developer",applications.get(0).getRelatedOffer());
			assertEquals("resume",applications.get(0).getResumeText());
			assertEquals(ApplicationStatus.APPLIED,applications.get(0).getStatus());
		} catch (Exception e) {
			System.out.println("fail");
		}
	}
	@Test
	void testGetAllApplicationsByJobTitleNullCheck() {
		List<Application> applications = new ArrayList<>();
		try {
			applications = controller.getAllApplicationsByJobTitle("Java");
			
		} catch (Exception e) {
			assertEquals("No Application For this Job Title Available", e.getMessage());
		}
	}
	@Test
	void testGetAllApplicationsByJobTitle() {
		List<Application> newApplications = new ArrayList<>();
		newApplication.setApplicationId(4L);
		newApplications.add(newApplication);
		Mockito.when(myrecruiterService.getApplicationByJobTitle("Java Developer")).thenReturn(newApplications);
		try {
			List<Application> applications = controller.getAllApplicationsByJobTitle("Java Developer");
			assertNotNull(applications);
			assertEquals(4L,applications.get(0).getApplicationId());
			assertEquals("test@test.com",applications.get(0).getCandidateEmail());
			assertEquals("Java Developer",applications.get(0).getRelatedOffer());
			assertEquals("resume",applications.get(0).getResumeText());
			assertEquals(ApplicationStatus.APPLIED,applications.get(0).getStatus());
		} catch (Exception e) {
			System.out.println("fail");
		}
	}
	public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
}
