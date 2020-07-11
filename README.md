# MyRecruitor
A simple recruitment application

Project Description

A backend service that handles a (very simple) recruiting process.

The process provides two types of features: 

1.job offers
	
2.applications from candidates. 


Use cases
1.User can create a new unique job offer.
2.User can read these offer.
	a.Single offer on the basis of either Job Id or Job tiltle
	b.All the offers 
3.Candidate can apply for a offer.
4.User can read these application
	a.Single Application on the basis of either Job Id or Job tiltle
	b.All the Applications 
	

	
Rest APIs Exposed

1. GET http://localhost:8080/user/offers 
2. GET http://localhost:8080/user/offers/id/{jobId} 
	-> Example :- jobId = 1,2....
3. GET http://localhost:8080/user/offers/title/{jobTitle}
	-> Example :- jobTitle = Java developer or DevOps etc
4. POST http://localhost:8080/user/offers 
	-> RequestBoby  {
						"jobTitle": "job ID1",
						"startDate": "2020-08-09"
						"noOfApplications": 0 
					}
				jobTitle -> Needs to be unique
				startDate -> yyyy-MM-dd
				noOfApplications -> optional	
5. GET http://localhost:8080/candidate/applications
6. GET http://localhost:8080/candidate/applications/{applicationId}
	-> Example :- applicationId = 1,2....
7. POST http://localhost:8080/candidate/applications
	-> RequestBoby  {
						"relatedOffer": "job ID",
						"resumeText": "Resume",
						"candidateEmail": "1232@gmail.com",
						"status": "APPLIED"
					}
				relatedOffer -> valid job title
				candidateEmail -> unique
				status -> {APPLIED, INVITED, REJECTED, HIRED}

8. GET http://localhost:8080/candidate/applications/offers/id/{jobId}
	-> Example :- jobId = 1,2....
9. GET http://localhost:8080/candidate/applications/offers/title/{jobTitle}
	-> Example :- jobTitle = Java developer or DevOps etc
				
Technology Specification

1. Java version 1.8
2. Database H2 http://localhost:8080/h2-console/login.jsp
3. Springboot 2.3.1-Release

Important Commands

1. Build Command - mvn clean install
2. Run Application - mvn spring-boot:run (Root folder)



