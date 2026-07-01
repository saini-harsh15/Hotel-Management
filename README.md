# 🏨 Hotel Management System

A full-stack Hotel Management System built using **Java**, **Spring Boot**, **Spring Security**, **JWT Authentication**, **Hibernate/JPA**, and **MySQL**.

The system supports role-based access control with **CUSTOMER**, **HOTEL_ADMIN**, and **SUPER_ADMIN** roles, allowing hotel owners to manage hotels, room types, and rooms while customers can browse approved hotels.

🔗 **Frontend Repo:** [Hotel-Management-Frontend](https://github.com/saini-harsh15/Hotel-Management-Frontend)

---

## 🚀 Features

### 🔐 Authentication & Authorization

- User Registration
- User Login
- BCrypt Password Encryption
- JWT-Based Authentication
- Spring Security Integration
- Role-Based Access Control (RBAC)

### 👥 User Roles

#### CUSTOMER
- Register Account
- Login
- View Approved Hotels

#### HOTEL_ADMIN
- Create Hotels
- View Own Hotels
- Update Own Hotels
- Create Room Types
- View Room Types
- Create Rooms
- View Rooms

#### SUPER_ADMIN
- Approve Hotels
- Manage Platform Hotels

---

## 🏗️ System Architecture

```text
User
 │
 ├── CUSTOMER
 │     └── View Approved Hotels
 │
 ├── HOTEL_ADMIN
 │     ├── Create Hotel
 │     ├── Manage Hotel
 │     ├── Create Room Types
 │     └── Create Rooms
 │
 └── SUPER_ADMIN
       └── Approve Hotels
```

---

## 🏨 Hotel Workflow

```text
HOTEL_ADMIN
      │
      ▼
Create Hotel
      │
      ▼
PENDING
      │
      ▼
SUPER_ADMIN Approval
      │
      ▼
APPROVED
      │
      ▼
Visible To Customers
```

---

## 🛏️ Hotel Structure

```text
Hotel
 │
 ├── Room Type (Deluxe)
 │      ├── Room 101
 │      ├── Room 102
 │      └── Room 103
 │
 ├── Room Type (Suite)
 │      ├── Room 201
 │      └── Room 202
 │
 └── Room Type (Standard)
        ├── Room 301
        └── Room 302
```

---

# 🛠️ Tech Stack

## Backend

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- JWT Authentication
- Maven

## Database

- MySQL

## Tools

- Postman
- IntelliJ IDEA
- Git
- GitHub

---

# 📂 Project Structure

```text
src/main/java/com/hotelmanagement

├── controller
│   ├── AuthController
│   ├── HotelController
│   ├── RoomTypeController
│   ├── RoomController
│   └── AdminHotelController
│
├── service
│   ├── AuthService
│   ├── HotelService
│   ├── RoomTypeService
│   └── RoomService
│
├── service/impl
│   ├── AuthServiceImpl
│   ├── HotelServiceImpl
│   ├── RoomTypeServiceImpl
│   └── RoomServiceImpl
│
├── repository
│   ├── UserRepository
│   ├── HotelRepository
│   ├── RoomTypeRepository
│   └── RoomRepository
│
├── entity
│   ├── UserEntity
│   ├── HotelEntity
│   ├── RoomTypeEntity
│   └── RoomEntity
│
├── security
│   ├── SecurityConfig
│   ├── JwtService
│   ├── JwtAuthenticationFilter
│   └── CustomUserDetailsService
│
├── dto
├── enums
├── exception
└── config
```

---

# 🗄️ Database Design

## User

```text
User
 ├── id
 ├── firstName
 ├── lastName
 ├── email
 ├── phoneNumber
 ├── password
 ├── role
 └── status
```

## Hotel

```text
Hotel
 ├── id
 ├── name
 ├── description
 ├── address
 ├── contactNumber
 ├── email
 ├── status
 ├── averageRating
 └── owner
```

## Room Type

```text
RoomType
 ├── id
 ├── name
 ├── description
 ├── price
 ├── capacity
 ├── bedType
 └── hotel
```

## Room

```text
Room
 ├── id
 ├── roomNumber
 ├── floorNumber
 ├── status
 ├── hotel
 └── roomType
```

---

# 🔐 Security

The application uses JWT Authentication.

### Login Flow

```text
User Login
     │
     ▼
Validate Credentials
     │
     ▼
Generate JWT Token
     │
     ▼
Return Token
     │
     ▼
Protected API Access
```

### Authorization Header

```http
Authorization: Bearer <JWT_TOKEN>
```

---

# 📡 Implemented APIs

## Authentication APIs

| Method | Endpoint | Description |
|----------|-----------|-------------|
| POST | `/api/auth/register` | Register User |
| POST | `/api/auth/login` | Login User |
| GET | `/api/auth/hello` | JWT Protected Test |
| GET | `/api/auth/me` | Get Logged In User |

---

## Hotel APIs

| Method | Endpoint | Description |
|----------|-----------|-------------|
| POST | `/api/hotels` | Create Hotel |
| GET | `/api/hotels/my-hotels` | Get My Hotels |
| GET | `/api/hotels/{id}` | Get Hotel By Id |
| PUT | `/api/hotels/{id}` | Update Hotel |
| GET | `/api/hotels` | Get Approved Hotels |

---

## Admin APIs

| Method | Endpoint | Description |
|----------|-----------|-------------|
| PUT | `/api/admin/hotels/{hotelId}/approve` | Approve Hotel |

---

## Room Type APIs

| Method | Endpoint | Description |
|----------|-----------|-------------|
| POST | `/api/hotels/{hotelId}/room-types` | Create Room Type |
| GET | `/api/hotels/{hotelId}/room-types` | Get Room Types |

---

## Room APIs

| Method | Endpoint | Description |
|----------|-----------|-------------|
| POST | `/api/room-types/{roomTypeId}/rooms` | Create Room |
| GET | `/api/room-types/{roomTypeId}/rooms` | Get Rooms |
| GET | `/api/room-types/{roomTypeId}/rooms/{roomId}` | Get Room By Id |

---

# 🚧 Upcoming Features

- Booking Management
- Room Availability Tracking
- Check-In / Check-Out
- Hotel Amenities
- Hotel Images
- Room Images
- Reviews & Ratings
- Payment Integration
- Dashboard Analytics
- Email Notifications

---

# ▶️ Running the Project

## Clone Repository

```bash
git clone https://github.com/your-username/hotel-management-system.git
```

## Navigate To Project

```bash
cd hotel-management-system
```

## Configure Database

Update:

```properties
application.properties
```

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hotel_management
spring.datasource.username=root
spring.datasource.password=your_password
```

## Run Application

```bash
mvn spring-boot:run
```

Application runs on:

```text
http://localhost:8080
```

---

# 📈 Current Progress

```text
Authentication Module      ✅ Completed
Authorization Module       ✅ Completed
Hotel Module               ✅ Completed
Room Type Module           ✅ Completed
Room Module                ✅ Completed

Booking Module             🚧 In Progress
Payment Module             ⏳ Planned
Review Module              ⏳ Planned
```

---

# 👨‍💻 Author

**Harsh Saini**

Backend Developer | Java | Spring Boot | MySQL

Built as a real-world backend project to demonstrate:

- Spring Boot Development
- REST API Design
- JWT Authentication
- Spring Security
- JPA/Hibernate
- Database Design
- Role-Based Access Control
- Clean Architecture

---
⭐ If you found this project useful, consider giving it a star.
