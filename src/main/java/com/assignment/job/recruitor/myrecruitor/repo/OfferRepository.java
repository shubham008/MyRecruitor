package com.assignment.job.recruitor.myrecruitor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.job.recruitor.myrecruitor.entities.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
	
	Offer findByJobTitle(String jobTitle);
}
