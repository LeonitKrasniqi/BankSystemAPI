# BankSystemAPI

BankSystemAPI is a Java Spring Boot project designed to facilitate banking operations through a RESTful API. It provides endpoints to manage bank accounts, perform transfers, deposits, and withdrawals.

# Prerequisites
* Java 17
* Maven
* Docker

## Customizing Port
This Spring Boot application is hosted on port 8080. If you need to change the port, you can  modufy the `application.yml` file.

# Installation
* git clone https://github.com/LeonitKrasniqi/BankSystemAPI

# Docker
Run the following command to start the application and the required database container:
* docker compose up -d
  
# BankSystemAPI Usage
### Account Controller
#### Create Account
POST /api/v1/accounts/
e.g. 
{
    "id": 90,
    "name": "Niti",
    "amount": 100.0
}

#### Get Account By Id
GET /api/v1/accounts/{id}
e.g.
localhost:8080/api/v1/accounts/5
#### Delete Account by ID
DELETE /api/v1/accounts/{id}
e.g. 
localhost:8080/api/v1/accounts/45

### Bank Controller
#### Create Bank
POST /api/v1/banks/
e.g.
{
    "name": "TEB Bank",
    "transactionPercentFeeValue": 1.4
}


### TransactionController
#### Transfer money
POST /api/v1/transactions/transfer
e.g. 
{
    "originatingAccountId": 1,
    "resultingAccountId": 2,
    "amount": 100,
    "description": "Testing transfer",
    "bankId": 1
}

#### Deposit money
POST /api/v1/transactions/deposit
e.g.
{
    "originatingAccountId": 1,
    "amount": 100.00,
    "description": "Deposit into account",
    "bankId": 6
}

#### Withdraw money
POST /api/v1/transactions/withdraw
e.g.
{
    "originatingAccountId": 2,
    "amount": 10.00,
    "description": "Withdraw into account",
    "bankId": 1
}
#### Get Transactions by Account ID
GET /api/v1/transactions/account/{accountId}
e.g. localhost:8080/api/v1/transactions/account/5




# Key Libraries Used

- **Spring Boot Starter Web**
- **Spring Boot Starter Validation**
- **Lombok**
- **PostgreSQL**
- **Spring Boot Starter Data JPA**
- **Spring Boot Starter Test**


