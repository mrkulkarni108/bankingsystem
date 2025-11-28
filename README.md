# BankingSystem

A modular Java-based banking application with SQLite database integration.

---

## Introduction

BankingSystem is a lightweight, modular banking application built in Java. It demonstrates essential banking operations such as account creation, balance inquiry, deposits, withdrawals, and persistent database storage using SQLite.

This project is suitable for:

* Learning Java, JDBC, and SQLite
* Understanding modular Java application structure
* Academic/mini-project submissions
* Demonstrating CRUD operations in Java

---

## Features

* Create new bank accounts
* Deposit money
* Withdraw money
* View account details and balance
* Persistent data storage using SQLite
* Modular architecture: ATM, Accountant, and Server modules
* Cross-platform support (runs anywhere Java is available)

---

## Tech Stack

| Component    | Technology               |
| ------------ | ------------------------ |
| Language     | Java 8+                  |
| Database     | SQLite                   |
| JDBC Driver  | sqlite-jdbc-3.14.2.1.jar |
| Architecture | Modular Java project     |

---

## Project Structure

```
bankingsystem/
│
├── accountant/          # Account creation and management logic
├── atm/                 # ATM operations: deposit, withdraw, inquiry
├── server/              # Main application and server logic
│
├── bank.db              # SQLite database
├── sqlite-jdbc-3.14.2.1.jar
├── README.md
└── LICENSE (optional)
```

---

## Installation and Setup

### 1. Clone the repository

```
git clone https://github.com/mrkulkarni108/bankingsystem.git
cd bankingsystem
```

### 2. Verify Java installation

```
java -version
```

Ensure JDK 8 or newer is installed.

### 3. Compile the application

```
javac -cp ".;sqlite-jdbc-3.14.2.1.jar" */*.java
```

### 4. Run the application

```
java -cp ".;sqlite-jdbc-3.14.2.1.jar" server.Server
```

---

## System Architecture

### 1. Accountant Module (`accountant/`)

* Creates customer accounts
* Updates account details
* Manages account-related database interactions

### 2. ATM Module (`atm/`)

* Deposit operations
* Withdrawal operations
* Balance inquiries
* Handles input validation

### 3. Server Module (`server/`)

* Loads JDBC driver
* Initializes database
* Acts as the entry point for the program
* Routes commands between modules

### 4. Database (`bank.db`)

Stores information such as:

* Account number
* Customer name
* Account balance
* Timestamped data entries

---

## Future Improvements

* Add a graphical user interface (JavaFX or Swing)
* Implement authentication and admin login
* Add transaction history tracking
* Support fund transfers between accounts
* Add printed or PDF mini-statements
* Convert to REST API using Spring Boot
* Add customer login using encrypted passwords
* Account editing and deletion functionality

---

## Contributing

Contributions are welcome. To contribute:

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push your branch
5. Submit a pull request

---

## License

This project may be distributed under the MIT License.

---

## Authors

1. Chinmay Kulkarni
2. Aditya Raj
3. Nandini Prayagi
4. Doyel Patil

---

## Support

If you find this project useful, consider giving it a star on GitHub.
