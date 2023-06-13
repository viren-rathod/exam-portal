# Exam Portal Backend

This is the backend implementation for an Exam Portal application built using Spring Boot v3.

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [API Documentation](#api-documentation)
- [Contributing](#contributing)
- [License](#license)

## Introduction
The Exam Portal Backend is a RESTful API server that provides various endpoints to manage exams, questions, and student submissions. It is built using Spring Boot v3, which is a powerful and widely used framework for developing Java-based applications.

## Features
- Authentication and authorization using JWT (JSON Web Tokens) and OAuth2.0
- CRUD operations for managing exams, questions, categories and student submissions
- Role-based access control for different user types (admin, instructor, student)
- Secure endpoints to prevent unauthorized access
- Integration with MySQL database to persist data
- Error handling and validation for API requests
- Unit and integration tests for ensuring code quality and functionality

## Prerequisites
Before running the application, ensure you have the following prerequisites installed:
- Java 17 or higher
- Apache Maven
- MySQL database

## Getting Started
1. Clone the repository:
   ```bash
   git clone https://github.com/viren-rathod/exam-portal.git
   ```
2. Navigate to the project directory:
   ```bash
   cd exam-portal
   ```
3. Build the project using Maven:
   ```bash
   mvn clean install
   ```
4. Configure the application properties by updating the `application.yml` or `application.properties` file with your database and other necessary settings.
5. Run the application:
   ```bash
   mvn spring-boot:run
   ```
6. The backend server should now be up and running on `http://localhost:8080`.

## Usage
To use the Exam Portal Backend, you can interact with the available API endpoints using tools like cURL, Postman, or any other HTTP client. Refer to the API documentation section below for detailed information about the available endpoints and their usage.

## API Documentation
For detailed API documentation and usage examples, please refer to the [API Documentation](`api-docs.md`) file.

## Contributing
Contributions to the Exam Portal Backend are welcome. If you find any issues or would like to add new features, please open a pull request with a detailed description of your changes.

## License
This project is licensed under the [MIT License](LICENSE).