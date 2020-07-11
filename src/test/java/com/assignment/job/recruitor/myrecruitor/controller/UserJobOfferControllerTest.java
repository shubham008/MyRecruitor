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

import com.assignment.job.recruitor.myrecruitor.entities.Offer;
import com.assignment.job.recruitor.myrecruitor.service.MyrecruiterService;
import com.fasterxml.jackson.databind.ObjectMapper;

@TestInstance(Lifecycle.PER_CLASS)
public class UserJobOfferControllerTest {
	
	@InjectMocks
    private UserJobOfferController controller;
	
	@Mock
    private MyrecruiterService myrecruiterService;
	
	@Autowired 
	private MockMvc mvc;
	
	private List<Offer> newOffers = new ArrayList<Offer>();
	private Offer newOffer;
	
	
	@BeforeAll
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
        newOffer = new Offer("Java Developer", new Date(), 0);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
        Mockito.when(myrecruiterService.createOffer(Matchers.any(Offer.class))).thenReturn(newOffer);
        
    }
	
	
	@Test
	void testGetAllOffersNullCheck() {
		List<Offer> offers = null;
		try {
			offers = controller.getAllOffers();
		} catch (Exception e) {
			assertEquals("No Job Offers Added.", e.getMessage());
			assertTrue(offers == null);
		}
	}
	@Test
	void testGetOfferByIdNullCheck() {
		Offer offers = null;
		try {
			offers = controller.getOfferById(1L);
		} catch (Exception e) {
			assertEquals("Id: 1 Not Found", e.getMessage());
			assertTrue(offers == null);
		}
	}
	@Test
	void testGetOfferByTitleNullCheck() {
		Offer offers = null;
		try {
			offers = controller.getOfferByTitle("Java Developer");
		} catch (Exception e) {
			assertEquals("Title: Java Developer Not Found", e.getMessage());
			assertTrue(offers == null);
		}
	}
	@Test
	void testCreateOffer() {
		try {
			newOffer.setJobId(1L);
			ResultActions perform = mvc.perform(MockMvcRequestBuilders.post("/user/offers")
	                .content(asJsonString(newOffer))
	                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
			
			assertEquals("http://localhost/user/offers/1",perform.andReturn().getResponse().getHeader("location"));
			
		} catch (Exception e) {
			System.out.println("fail");
		}
	}
	
	@Test
	void testGetAllOffers() {
		newOffers.add(newOffer);
		Mockito.when(myrecruiterService.getAllOffers()).thenReturn(newOffers);
		try {
			List<Offer> newOffers = controller.getAllOffers();
			assertNotNull(newOffers);
			assertTrue(!newOffers.isEmpty());
			assertEquals("Java Developer", newOffers.get(0).getJobTitle());
			assertEquals(0, newOffers.get(0).getNoOfApplications());
		} catch (Exception e) {
			System.out.println("fail");
		}
	}
	@Test
	void testGetOfferById() {
		Offer offers = null;
		newOffer.setJobId(1L);
		Mockito.when(myrecruiterService.getOfferById(1L)).thenReturn(newOffer);
		try {
			offers = controller.getOfferById(1L);
			assertNotNull(offers);
			assertEquals("Java Developer", offers.getJobTitle());
			assertEquals(1L, offers.getJobId());
			assertEquals(0, offers.getNoOfApplications());
		} catch (Exception e) {
			System.out.println("fail");
		}
	}
	@Test
	void testGetOfferByTitle() {
		Offer offers = null;
		newOffer.setJobId(1L);
		Mockito.when(myrecruiterService.getOfferByTitle("Java Developer")).thenReturn(newOffer);
		try {
			offers = controller.getOfferByTitle("Java Developer");
			assertNotNull(offers);
			assertEquals("Java Developer", offers.getJobTitle());
			assertEquals(1L, offers.getJobId());
			assertEquals(0, offers.getNoOfApplications());
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
