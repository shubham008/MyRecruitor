package com.assignment.job.recruitor.myrecruitor.entities;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Offer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "job_id")
	private Long jobId;
	@Column(name = "job_title", unique = true)
	private String jobTitle;
	@Column(name = "start_date")
	@Temporal(TemporalType.DATE)
	private Date startDate;
	@Column(name = "no_of_application")
	private int noOfApplications = 0;

	public Offer() {
	}

	public Offer(String jobTitle, Date startDate, int noOfApplications) {

		this.jobTitle = jobTitle;
		this.startDate = startDate;
		this.noOfApplications = noOfApplications;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getNoOfApplications() {
		return noOfApplications;
	}

	public void setNoOfApplications(int noOfApplications) {
		this.noOfApplications = noOfApplications;
	}

	@Override
	public String toString() {
		return "Offer [jobId=" + jobId + ", jobTitle=" + jobTitle + ", startDate=" + startDate + ", noOfApplications="
				+ noOfApplications + "]";
	}
	
	
	
	
	
}
