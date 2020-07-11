package com.assignment.job.recruitor.myrecruitor.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Application {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "application_id")
	private Long applicationId;
	@Column(name = "related_offer")
	private String relatedOffer;
	@Column(name = "resume_text")
	private String resumeText;
	@Column(name = "candidate_email", unique = true)
	private String candidateEmail;
	@Column(name = "status")
	private ApplicationStatus status;

	public Application() {
	}

	public Application(String relatedOffer,String resumeText, String candidateEmail, ApplicationStatus status) {
		this.relatedOffer = relatedOffer;
		this.resumeText = resumeText;
		this.candidateEmail = candidateEmail;
		this.status = status;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public String getRelatedOffer() {
		return relatedOffer;
	}

	public void setRelatedOffer(String relatedOffer) {
		this.relatedOffer = relatedOffer;
	}
	
	public String getResumeText() {
		return resumeText;
	}

	public void setResumeText(String resumeText) {
		this.resumeText = resumeText;
	}

	public String getCandidateEmail() {
		return candidateEmail;
	}

	public void setCandidateEmail(String candidateEmail) {
		this.candidateEmail = candidateEmail;
	}

	public ApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(ApplicationStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Application [applicationId=" + applicationId + ", relatedOffer=" + relatedOffer + ", candidateEmail="
				+ candidateEmail + ", status=" + status + "]";
	}
	
}
