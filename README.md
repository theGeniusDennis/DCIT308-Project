# Book Lending & Cataloguing System for Ebenezer Community Library

A Java console-based system for managing book lending, inventory, and reporting for the Ebenezer Community Library. Implements custom data structures and file-based storage as per DCIT308 project requirements.

## Group Project Plan (5 Days)

### Day 1: Setup & Core Models
- **Project Manager:** Set up GitHub repo, branches, assign roles, and coordinate setup.
- **Book Inventory Lead:** Design Book class structure and plan inventory logic.
- **Borrower Registry Lead:** Design Borrower class structure and plan registry logic.
- **Lending Tracker Lead:** Design Transaction class and plan lending logic.
- **Overdue Monitoring & Fines Lead:** Research and plan overdue/fine tracking structure.
- **Search & Sort Utilities Lead:** Plan search/sort algorithms and utility class structure.
- **Reports & Documentation Lead:** Outline report requirements and documentation structure.

### Day 2: Implement Core Classes & File I/O
- **Project Manager:** Ensure all members are on track, review initial code, and resolve blockers.
- **Book Inventory Lead:** Implement Book and BookInventory classes, start file I/O for books.
- **Borrower Registry Lead:** Implement Borrower and BorrowerRegistry classes, start file I/O for borrowers.
- **Lending Tracker Lead:** Implement Transaction and LendingTracker classes, start file I/O for transactions.
- **Overdue Monitoring & Fines Lead:** Implement overdue/fine data structures, integrate with LendingTracker.
- **Search & Sort Utilities Lead:** Implement basic search and sort methods for books and borrowers.
- **Reports & Documentation Lead:** Start drafting documentation and report templates.

### Day 3: Integrate Features & Advanced Logic
- **Project Manager:** Oversee integration, manage pull requests, and facilitate communication.
- **Book Inventory Lead:** Integrate inventory with search/sort utilities.
- **Borrower Registry Lead:** Integrate registry with lending and overdue logic.
- **Lending Tracker Lead:** Integrate lending/return logic with inventory and registry.
- **Overdue Monitoring & Fines Lead:** Implement overdue checks and fine updates.
- **Search & Sort Utilities Lead:** Refine and optimize search/sort algorithms.
- **Reports & Documentation Lead:** Implement initial report generation features.

### Day 4: Testing, Bug Fixes & Reporting
- **Project Manager:** Coordinate testing, assign bug fixes, and review progress.
- **Book Inventory Lead:** Test inventory features, fix bugs, and update documentation.
- **Borrower Registry Lead:** Test registry features, fix bugs, and update documentation.
- **Lending Tracker Lead:** Test lending/return features, fix bugs, and update documentation.
- **Overdue Monitoring & Fines Lead:** Test overdue/fine logic, fix bugs, and update documentation.
- **Search & Sort Utilities Lead:** Test search/sort features, fix bugs, and update documentation.
- **Reports & Documentation Lead:** Finalize report generation and documentation.

### Day 5: Final Integration, Review & Presentation Prep
- **Project Manager:** Lead final review, ensure all requirements are met, and prepare for presentation.
- **All Leads:** Final integration, polish code, ensure file persistence, and rehearse demo.
- **Reports & Documentation Lead:** Complete README, code comments, and performance analysis.

## Roles & Responsibilities
- **Project Manager:** Coordination, GitHub repo, reviews, and merges.
- **Book Inventory Lead:** Book and inventory logic, file storage.
- **Borrower Registry Lead:** Borrower management, recursive search, file storage.
- **Lending Tracker Lead:** Lending/return logic, transaction records.
- **Overdue Monitoring & Fines Lead:** Overdue tracking, fine calculation.
- **Search & Sort Utilities Lead:** Search and sorting algorithms.
- **Reports & Documentation Lead:** Reports, documentation, and performance analysis.

## Features
- Book inventory management
- Book search and sorting
- Borrower registry
- Lending tracker
- Overdue monitoring
- File logging and data persistence
- Reports and data analysis

## Structure
- `src/main/java/library/model/` – Data models
- `src/main/java/library/data/` – Inventory, registry, and lending logic
- `src/main/java/library/util/` – Utilities for file, search, and sort
- `src/main/java/library/report/` – Report generation
- `src/main/resources/` – Data files

## How to Run
Compile and run `Main.java` using any Java IDE or command line.
