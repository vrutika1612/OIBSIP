# Online Reservation System

A GUI-based train reservation application for Oasis Infobyte OIBSIP Task 1. It uses Java Swing, JDBC, SQLite and Maven with a layered architecture suitable for a code-review exercise.

## Project Overview
Users authenticate, enter a train number, automatically load train details, create a reservation with a generated numeric PNR, retrieve a reservation by PNR and cancel it after confirmation.

## Features
- Login with hashed passwords
- Train lookup using JDBC `PreparedStatement`
- Reservation creation with unique 10-digit PNR
- PNR retrieval and cancellation
- Input validation and friendly error handling
- SQLite initialization with idempotent schema/sample data
- Transactional booking
- Java Util Logging
- JUnit 5 unit/integration-style database tests

## Technology Stack
- Java 17
- Swing
- JDBC
- SQLite
- Maven
- JUnit 5

## Architecture
GUI -> Service -> DAO -> JDBC -> SQLite

The GUI does not contain SQL. Services own business rules. DAOs own persistence. Validation and utility concerns are separated.

## Project Structure
See the complete source tree included with the project.

## Database Schema
`users` stores usernames and PBKDF2 password hashes. `trains` stores sample train master data. `reservations` stores bookings with a 10-digit PNR primary key and a foreign key to `trains`.

## Application Flow
1. Start application.
2. Database is created and initialized if necessary.
3. Login as the demo user.
4. Open Book Reservation.
5. Enter a train number such as 12951; train name is loaded automatically.
6. Complete the form and book.
7. Open Cancel Reservation and fetch the PNR.
8. Confirm cancellation.
9. Logout without restarting the application.

## How to Install
Install JDK 17+ and Maven 3.9+.

## How to Configure
The default configuration is `jdbc:sqlite:reservation.db` in `src/main/resources/application.properties`. Tests override this using a temporary database system property.

## How to Run
```bash
mvn clean package
java -jar target/online-reservation-system-1.0.0.jar
```

## Sample Login Credentials
Username: `admin`
Password: `admin123`

This account is created only when the users table is empty. Change the password implementation before using this as a real application.

## How to Test
```bash
mvn clean test
```

## Maven Commands
```bash
mvn clean test
mvn clean package
```

## Validation
Required fields, numeric train number/PNR, date format, non-past journey date, train existence, and different source/destination are validated.

## Security Considerations
SQL uses prepared statements. Passwords are never stored as plaintext; the demo implementation uses PBKDF2-HMAC-SHA256 with a per-password random salt. Passwords and hashes are not logged. This is an educational desktop application and is not a real railway production system.

## Screenshots
The `screenshots/` directory is intentionally empty until real screenshots are captured from the running application. No fake screenshots are included.

## Future Improvements
- Role-based administration for train master data
- Stronger secret/configuration management
- Audit history for cancellations
- Automated UI tests
- Exportable booking receipts

## OIBSIP Task Information
- Program: Oasis Infobyte SIP (OIBSIP)
- Task: Java Task 1 - Online Reservation System

## Author
Vrutika Patel
