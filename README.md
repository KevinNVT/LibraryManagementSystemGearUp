# LibraryManagementSystemGearUp
Project for GearUp

### Book Management
- Add new books.
- Remove existing books.
- Update book information.
- Search for books by different criteria (title, author, ISBN).

### Member Management
- Register new members.
- Remove existing members.
- Update member information.
- Search for members by different criteria (name, ID).

### Loan Transaction
- Register a book loan.
- Register the return of a book.
- View a member's loan history.
- Check the availability of a book.

### Non-functional Requirements
- User Interface: Simple console for user interactions.
- Persistence: Use a MySQL database to store data about books, members, and transactions.
- Exception Handling: Properly handle all possible exceptions that may arise during system operations.
- Documentation: Well-documented code, clear instructions on how to set up and run the system.

### Tips and Best Practices Data Validation
- Ensure that all input data is validated to avoid errors and security issues.Exception Handling
- Handle all possible exceptions appropriately to prevent the application from failing unexpectedly.Modularity
- Keep the code modular and provide clear instructions on how to set up and run the application.Testing
- Perform thorough testing of all functionalities to ensure that the system works correctly in all possible scenarios.

## Design
### Conceptual model
Represents important objects and the relationships between them.
#### Nouns (Objects)
- *Library.*
- Book.
- Member.
- Loan.
  
#### Verbs (Classes)
- Add.
- Delete.
- Update.
- Search by.
  
- Register member.
- Delete member.
- Update member.
- Search by.

- Loan book.
- Return book.
- Watch history.
- Check for disponibility.

  While it might seem intuitive to have a *Library* class to represent the entire library system, this approach can lead to several design issues. I would violate the Single Responsibility Principle (SRP), I would have some issues with modularity, extending functionality, testing and maintaining my application. I had some issues trying to do my UML diagram so I went back to this part and I decided to delete a single overarching *Library* class.

### Designing my DB
#### My DB

```mysql
-- Create the DB
CREATE DATABASE DB_LIBRARY;

-- Use the DB we created 
USE DB_LIBRARY;
```
#### My tables

```mysql
CREATE TABLE tbl_books (
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(100) NOT NULL,
  author VARCHAR(100) NOT NULL,
  isbn VARCHAR(13) NOT NULL,
  available BOOLEAN DEFAULT TRUE
);
```

```mysql
CREATE TABLE tbl_members (
  id INT AUTO_INCREMENT PRIMARY KEY,
  member_name VARCHAR(100) NOT NULL,
  member_id VARCHAR(20) NOT NULL
);
```

```mysql
CREATE TABLE tbl_loans (
  id INT AUTO_INCREMENT PRIMARY KEY,
  book_id INT NOT NULL,
  member_id INT NOT NULL,
  loan_date DATE NOT NULL,
  return_date DATE,
  returned BOOLEAN DEFAULT FALSE,
  FOREIGN KEY (book_id) REFERENCES tbl_books(id),
  FOREIGN KEY (member_id) REFERENCES tbl_members(id)
);
```

1. `tbl_books` table:
    - `id` (INT, AUTO_INCREMENT, PRIMARY KEY): Unique identifier for the book.
    - `title` (VARCHAR): Title of the book, marked as NOT NULL.
    - `author` (VARCHAR): Author of the book, marked as NOT NULL.
    - `isbn` (VARCHAR): ISBN of the book, marked as NOT NULL.
    - `available` (BOOLEAN, DEFAULT TRUE): Indicates the availability of the book, defaults to TRUE, so I do not need to restrict it with NOT NULL.

1. `tbl_members` table:
    - `id` (INT, AUTO_INCREMENT, PRIMARY KEY): Unique identifier for the member.
    - `member_name` (VARCHAR): Name of the member, marked as NOT NULL.
    - `member_id` (VARCHAR): ID of the member, marked as NOT NULL.

1. `tbl_loans` table:
    - `id` (INT, AUTO_INCREMENT, PRIMARY KEY): Unique identifier for the loan.
    - `member_id` (INT, FOREIGN KEY REFERENCES tbl_members(id)): Identifier of the member who made the loan, marked as NOT NULL.
    - `book_id` (INT, FOREIGN KEY REFERENCES tbl_books(id)): Identifier of the borrowed book, marked as NOT NULL.
    - `loan_date` (DATE): Date of the loan, marked as NOT NULL.
    - `return_date` (DATE): Date of return, marked as NOT NULL.
    - `returned` (BOOLEAN, DEFAULT FALSE): Indicates if the book has been returned, defaults to FALSE.
