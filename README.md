
# Book Lending & Cataloguing System for Ebenezer Community Library

A Java console-based system for managing book lending, inventory, and reporting for the Ebenezer Community Library. Implements custom data structures and file-based storage as per DCIT308 project requirements.

---

## Table of Contents
1. [Project Overview](#project-overview)
2. [Features](#features)
3. [Project Structure](#project-structure)
4. [How to Run](#how-to-run)
5. [Example Usage](#example-usage)
6. [Data Structures & Algorithms](#data-structures--algorithms)
7. [Known Limitations & Future Work](#known-limitations--future-work)
8. [Team Roles & Project Plan](#team-roles--project-plan)

---

## Project Overview
This system provides a robust, offline-first solution for managing book lending, inventory, overdue tracking, and reporting for a community library. All logic and storage are handled through custom Java data structures and file-based persistence—no external databases or frameworks are used.

## Features
- Book inventory management (add, remove, list, group)
- Book search (linear, binary) and sorting (selection, merge)
- Borrower registry (add, remove, search)
- Lending tracker (borrow, return, transaction history)
- Overdue monitoring and fine calculation
- File-based data persistence for all entities
- Reports: most borrowed books, top fines, inventory by category

## Project Structure
- `src/main/java/library/model/` – Data models (`Book`, `Borrower`, `Transaction`)
- `src/main/java/library/data/` – Inventory, registry, and lending logic
- `src/main/java/library/util/` – File, search, and sort utilities
- `src/main/java/library/report/` – Report generation
- `src/main/resources/` – Data files (`books.txt`, `borrowers.txt`, `transactions.txt`)

## How to Run
### Using Command Line
1. Open a terminal in the project root directory.
2. Compile all Java files:
   ```sh
   javac -d bin src/main/java/library/**/*.java
   ```
3. Run the main program:
   ```sh
   java -cp bin library.Main
   ```

### Using an IDE (e.g., IntelliJ, Eclipse, VS Code)
1. Import the project as a Java project.
2. Set the main class to `library.Main`.
3. Run the project.

## Example Usage
```
Welcome to Ebenezer Community Library System
1. Add Book
2. Remove Book
3. List Books
4. Add Borrower
5. Borrow Book
6. Return Book
7. Generate Reports
0. Exit
Enter your choice: 1
Enter book details...
...etc.
```

## Data Structures & Algorithms
**BookInventory, BorrowerRegistry, and LendingTracker** use custom implementations of:
- **HashMap/Dictionary:** For fast lookup of books and borrowers by ISBN/ID.
- **ArrayList/LinkedList:** For storing lists of books, borrowers, and transactions.
- **Queue/Stack:** For managing transaction history and overdue processing.

**Search & Sort:**
- **Linear Search:** Used for flexible, partial matches (e.g., search by title/author).
- **Binary Search:** Used for fast lookup when data is sorted (e.g., by ISBN).
- **Selection Sort:** Simple, stable sort for small lists (e.g., by title).
- **Merge Sort:** Efficient, stable sort for larger lists (e.g., by borrow count).

**Justification:**
- Custom data structures and algorithms were chosen to meet the project’s educational requirements and to demonstrate understanding of core Java concepts. File-based storage ensures offline operation and transparency.

## Known Limitations & Future Work
- No GUI; console-only interface.
- No concurrent access or multi-user support.
- Data files must be manually cleaned for duplicates or formatting issues.
- No automated tests (manual testing recommended).
- Future work: add GUI, automated tests, advanced analytics, and user authentication.

## Team Roles & Project Plan

### Roles & Responsibilities
- **Project Manager:** Coordination, GitHub repo, reviews, and merges.
- **Book Inventory Lead:** Book and inventory logic, file storage.
- **Borrower Registry Lead:** Borrower management, recursive search, file storage.
- **Lending Tracker Lead:** Lending/return logic, transaction records.
- **Overdue Monitoring & Fines Lead:** Overdue tracking, fine calculation.
- **Search & Sort Utilities Lead:** Search and sorting algorithms.
- **Reports & Documentation Lead:** Reports, documentation, and performance analysis.

### Group Project Plan (5 Days)
- **Day 1:** Setup & Core Models
- **Day 2:** Implement Core Classes & File I/O
- **Day 3:** Integrate Features & Advanced Logic
- **Day 4:** Testing, Bug Fixes & Reporting
- **Day 5:** Final Integration, Review & Presentation Prep

See project comments and code for further details.
