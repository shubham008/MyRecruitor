package com.assignment.job.recruitor.myrecruitor.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.job.recruitor.myrecruitor.entities.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
	
	List<Application> findAllByRelatedOffer(String relatedOffer);
}
