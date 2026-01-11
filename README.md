# Course API

REST API for student management, developed with Spring Boot following **Hexagonal Architecture (Ports and Adapters)**.

## 🏗️ Architecture

This project implements **Hexagonal Architecture** (also known as Ports and Adapters), separating business logic from the domain from technical infrastructure details.

### Project Structure

```
src/main/java/com/example/course_api/
├── domain/                           # CORE (Domain)
│   ├── model/                        # Domain entities
│   │   └── Student.java              # Pure domain model
│   └── exception/                    # Domain exceptions
│       ├── StudentNotFoundException.java
│       └── DuplicateEmailException.java
│
├── application/                      # APPLICATION LAYER
│   ├── port/                         # Ports (interfaces)
│   │   ├── input/                    # Input Ports (use cases)
│   │   │   └── StudentUseCase.java
│   │   └── output/                   # Output Ports (repositories)
│   │       └── StudentRepositoryPort.java
│   └── service/                      # Use case implementation
│       └── StudentService.java
│
└── infrastructure/                   # INFRASTRUCTURE (Adapters)
    └── adapter/
        ├── input/                    # Primary Adapters (input)
        │   └── rest/
        │       ├── StudentController.java
        │       ├── dto/
        │       │   ├── StudentRequest.java
        │       │   └── StudentResponse.java
        │       └── exception/
        │           └── GlobalExceptionHandler.java
        └── output/                   # Secondary Adapters (output)
            └── persistence/
                ├── StudentJpaEntity.java
                ├── StudentJpaRepository.java
                └── StudentRepositoryAdapter.java
```

## 🎯 Features

- ✅ **Hexagonal Architecture**: Clear separation between domain, application, and infrastructure
- ✅ **Independent Domain**: No dependencies on frameworks or technologies
- ✅ **Ports and Adapters**: Interfaces define contracts, adapters implement details
- ✅ **Testability**: Easy to test each layer independently
- ✅ **Flexibility**: Change technologies without affecting the domain
- ✅ **SOLID Principles**: Applied throughout the entire codebase
- ✅ **Clean Code**: Refactored methods following clean code practices
- ✅ **Docker Support**: Containerized application with MySQL database
- ✅ **Professional Logging**: SLF4J logging instead of System.out.println

## 🚀 Technologies

- **Java 17** (LTS)
- **Spring Boot 3.3.12**
- **Spring Data JPA**
- **MySQL** (production) / **H2** (tests)
- **Lombok**
- **Spring Validation**
- **SLF4J** (Logging)
- **Docker** & **Docker Compose**
- **JUnit 5** (testing)
- **Mockito** (mocking)

## 📋 Main Endpoints

- `GET /api/v1/students` — List all students
- `GET /api/v1/students/{studentId}` — Get a student by ID
- `POST /api/v1/students` — Create a new student
- `PUT /api/v1/students/{studentId}` — Update an existing student
- `DELETE /api/v1/students/{studentId}` — Delete a student

## 📝 Example JSON for create or update

```json
{
  "firstName": "Juan",
  "lastName": "Pérez",
  "email": "juan.perez@email.com"
}
```

## ✅ Validations

- Email must be unique and valid
- First name, last name, and email fields are required
- Validation both in the domain and in the input adapter

## 🧪 Testing

The project includes comprehensive tests for all layers:

- **Domain Tests**: Unit tests for the domain model
- **Application Tests**: Use case tests with mocks
- **Infrastructure Tests**: Adapter tests (repository, controller)

### Run Tests

```bash
# All tests
mvn test

# Specific tests
mvn test -Dtest=StudentServiceTest
mvn test -Dtest=StudentTest
mvn test -Dtest=StudentRepositoryAdapterTest
```

## 🔧 Configuration

### 1. Clone the repository

```bash
git clone https://github.com/stiv120/course-api.git
cd course-api
```

### 2. Run with Docker (Recommended)

The easiest way to run the application is using Docker Compose:

**Windows:**
```bash
docker-start.bat
```

**Linux/Mac:**
```bash
chmod +x docker-start.sh
./docker-start.sh
```

**Or with Docker Compose directly:**
```bash
docker-compose up --build
```

This will:
- Start MySQL 8.0 database container
- Build and start the Spring Boot application container
- Configure network and volumes automatically
- Application will be available at: http://localhost:8080
- MySQL will be available at: localhost:3306

**Docker commands:**
```bash
# Start services
docker-compose up -d

# View logs
docker-compose logs -f app

# Stop services
docker-compose down

# Stop and remove volumes (clean database)
docker-compose down -v
```

