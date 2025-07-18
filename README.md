# Title
LIBRARY MANAGEMENT SYSTEM
| NAME  | MATRIC NUMBER |
| ------------- | ------------- |
| SNEHA SEENIVASAN  | B032310779  |
| UGAHNESH A/L MOORTHY  | B032310781  |
| KIRTIGAN A/L BALU  | B032310873  |
| PARTHIBAN A/L SUBRAMANIAM  | B032310789  |
| MADHANAVILASHAN A/L GANAPATHY  | B032310801  |

# Introduction
# Project Overview
The Library Management System (LMS) is a Java-based desktop application that streamlines the management of library operations such as book cataloging, user registration, and borrowing processes. It provides a structured solution to replace traditional manual systems, reducing errors, saving time, and improving record-keeping efficiency. The system supports two main user roles: administrators, who can add or remove books and view the list of registered users, and regular users, who can register, log in, and browse the book collection. Designed for small to medium-sized libraries, academic institutions, or community reading centers, this project offers an intuitive interface and serves as a foundation for potential future enhancements like online book reservations, overdue fine tracking, or integration with external APIs for book data.

# Commercial Value
# Potential Real-World Use
Schools and community libraries can adopt LMS to digitize their daily operations.
Reduces administrative workload by automating inventory and user management.
Can be easily extended to cloud hosting for online access.

# Third-Party Integration Possibilities

# System Architecture
![System Architecture](system-architecture.png)

The Library Management System is a Java Swing-based desktop application built using a layered architecture that separates the presentation (UI), business logic, and data access layers. The application supports two main user roles: Admin and User.

- **Presentation Layer:**
  The Java Swing GUI handles all user interactions through multiple forms such as login, registration, user menu, and admin menu.

- **Business Logic Layer:** This layer contains all the logic for system functionality, such as login authentication, book borrowing and returning, and admin actions like adding or removing books.

- **Data Layer:**
  This layer is responsible for all communication with the database. It uses JDBC (Java Database Connectivity) to interact with a MySQL/SQLite database that stores user data, book records, and borrowing history.

- **User Roles**
  - **Admin:** Can log in to manage the library system, including adding/removing books and viewing registered users.
  - **User:** Can register, log in, view available books, and borrow or return them.

# Backend Application
# Technology Stack
| Layer  | Technology |
| ------------- | ------------- |
| Programming  | Eclipse  |
| Server  | Apache (XAMPP)  |
| Database| MySQL (library_db.sql)  |
| Communication  | RESTful API (HTTP, JSON)  |

# Security
Currently uses simple role-based access control (RBAC) at frontend level.
For future enhancements, consider:
JWT (JSON Web Tokens) for stateless API security.
OAuth 2.0 for third-party authentication.
Password hashing using PHP password_hash().

# Entity-Relationship Diagram (ERD)
![Entity-Relationship Diagram (ERD)](entity-relationship-diagram.png)
- **USERS TABLE**
  Stores user credentials and roles.
  - id: Primary Key
  - username: Unique identifier for each user
  - password: Encrypted password
  - role: Defines whether the user is an admin or a regular user
 
- **BOOKS TABLE**
  Manages book information and borrowing details.
  - id: Primary Key
  - title, author, year: Descriptive book details
  - available: Boolean (1 = available, 0 = borrowed)
  - borrow_date: Date when the book was borrowed
  - return_date: Date when the book was returned
 
- **RELATIONSHIP BY ROLES**
  - **🔒 Users (role = user):**
    - borrows: Users can borrow books
    - views: Users can view available books

  - **🛠️ Admins (role = admin):**
    - adds: Admins can add new books
    - deletes: Admins can remove books
    - views: Admins can view all user activity
