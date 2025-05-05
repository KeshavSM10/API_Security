# 🔐 API_Security – Monetized & Secure API Gateway using Spring Boot

A scalable, production-ready backend system built with **Java 21**, **Spring Boot**, and **Spring Security**. It integrates robust API security with monetization logic (Free, Basic, Premium plans), JWT authentication, PostgreSQL storage, and traffic protection via **Bucket4j** rate limiting. Load balancing is handled using **NGINX** for smooth and reliable performance. This can be used as source for developemnt of secure APIs. Needs inclusion of API of desired Payment service like **Stripe**, **Paypal** etc.

---

## 💡 Key Highlights

- 🔐 JWT-based Authentication & Authorization
- 📊 API Rate Limiting with Bucket4j
- 📦 PostgreSQL-backed persistent storage
- 🌐 Load balancing with NGINX for scalability
- 💳 User Monetization Classes: `Free`, `Basic`, `Premium`
- 🔐 Role-based and Plan-based API access control

---

## ⚙️ Tech Stack

| Layer            | Technology             |
|------------------|------------------------|
| Language         | Java 21                |
| Framework        | Spring Boot, Spring Security |
| Database         | PostgreSQL             |
| API Security     | JWT (JSON Web Tokens)  |
| Rate Limiting    | Bucket4j               |
| Load Balancer    | NGINX                  |
| Build Tool       | Maven                  |
| Containerization | Docker (optional)      |

---

## 🧠 Core Modules

- **Authentication Module**: Secure login and signup with encrypted credentials.
- **Authorization Layer**: Access controlled via Spring Security and user roles.
- **User Plan Management**: Users are categorized into:
  - `Free`: Limited access, strict rate limit.
  - `Basic`: Moderate access, relaxed rate limit.
  - `Premium`: Full access with premium API quota.
- **Rate Limiting**: Implemented using Bucket4j to avoid abuse per user plan.
- **Request Logging & Auditing**: (Optional, based on implementation)
- **NGINX Load Balancing**: Ensures service availability under high traffic.

---

## 📁 File Structure

Use `tree -L 2` to generate:

```bash
API_Security/
├── src/
│   ├── main/
│   │   ├── java/com/security/api/
│   │   │   ├── auth/
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   ├── security/
│   │   │   ├── service/
│   │   │   └── util/
│   └── resources/
│       ├── application.yml
│       └── static/
├── nginx/
│   └── default.conf
├── Dockerfile
├── pom.xml
└── README.md

