# Backend_system-for-assignment_portal

This project is a backend system designed for managing assignments in a portal. It is built using Spring Boot and MongoDB, providing a robust foundation for handling complex data and user interactions efficiently.

Features:

User Authentication: Secure user management with Spring Security.
Data Validation: Input validation using Spring Boot's validation framework.
MongoDB Integration: Scalable and flexible NoSQL database support.
API Endpoints: RESTful APIs for CRUD operations on assignments.
Testing: Comprehensive unit and integration tests.
Prerequisites
Ensure the following tools are installed on your machine:

Java 17 or higher
Maven 3.8 or higher

MongoDB (local or cloud instance)
Installation
Clone the repository:

bash
Copy code
git clone https://github.com/neerajkumar202/assignment-portal.git
cd assignment-portal
Build the project:

bash
Copy code
./mvnw clean install

Configure your environment variables for MongoDB connection in application.properties or provide them as Docker environment variables.

Running the Application:

Locally:

Start the MongoDB server on localhost:27017 (default).

Run the application:

Run tests using Maven:Project Structure
src/main/java: Contains the main application code.
src/main/resources: Configuration files.
docker-compose.yml: Docker configuration for containerized setup.
pom.xml: Project dependencies and Maven configuration.
Key Dependencies
Spring Boot Starter Web: For building REST APIs.
Spring Boot Starter Data MongoDB: For interacting with MongoDB.
Spring Boot Starter Security: For secure authentication and authorization.
Lombok: Reduces boilerplate code with annotations.
Spring Boot Starter Test: Testing utilities.
