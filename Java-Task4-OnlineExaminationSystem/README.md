# Online Examination System

## Project Overview
Professional Java Swing desktop application for a timed MCQ examination, built for Oasis Infobyte Task 4.

## Features
- Securely hashed local demo password
- Login, profile update and password change
- 20 Java/programming MCQs
- One-question-at-a-time navigation with saved answers
- 30-minute Swing Timer with automatic submission
- Manual submission confirmation and duplicate-submission protection
- Detailed result and question-wise breakdown
- Session management and close-window protection during exam

## Technology Stack
Java 17, Swing, Maven, JUnit 5. No external database.

## Architecture
Layered design: model, repository, service, session, and UI packages. UI uses one JFrame + CardLayout.

## Project Structure
See `src/main/java/com/oibsip/onlineexam/` for model, repository, service, session, UI and utility layers.

## Requirements
JDK 17+ and Maven 3.8+.

## Installation / Running
```bash
mvn clean package
mvn exec:java
```
Tests:
```bash
mvn test
```

## Default Login Credentials
Username: `student`  
Password: `student123`

## How to Use
1. Login.
2. Update profile if needed.
3. Start examination.
4. Select answers.
5. Use Previous/Next.
6. Submit manually or wait for timeout.
7. Review result.
8. Logout.

## Timer Behaviour
Official duration is 30 minutes in `Constants.EXAM_DURATION_SECONDS`. The UI uses `javax.swing.Timer`; no `Thread.sleep()` is used. For a demo, temporarily change the constant to `60` seconds, then restore it to `30*60` before submission.

## Session Management
`SessionManager` holds the logged-in user and active `ExamSession`. Closing the window during an active exam requires confirmation and warns that progress will be lost.

## Testing
JUnit 5 covers login success/failure, empty credentials, password validation, scoring, percentage, unanswered questions, duplicate submission and remaining-time safety. Run `mvn test`.

## Screenshots
Capture real screenshots from the running application; no fake screenshots are included. Recommended captures: Login, Profile, Exam, Timer running, Submit confirmation, Result, Auto-submit.

## Future Enhancements
SQLite persistence, admin question management, randomized question sets, secure backend authentication, audit logging, exportable result reports.

## Author
Vrutika Patel  
Internship: Oasis Infobyte  
Task 4 - Online Examination System

## GitHub Upload
Place this folder at `OIBSIP/Java-Task4-OnlineExaminationSystem/`. See `GITHUB_UPLOAD_GUIDE.md`.
