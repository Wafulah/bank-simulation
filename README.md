# Simple Bank Simulation Project

## Overview

This is a **Simple Bank Simulation** application designed to demonstrate fundamental banking operations. The application allows users to create accounts, perform fund transfers, and manage currency conversions while tracking transactions.

The project uses **Spring Boot** as its backend framework, following best practices for clean code and scalability. While the current implementation provides core functionalities, there are areas for enhancement, including logging, robust handling of edge cases, and comprehensive testing.

## Features

- **User Management**: Create and authenticate users to access their accounts securely.
- **Account Management**: Create and retrieve accounts associated with users.
- **Fund Transfers**: Transfer funds between accounts securely.
- **Currency Exchange Rates**: View current exchange rates.
- **Currency Conversion**: Convert funds between currencies.

## Project Structure

The project is organized into the following directories and packages:

```bash
src/
├── main/
│   ├── java/com/bank/bank_simulation
│   │   ├── controller       # REST API controllers for endpoints
│   │   ├── dto              # Data Transfer Objects for API requests/responses
│   │   ├── entity           # Database entities representing domain objects
│   │   ├── service          # Business logic for banking operations
│   │   └── repository       # Data access layer for interacting with the database
│   ├── resources/
│   │   |── application.properties  # Application configuration
│   │   
├── test/
│   └── java/com/bank/bank_simulation
│       └── AccountServiceTest          # Unit and integration tests for services
```

## Technologies and Dependencies

- **Spring Boot**: Core framework for the backend.
- **Spring Security**: Handles authentication and authorization.
- **Lombok**: Reduces boilerplate code with annotations like `@Getter` and `@RequiredArgsConstructor`.
- **H2 Database**: In-memory database for development and testing.
- **Spring Data JPA**: Simplifies database operations.
- **Validation API**: Ensures data integrity through annotations like `@Valid`.
- **Logback (SLF4J)**: Logging framework for structured logs.
- **Maven**: Dependency management and project build.

## Endpoints

| Endpoint            | HTTP Method | Description                                             |
|---------------------|-------------|---------------------------------------------------------|
| `/accounts`         | POST        | Create a new account for the authenticated user.       |
| `/accounts`         | GET         | Retrieve all accounts for the authenticated user.      |
| `/accounts/transfer`| POST        | Transfer funds between accounts.                       |
| `/accounts/rates`   | GET         | Retrieve current exchange rates for supported currencies. |
| `/accounts/find`    | POST        | Find an account using its code and recipient number.   |
| `/accounts/convert` | POST        | Convert currency between accounts for the authenticated user. |

## Key Classes and Their Roles

### AccountController
- Manages API endpoints for account-related operations.
- Uses Spring Security's Authentication to ensure only authorized users can perform operations.

### AccountService
- Contains business logic for account creation, transfers, and conversions.
- Interacts with the repository layer for data persistence.

### Data Transfer Objects
- **AccountDto**, **TransferDto**, **ConvertDto**: Used for API requests and responses.

### Entities
- **Account**, **Transaction**, **User**: Represent the core domain models.

### AccountRepository
- JPA repository for performing database operations.

## Areas for Improvement

### Logging
- Add structured logging using SLF4J to track operations like account creation, transfers, and conversions.
- Use log levels (`INFO`, `DEBUG`, `ERROR`) for better traceability.

### Edge Case Handling
- Ensure proper error handling for scenarios like insufficient funds, invalid account numbers, or unsupported currencies.
- Implement global exception handling with a `@ControllerAdvice`.

### Testing
- Add unit tests for business logic in `AccountService`.
- Perform integration tests for API endpoints.
- Test edge cases for invalid data, authentication failures, and concurrency issues.

### Security Enhancements
- Validate user permissions before allowing sensitive operations like transfers and currency conversion.

### Scalability
- Ensure the application is optimized for PostgreSQL, leveraging its scalability features for handling large amounts of data in production.
- Implement caching for frequently accessed data like exchange rates to reduce database load and improve response time.


## Example Workflow

1. **Create a User**:
   - Register a new user through an authentication service (not covered here).
   - Authenticate the user and obtain a token.

2. **Create an Account**:
   - Call the `/accounts` POST endpoint with account details (e.g., account type, currency).

3. **View User Accounts**:
   - Retrieve all accounts linked to the authenticated user using the `/accounts` GET endpoint.

4. **Transfer Funds**:
   - Use the `/accounts/transfer` POST endpoint to transfer funds between accounts.

5. **Currency Conversion**:
   - Call the `/accounts/convert` POST endpoint to convert currency between accounts.

## How to Run the Project

### Clone the Repository

```bash
git clone https://github.com/username/bank-simulation.git
cd bank-simulation
```

### Build the Project

```bash
mvn clean install
```

### Run the Application

```bash
mvn spring-boot:run
```

### Access the API

Open [http://localhost:8080/accounts](http://localhost:8080/accounts) for account-related operations.

## Future Enhancements

- **Integration with Payment Gateways**:
  - Add M-Pesa, Stripe, or PayPal for real-world transactions.
- **Enhanced Reporting**:
  - Generate user-friendly reports for transactions and account balances.
- **AI-Driven Insights**:
  - Use AI to analyze spending habits and suggest savings or investment options.

## Contributing

1. Fork the repository.
2. Create a feature branch.
3. Submit a pull request with detailed documentation for review.

## License

This project is licensed under the **MIT License**.