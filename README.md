
# Clinic Management System (Java CLI + SQLite)

A command-line application for managing hospital operations, including Doctors, Patients, Appointments, and Billing. The system uses Java and SQLite for data storage, with modular code organization for maintainability and extensibility.

## Features

- CRUD operations for doctors, patients, appointments, and billing
- Command-line interface for quick data entry and management
- Centralized SQLite database connection and helper utilities
- Modular package structure for app logic, database access, and management roles
- Easily extensible for new entities or features

## Tech Stack

- Java 8+
- SQLite (via JDBC)

## Project Structure

```
Clinic-System-CLI/
├─ clinic_management.db          # SQLite database file
├─ pom.xml                         # Maven build file
├─ run_app.cmd                     # Windows batch file to run the app
├─ src/
│  ├─ sqlite-jdbc.jar              # SQLite JDBC driver
│  ├─ main/
│  │  └─ java/
│  │     └─ com/
│  │        └─ hospital/
│  │           ├─ app/
│  │           │  └─ Main.java                # Application entry point
│  │           ├─ db/
│  │           │  ├─ DatabaseHelper.java      # SQLite connection and helpers
│  │           │  └─ DatabaseSeed.java        # Initial data seeding
│  │           └─ manager/
│  │              ├─ AdminManager.java
│  │              ├─ DoctorManager.java
│  │              ├─ ReceptionistManager.java
│  │              └─ AccountantManager.java
│  └─ resources/
└─ test/
   └─ java/
```

## Database Setup

1. No external setup required—uses local `clinic_management.db` SQLite file.
2. If starting fresh, the app will seed initial data via `DatabaseSeed.java`.
3. The database file is created automatically if missing.

## Running the Application

1. Ensure Java 8+ is installed.
2. Double-click `run_app.cmd` or run it from the command line.
3. Alternatively, run the `Main` class in your IDE (IntelliJ, Eclipse, VS Code).
4. Follow CLI prompts to manage hospital data.

## Extending the System

To add a new entity or feature:

1. Create a new manager class in `com.clinic.manager`.
2. Add relevant database logic in `com.clinic.db.DatabaseHelper`.
3. Update `Main.java` to include new CLI options.

## Contribution Guidelines

- Keep code modular and methods focused.
- Follow existing package structure.
- Do not commit credentials or machine-specific configs.
- Use consistent formatting (IntelliJ default is fine).

### Workflow

1. Fork the repository.
2. Create a feature branch (`feat/<feature>`, `fix/<bug>`, etc.).
3. Commit with clear messages.
4. Open a Pull Request describing changes and testing steps.

### Testing Checklist

- [ ] Application starts without errors
- [ ] CRUD works for all entities
- [ ] No hardcoded credentials
- [ ] CLI prompts and flows are clear

## Reporting Issues

Please include:

- Steps to reproduce
- Expected vs actual behavior
- Stack trace (if any)
- Screenshot (if relevant)

## Roadmap (Suggestions)

- Add user authentication and roles
- Migrate to Maven/Gradle for dependency management
- Add logging and configuration files
- Implement search/filtering in CLI
- Export data (CSV/PDF)

## License

MIT

---

Encounter issues? Open an issue with logs, stack trace, and environment details (OS, JDK version).
