# Points Application

## Overview

This project is a Points Management System that allows users to manage and track their points. It provides functionalities to add, spend, and view the points from different payers. This service is built with Java using Maven for dependency management and is containerized with Docker.

## Features

- **Add Points**: Add points for a specific payer with a timestamp.
- **Spend Points**: Spend points across different payers while respecting the order of transactions.
- **View Balance**: View the remaining balance for each payer.

## Prerequisites

Make sure you have the following installed:

- Docker

## Building and Running the Project

### 1. Clone the Repository

```bash
git clone https://github.com/ching4098/Fetch-Intern-Points-Project.git
cd Fetch-Intern-Points-Project/points
```
### 2. Build the Docker Image
Ensure that you are in the project directory, then run the following command to build the Docker image:
```bash
docker build -t points-application-zx .
```

### 3. Run the Docker container
Once the Docker image is built, you can run the container with the following command:
```bash
docker run -p 8000:8080 points-application-zx
```
This will map port 8000 on your local machine to port 8080 inside the Docker container. You are free to change it to whatever port you prefer to run this API on.

### 4. Testing the API using curl
Add Points:
```bash
curl -X POST http://localhost:8000/api/points/add -H "Content-Type: application/json" -d '{"payer":"DANNON", "points":1000, "time":"2023-10-01T10:00:00"}'
```
Spend Points:
```bash
curl -X POST http://localhost:8000/api/points/spend -H "Content-Type: application/json" -d '{"points":500}'
```
Get Balance:
```bash
curl -X GET http://localhost:8000/api/points/balance
```