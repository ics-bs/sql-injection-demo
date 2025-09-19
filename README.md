# Minimal JavaFX Example — SQL Injection (Educational)

**Purpose**  
A minimal JavaFX example that intentionally contains a SQL Injection vulnerability for educational and testing purposes only. Use this project in a safe, isolated test environment to learn how SQL Injection works and how to defend against it.

> **Warning:** Do **not** run this against production data or any system connected to the internet. Run only on an isolated development database or VM.

---

## Requirements

- JDK (Java 11+ recommended)
- JavaFX (matching your JDK version)
- JDBC driver for Microsoft SQL Server (e.g., `mssql-jdbc`)
- Microsoft SQL Server instance (local or test VM)

---

## Patterns Used

- Model–View–Controller (MVC)  
- Data Access Object (DAO)

---

## Project Structure (relevant paths)

    src/
      main/
        java/       # Java source (MVC + DAO)
        resources/
          Config/
            config.properties
          sql/
            ddl.sql

---

## Setup Instructions

1. **Create configuration folder**  
   Create a folder named `Config` under `src/main/resources`.

2. **Create `config.properties`**  
   Add a `config.properties` file with the following keys and appropriate values for your environment.

    Example (`src/main/resources/Config/config.properties`):

        # src/main/resources/Config/config.properties
        database.server.name=localhost
        database.server.port=1433
        database.name=YourDatabaseName
        database.user.name=your_db_user
        database.user.password=your_db_password

   Replace the placeholder values above with your SQL Server host, port, database name and credentials.

3. **Create / Initialize database**  
   Run the provided DDL script to create and populate the tables.

   File path:

    src/main/resources/sql/ddl.sql

   Execute this script using SQL Server Management Studio (SSMS), `sqlcmd`, or any database client connected to your test SQL Server instance.

4. **Add JDBC driver**  
   Ensure the Microsoft SQL Server JDBC driver is on your classpath (Maven, Gradle, or manually add the JAR).

5. **Run the app**  
   Launch the JavaFX application from your IDE or by building and running the generated JAR.

---

## Example `ddl.sql` (snippet)

Place this file at `src/main/resources/sql/ddl.sql`. This is a *sample* schema — adapt it to your code expectations.

    CREATE TABLE Employee (
        Id INT IDENTITY(1,1) PRIMARY KEY,
        Name NVARCHAR(100),
        Salary INT
    );

    INSERT INTO Employee (Name, Salary) VALUES ('Alice', 50000);
    INSERT INTO Employee (Name, Salary) VALUES ('Bob', 60000);

---

## Example `config.properties` (ready-to-edit)

    # src/main/resources/Config/config.properties
    database.server.name=localhost
    database.server.port=1433
    database.name=TestDB
    database.user.name=testuser
    database.user.password=SuperSecretPassword

---

## Testing Instructions (demonstrate the vulnerability)

1. Launch the JavaFX GUI application.  
2. In the GUI fields (for example, `Name` or `Salary`), enter a malicious input to exercise the vulnerability. Example injection string (for demonstration in an isolated test DB):

    lol pwned', 1337); DELETE FROM Employee; --

   This payload closes the current SQL value list, inserts a new row, and appends a `DELETE` statement — demonstrating how concatenated SQL can be exploited.

3. After submitting the form, inspect the database (using SSMS or a query tool) to see whether rows were deleted or unexpected changes occurred.

---

## Security Notes & Mitigation
- This project intentionally shows **unsafe** concatenation of user input into SQL statements.  
- **Mitigation:** use prepared statements / parameterized queries (`PreparedStatement` in JDBC), input validation, least-privilege DB users, and stored procedures where appropriate.  
