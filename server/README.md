📌 Issue Tracker – Java Spring Boot + Angular

A complete full-stack Issue Tracker Application with CRUD, search, filters, pagination, file uploads, Swagger documentation, and PostgreSQL persistence.
🔧 Tech Stack
Layer Technology
Frontend Angular 17, HTML, CSS, TypeScript
Backend Spring Boot 3.3, Spring Data JPA
Database PostgreSQL 14+
Testing JUnit 5
API Docs Swagger / OpenAPI
Build Tools Maven, NodeJS
Others File Uploads, Jakarta Validation

🚀 Features
✔ Issue Management

Create, update, delete issues

Search by title

Filter by status / priority

Sort by created date

Pagination

Dashboard statistics

JUnit test case included

✔ Attachments

Upload / download / delete

Validations for size + type

Files stored in /uploads directory

✔ Swagger API

Auto-generated REST documentation

Accessible at:
👉 http://localhost:8080/swagger-ui/index.html

📦 Project Structure
IssueTracker/
├── backend/
│ ├── src/main/java/com/example/issuetracker
│ ├── src/main/resources/
│ │ ├── application.properties
│ │ ├── data.sql (optional)
│
├── frontend/
│ ├── src/app/
│ │ ├── features/issues
│ │ ├── services
│ │ ├── pages
│
└── README.md

🔧 Requirements
Tool Version
Java JDK 17+
Spring Boot 3.3+
Angular 17+
PostgreSQL 14+
NodeJS 18+

🛠 Backend – Setup & Run
1️⃣ Create PostgreSQL Database

Run:

CREATE DATABASE issuetracker;

2️⃣ Configure application.properties
spring.datasource.url=jdbc:postgresql://localhost:5433/issuetracker
spring.datasource.username=postgres
spring.datasource.password=9010

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.sql.init.mode=never

file.upload-dir=uploads

spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

server.port=8080

3️⃣ Run Backend

Run:

mvn spring-boot:run

Backend starts at:
👉 http://localhost:8080

🖥 Frontend – Setup & Run

Go to frontend folder:

cd frontend
npm install
ng serve --open

Frontend UI runs at:
👉 http://localhost:4200/

📁 File Upload Path

Uploaded attachments stored in:

/uploads

📘 API Documentation

Swagger UI
👉 http://localhost:8080/swagger-ui/index.html

OpenAPI JSON
👉 http://localhost:8080/v3/api-docs

📌 Features Implemented
✔ Issue CRUD

Create Issue

Update Issue

Delete Issue

View Issue

Pagination

Sorting

Search

Filters (status, priority)

✔ Dashboard

Total issues

Open

In Progress

Resolved

Closed

Priority stats

✔ Attachments

Upload file

Download file

Delete file

Validate file

Store in local folder

✔ Input Validation

Title: min 3 chars

Description: min 5 chars

Status required

Priority required

📌 Sample API Request
POST: Create Issue
{
"title": "Payment service timeout",
"description": "Fix API delay during checkout",
"status": "IN_PROGRESS",
"priority": "HIGH"
}

GET: Issues List
/api/issues?page=0&size=10&sortBy=createdAt&sortDir=desc

POST: Upload Attachment
/api/issues/{id}/attachments

🧪 Unit Testing
✔ JUnit tests include:

Create Issue

Get Issue

Update Issue

Delete Issue

📦 Final Completed Items
Task Status
DTOs ✔ Done
Mapper classes ✔ Done
Swagger config ✔ Done
Controllers ✔ Done
Services ✔ Done
Pagination ✔ Done
Filters ✔ Done
Sorting ✔ Done
File Upload ✔ Done
JUnit Testing ✔ Done
Angular UI ✔ Done
Database setup ✔ Done
README.md ✔ Done

👩‍💻 Author

Rufiya Shaik
