# TRACW - Transaction and Account Management System

## Overview
TRACW is a secure financial transaction management system built with Spring Boot that allows users to manage their accounts, perform transactions, and track their financial history.

## Features
- 👤 User Authentication & Authorization
- 💰 Account Management
- 🔄 Transaction Processing (Deposit/Withdraw)
- 💸 Money Transfer Between Users
- 📊 Transaction History
- 🔒 Secure Password Management
- 📱 Responsive Design

## Technologies Used
- Java 21
- Spring Boot 3.4.2
- Spring Security
- Spring Data JPA
- Thymeleaf
- MySQL/H2 Database
- Maven
- HTML/CSS
- JavaScript

## Prerequisites
- Java Development Kit (JDK) 21
- Maven 3.6+
- MySQL (optional, can use H2 in-memory database)

## Installation & Setup

### 1. Install Java Oracle's website](https://www.oracle.com/java/technologies/downloads/#java21)
2. Install the JDK
3. Set JAVA_HOME environment variable:
   # Windows
    setx JAVA_HOME "C:\Program Files\Java\jdk-21"
  # Linux/Mac
    export JAVA_HOME=/usr/lib/jvm/java-21


### 2. Install Maven
1. Download from [Apache Maven website](https://maven.apache.org/download.cgi)
2. Extract and add to PATH:
   
# Windows
setx PATH "%PATH%;C:\apache-maven\bin"
# Linux/Mac
export PATH=$PATH:/opt/apache-maven/bin

### 3. Database Configuration
Edit `src/main/resources/application.properties`:

For H2 Database:
properties
spring.datasource.url=jdbc:h2:mem:tracwdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true


For MySQL:
properties
spring.datasource.url=jdbc:mysql://localhost:3306/tracwdb
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

## Running the Application

1. Clone the repository:
  bash
  git clone [your-repository-url]
  cd tracw

2. Build the project:
  bash
  mvn clean install

3. Run the application:
  bash
  mvn spring-boot:run

4. Access the application:
- Open browser and navigate to `http://localhost:8080`
- Register a new account
- Start managing your transactions

## Project Structure
tracw/

tracw/
├── src/
│   ├── main/
│   │   ├── java/com/tracw/tracw/
│   │   │   ├── controller/     
│   │   │   ├── model/          
│   │   │   ├── repository/      
│   │   │   ├── service/        
│   │   │   └── dto/            
│   │   ├── resources/
│   │   │   ├── templates/      
│   │   │   ├── static/         
│   │   │   └── application.properties  
│   ├── test/                   
├── pom.xml                     


## Security Features
- BCrypt password encryption
- CSRF protection
- Session management
- Secure form submission
- Protected endpoints

## Troubleshooting

### Common Issues and Solutions

1. Port Conflict
   
# Change in application.properties
server.port=8081

2. Database Connection Issues
- Verify database credentials
- Ensure database server is running
- Check database URL

3. Maven Build Failures
  bash
  mvn clean
  rm -rf ~/.m2/repository
  mvn install

4. Memory Issues
  bash
  mvn clean
  rm -rf ~/.m2/repository
  mvn install
4. Memory Issues
  bash
  export MAVEN_OPTS="-Xmx512m"
