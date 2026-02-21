# BlackRock Hackathon - Financial Transaction Processing API

A Spring Boot REST API application for processing financial transactions, calculating returns, and analyzing savings patterns with temporal filtering capabilities.

## 📋 Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Data Models](#data-models)
- [Testing](#testing)
- [Project Structure](#project-structure)

## 🎯 Overview

This application provides a comprehensive solution for financial transaction management and analysis. It enables:
- Transaction parsing and validation
- Temporal filtering based on various time periods (Q, P, K periods)
- Returns calculation using NPS (Net Present Savings) and Index methods
- Performance monitoring and metrics
- Wage-based transaction validation

## ✨ Features

### Transaction Management
- **Transaction Parsing**: Convert expense DTOs into transaction objects with automatic processing
- **Transaction Validation**: Validate transactions against wage constraints
- **Temporal Filtering**: Apply time-based rules (Q, P, K periods) to filter transactions

### Returns Calculation
- **NPS Returns**: Calculate Net Present Savings with inflation adjustments
- **Index Returns**: Calculate index-based returns for financial analysis
- **Savings Grouping**: Group transactions by K-periods for comprehensive analysis

### System Monitoring
- **Performance Metrics**: Real-time memory usage and thread count monitoring
- **Resource Tracking**: CPU and memory utilization insights

## 🛠 Technology Stack

- **Framework**: Spring Boot 4.0.3
- **Java Version**: 21
- **Build Tool**: Maven
- **Key Dependencies**:
  - Spring Boot Starter Web
  - Spring Boot Starter Validation
  - Spring Boot DevTools
  - Jackson (JSON processing)

## 📦 Prerequisites

Before running this application, ensure you have:
- Java Development Kit (JDK) 21 or higher
- Maven 3.6+ (or use the included Maven Wrapper)
- Your preferred IDE (IntelliJ IDEA, Eclipse, VS Code, etc.)

## 🚀 Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd Blackrock-Hackathon
   ```

2. **Build the project**
   ```bash
   # Using Maven Wrapper (Windows)
   .\mvnw.cmd clean install

   # Using Maven Wrapper (Unix/Mac)
   ./mvnw clean install

   # Using system Maven
   mvn clean install
   ```

## ▶️ Running the Application

### Using Maven
```bash
# Windows
.\mvnw.cmd spring-boot:run

# Unix/Mac
./mvnw spring-boot:run
```

### Using Java
```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8080` by default.

## 📡 API Endpoints

All endpoints are prefixed with `/blackrock/challenge/v1`

### Transaction Endpoints

#### Parse Transactions
```http
POST /blackrock/challenge/v1/transactions:parse
Content-Type: application/json

[
  {
    "date": "2024-01-15 10:30:00",
    "amount": 150.50
  }
]
```
Converts expense DTOs into transaction objects.

#### Validate Transactions
```http
POST /blackrock/challenge/v1/transactions:validator
Content-Type: application/json

{
  "wage": 5000.0,
  "transactions": [...]
}
```
Validates transactions against wage constraints.

#### Filter Transactions
```http
POST /blackrock/challenge/v1/transactions:filter
Content-Type: application/json

{
  "wage": 5000.0,
  "q": [...],
  "p": [...],
  "k": [...],
  "transactions": [...]
}
```
Applies temporal filtering rules to transactions.

### Returns Calculation Endpoints

#### Calculate NPS Returns
```http
POST /blackrock/challenge/v1/returns:nps
Content-Type: application/json

{
  "age": 30,
  "wage": 5000.0,
  "inflation": 0.03,
  "q": [...],
  "p": [...],
  "k": [...],
  "transactions": [...]
}
```
Calculates Net Present Savings returns.

#### Calculate Index Returns
```http
POST /blackrock/challenge/v1/returns:index
Content-Type: application/json

{
  "age": 30,
  "wage": 5000.0,
  "inflation": 0.03,
  "q": [...],
  "p": [...],
  "k": [...],
  "transactions": [...]
}
```
Calculates index-based returns.

### System Endpoints

#### Performance Metrics
```http
GET /blackrock/challenge/v1/performance
```
Returns system performance metrics including memory usage and thread count.

## 📊 Data Models

### Transaction
```json
{
  "date": "2024-01-15 10:30:00",
  "amount": 150.50,
  "ceiling": 5000.0,
  "remanent": 4849.50,
  "inkPeriod": true
}
```

### ExpenseDTO
```json
{
  "date": "2024-01-15 10:30:00",
  "amount": 150.50
}
```

### ReturnsRequest
```json
{
  "age": 30,
  "wage": 5000.0,
  "inflation": 0.03,
  "q": [],
  "p": [],
  "k": [],
  "transactions": []
}
```

### ValidationResponse
```json
{
  "valid": [],
  "invalid": []
}
```

## 🧪 Testing

Run the test suite:
```bash
# Using Maven Wrapper (Windows)
.\mvnw.cmd test

# Using Maven Wrapper (Unix/Mac)
./mvnw test

# Using system Maven
mvn test
```

## 📁 Project Structure

```
Blackrock-Hackathon/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/demo/
│   │   │       ├── DemoApplication.java
│   │   │       ├── Controller/
│   │   │       │   ├── ReturnsController.java
│   │   │       │   └── TransactionController.java
│   │   │       ├── Model/
│   │   │       │   ├── ExpenseDTO.java
│   │   │       │   ├── InvalidTransaction.java
│   │   │       │   ├── KPeriod.java
│   │   │       │   ├── PerformanceDTO.java
│   │   │       │   ├── PPeriod.java
│   │   │       │   ├── QPeriod.java
│   │   │       │   ├── ReturnsRequest.java
│   │   │       │   ├── ReturnsResponse.java
│   │   │       │   ├── SavingsResult.java
│   │   │       │   ├── TemporalFilterRequest.java
│   │   │       │   ├── Transaction.java
│   │   │       │   └── TransactionValidationRequest.java
│   │   │       └── Service/
│   │   │           ├── ReturnsService.java
│   │   │           ├── TemporalFilterService.java
│   │   │           ├── TemporalService.java
│   │   │           ├── TransactionService.java
│   │   │           └── TransactionValidatorService.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/example/demo/
│               └── DemoApplicationTests.java
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## 🔧 Configuration

The application can be configured through `src/main/resources/application.properties`:

```properties
spring.application.name=demo
# Add additional configurations as needed
```

## 📝 Additional Notes

### Builder Pattern
The `Transaction` class uses the Builder pattern for flexible object construction:
```java
Transaction transaction = Transaction.builder()
    .date(LocalDateTime.now())
    .amount(100.0)
    .ceiling(5000.0)
    .remanent(4900.0)
    .inkPeriod(true)
    .build();
```

### Validation
The API uses Jakarta Bean Validation for request validation:
- `@NotNull`: Ensures required fields are present
- `@Positive`: Ensures numeric values are positive
- `@PositiveOrZero`: Ensures amounts are non-negative

### JSON Date Format
Dates use the format: `yyyy-MM-dd HH:mm:ss`

## 🤝 Contributing

This project was developed for the BlackRock Hackathon. For contributions or questions, please contact the development team.

## 📄 License

[Specify your license here]

## 👥 Authors

- Dev Gupta

---

**Note**: This is a hackathon project demonstrating financial transaction processing and returns calculation capabilities.