**Docker environment variables:**
- MySQL Root Password: `rootpassword`
- Database: `course_db`
- Application Profile: `docker`

### 3. Run locally (without Docker)

**Prerequisites:**
- Java 17+
- Maven 3.6+
- MySQL 8.0+ installed locally

**Database configuration:**

Create a MySQL database named `course_db` and update the file `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/course_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
```

**Run the project:**
```bash
mvn spring-boot:run
```

Or compile and run:
```bash
mvn clean install
java -jar target/course-api-0.0.1-SNAPSHOT.jar
```

## 📚 Hexagonal Architecture Concepts

### Domain

- **No external dependencies**: The domain does not depend on frameworks, databases, or technologies
- **Pure business logic**: Contains business rules and validations
- **Domain entities**: Models that represent business concepts

### Application

- **Orchestrates the domain**: Use cases coordinate domain operations
- **Ports**: Interfaces that define contracts
  - **Input Ports**: Define what the application can do (use cases)
  - **Output Ports**: Define what the application needs from outside (repositories)

### Infrastructure

- **Primary Adapters (Input)**: Receive requests from outside (REST Controllers)
- **Secondary Adapters (Output)**: Provide services to the domain (Database Repositories)

## 🔄 Data Flow

```
HTTP Client
    ↓
[Primary Adapter] StudentController (REST)
    ↓
[Input Port] StudentUseCase (interface)
    ↓
[Application Service] StudentService (implementation)
    ↓
[Domain Model] Student (business logic)
    ↓
[Output Port] StudentRepositoryPort (interface)
    ↓
[Secondary Adapter] StudentRepositoryAdapter
    ↓
[Infrastructure] StudentJpaRepository (JPA)
    ↓
Database
```

## 💡 Hexagonal Architecture Benefits

1. **Framework Independence**: The domain does not depend on Spring, JPA, or any framework
2. **Testability**: Easy to create mocks of ports and test the domain without a database
3. **Flexibility**: Changing databases (MySQL → PostgreSQL) only affects the adapter
4. **Separation of Concerns**: Each layer has a clear responsibility
5. **Maintainability**: More organized and easier to understand code

## 🏛️ SOLID Principles Applied

This project follows all SOLID principles throughout the codebase:

- ✅ **Single Responsibility Principle (SRP)**: Each class has a single, well-defined responsibility
- ✅ **Open/Closed Principle (OCP)**: Open for extension, closed for modification
- ✅ **Liskov Substitution Principle (LSP)**: Subtypes are substitutable for their base types
- ✅ **Interface Segregation Principle (ISP)**: Clients don't depend on interfaces they don't use
- ✅ **Dependency Inversion Principle (DIP)**: Depend on abstractions, not concretions

### Examples in the codebase:

- **SRP**: Services, controllers, and repositories have clear, single responsibilities
- **DIP**: Constructor injection used throughout (no field injection)
- **OCP**: Interfaces allow extension without modifying existing code
- **ISP**: Interfaces are focused and specific

## 🧹 Clean Code Practices

The codebase follows clean code principles:

- ✅ **Meaningful names**: Descriptive class, method, and variable names
- ✅ **Single return statements**: Methods refactored to have single exit points where appropriate
- ✅ **Small methods**: Methods do one thing and do it well
- ✅ **No code duplication**: DRY principle applied
- ✅ **Professional logging**: SLF4J logger instead of System.out.println
- ✅ **Proper error handling**: Structured exception handling with logging

## 📦 Docker Configuration

The project includes complete Docker setup:

### Files:
- **Dockerfile**: Multi-stage build for optimized image size
- **docker-compose.yml**: Orchestrates application and MySQL services
- **.dockerignore**: Optimizes build by excluding unnecessary files
- **application-docker.properties**: Spring Boot configuration for Docker environment

### Docker Features:
- ✅ Multi-stage build for smaller images
- ✅ Non-root user for security
- ✅ Health checks for both services
- ✅ Persistent volumes for database
- ✅ Automatic service dependency management
- ✅ Network isolation

### Access Points:
- **Application**: http://localhost:8080
- **API**: http://localhost:8080/api/v1/students
- **MySQL**: localhost:3306
  - Username: `root`
  - Password: `rootpassword`
  - Database: `course_db`

## 📖 Additional Documentation

The project includes examples and documentation about:

- **Prime Numbers**: Examples with threads and concurrent execution
- **Exception Handling**: Proper exception handling patterns
- **Testing**: Comprehensive tests with JUnit 5 and Mockito

## 👤 Author

Stiven Chávez

## 📄 License

This project is open source and available under the MIT License.
