# Covid-19 Dashboard for India

1. This application has been created with Spring boot/Spring Cloud Infrastructure, and Angular 8.
2. This application uses default server(Tomcat) embeded with Spting boot.
3. Database used in this application is in-memory H2 database.
4. Application uses URL "https://api.covid19india.org/districts_daily.json" to download latest Covid data
5. A scheduler is setup to refresh the data every hour
6. User authentication is facilitated by JWT token authentication, and token is stored in browser cookie[Token expires in 10 hours].
7. All the urls access are secured with spring security.
8. This application is hosted on AWS EC2 instance.
9. Source code is available on https://github.com/singhshiva88/covid-application

## Features included:
### User signup/login 
	1. User can create account by SignUp button (Top Right Corner)  
	2. User can login with login button (Top Right Corner)   
	3. sample user to login created by system :   
	4. Username: user1, Password: pass1,  
	5. Username: user2, Password: pass2  
	6. Once user login, a new tab appears - "Covid Stats"  

## Covid Stats tab:
Once user lands up to this screen for the first time, it shows Covid 19 Statistics for India. 

## This includes 5 sections:
	
		1. Left half of the screen shows all the states of India with 
			Total Confirmed, Active, Recovered, and Deceased in respective states. [All the state names are clickable]
			This table has search feature to filter states of your interest.
			
		2. Right half of the screen shows four different Charts/Graphs.
			First section shows Total number of cases.
			Second graph show the increamental changes starting from 22 March 2019.
			Third Bar chart (Stacked) shows top 15 states of India, with their active/recovered/deceased cases.
			Fourth Section shows percentage of contribution of each state to the National level.  
			
		3. Once user clicks on any state in Table, Right side of the Charts/Graphs updates as per their data.
			And table refreshes with the districts of that state. (Districts are again clickable)
			This included agreegated stats at state level, and cumulative chart, and all the information about its districts.
			
		4. Further to this, user can again click to any districts, and see the similar stats for any districts.
		5. User can always navigate to state lavel stats or national level stats by clicking on the links at top left Corner.
		
## Installation steps for local machine:
###   Steps for covid-server:
###     Things to install:
		1. JDK 8+
		2. node
		3. npm
###     Building both applications
	1. Extract covid-application:
		This includes two folders:
			a. covid-server [Spring boot application]
			b. covid-portal [Angular 8 Application]
	
	2. Steps for covid-server:
		Open command prompt 
		-> navigate to covid-server 
		-> run: mvn clean install
		--> goto target folder 
		--> run java -jar wander-covid19-app-1.0-SNAPSHOT.jar
	
	3. Steps for covid-portal:
		-->Navigate to covid-portal through cmd
		-->run: npm install
		-->after successful build run: npm start
	
	4. Now both the servers started
	
	5. Open Chrome:
		open localhost:4200
		Either use {user1, pass1} or {user2, pass2} to login 
		Or register yourself aand see the Covid 19 stats.
