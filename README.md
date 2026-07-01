# Hotel Management System - Backend

A robust Spring Boot backend for a full-stack Hotel Management System that enables secure hotel operations through role-based authentication, booking management, room management, and RESTful APIs.

> **Frontend Repository:**  
> https://github.com/saini-harsh15/Hotel-Management-System-Frontend

---

# Features

## Authentication & Authorization

- JWT Authentication
- Spring Security
- Role-Based Access Control
- Secure REST APIs
- Password Encryption
- Protected Endpoints

---

## Customer Module

- User Registration & Login
- Browse Hotels
- Search Hotels
- View Hotel Details
- Book Rooms
- View Booking History
- Submit Reviews

---

## Hotel Administrator Module

- Create Hotels
- Edit Hotel Details
- Manage Room Types
- Manage Rooms
- View Hotel Bookings
- Check-In Guests
- Check-Out Guests
- View Customer Reviews

---

## Booking Management

- Room Availability Validation
- Automatic Room Allocation
- Booking Lifecycle Management
- Booking Status Tracking
- Business Rule Validation

---

## Review Management

- Customer Reviews
- Hotel Review Dashboard
- Average Rating Calculation
- Total Review Tracking

---

## REST APIs

RESTful APIs are implemented for:

- Authentication
- Hotels
- Room Types
- Rooms
- Bookings
- Reviews

---

# Tech Stack

## Backend

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA (Hibernate)
- Maven

## Database

- MySQL

## Authentication

- JWT (JSON Web Token)

## API Testing

- Postman

---

# Database Design

The project follows a normalized relational database design.

```
User
│
├── Hotel
│      │
│      ├── RoomType
│      │      │
│      │      └── Room
│      │
│      └── Booking
│              │
│              ├── Payment
│              └── Review
```

---

# Project Structure

```
src
│
├── config
├── controller
├── dto
│   ├── request
│   └── response
├── entity
├── enums
├── exception
├── repository
├── security
├── service
│   └── impl
├── util
└── HotelManagementApplication
```

---

# Getting Started

## Clone Repository

```bash
git clone https://github.com/saini-harsh15/Hotel-Management-System-Backend.git
```

Navigate into the project

```bash
cd Hotel-Management-System-Backend
```

---

# Configure Database

Create a MySQL database.

Example:

```sql
CREATE DATABASE hotel_management;
```

Update your `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hotel_management

spring.datasource.username=YOUR_USERNAME

spring.datasource.password=YOUR_PASSWORD
```

---

# Build the Project

```bash
mvn clean install
```

---

# Run the Application

```bash
mvn spring-boot:run
```

The backend will start on:

```
http://localhost:8081
```

---

# API Testing

Use Postman or any REST client.

Example endpoints:

```
POST   /api/auth/register

POST   /api/auth/login

GET    /api/hotels

POST   /api/bookings

POST   /api/reviews
```

---

# Security

The application uses JWT Authentication.

Include the token in requests:

```
Authorization: Bearer <JWT_TOKEN>
```

---

# Future Enhancements

- Super Admin Dashboard
- Payment Gateway Integration
- Room Status Management
- Email Notifications
- Hotel Image Uploads
- Dashboard Analytics
- Revenue Reports

---

# Author

**Harsh Saini**

LinkedIn

https://www.linkedin.com/in/harshsaini15/

GitHub

https://github.com/saini-harsh15

Portfolio

https://harsh-saini-portfolio.vercel.app/

---

# License

This project is developed for learning, portfolio, and educational purposes.
