package library;

import library.data.BookInventory;
import library.data.BorrowerRegistry;
import library.data.LendingTracker;
import library.model.Book;
import library.model.Borrower;
import library.model.Transaction;
import java.util.*;

/**
 * Main class for the Ebenezer Community Library System.
 * Handles the main menu, user input, and integrates all core features:
 * book inventory, borrower registry, lending/return, overdue/fines, search/sort, and reporting.
 * Loads and saves data from/to files for persistence.
 */
public class Main {
    // Scanner for user input
    private static final Scanner scanner = new Scanner(System.in);
    // Core data managers
    private static final BookInventory bookInventory = new BookInventory();
    private static final BorrowerRegistry borrowerRegistry = new BorrowerRegistry();
    private static final LendingTracker lendingTracker = new LendingTracker();
    // File paths for persistence
    private static final String BOOKS_FILE = "src/main/resources/books.txt";
    private static final String BORROWERS_FILE = "src/main/resources/borrowers.txt";
    private static final String TRANSACTIONS_FILE = "src/main/resources/transactions.txt";

    /**
     * Program entry point. Loads data, displays menu, and handles user choices.
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the Ebenezer Community Library System!");
        // Load books, borrowers, and transactions from file at startup
        try {
            bookInventory.loadFromFile(BOOKS_FILE);
            System.out.println("[Loaded books from file]");
        } catch (Exception e) {
            System.out.println("[No books file found or error loading books]");
        }
        try {
            borrowerRegistry.loadFromFile(BORROWERS_FILE);
            System.out.println("[Loaded borrowers from file]");
        } catch (Exception e) {
            System.out.println("[No borrowers file found or error loading borrowers]");
        }
        try {
            lendingTracker.loadFromFile(TRANSACTIONS_FILE);
            System.out.println("[Loaded transactions from file]");
        } catch (Exception e) {
            System.out.println("[No transactions file found or error loading transactions]");
        }
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1": addBookMenu(); saveBooks(); break;
                case "2": removeBookMenu(); saveBooks(); break;
                case "3": listBooksMenu(); break;
                case "4": addBorrowerMenu(); saveBorrowers(); break;
                case "5": listBorrowersMenu(); break;
                case "6": borrowBookMenu(); saveBorrowers(); saveTransactions(); break;
                case "7": returnBookMenu(); saveBorrowers(); saveTransactions(); break;
                case "8": overdueMenu(); saveBorrowers(); saveTransactions(); break;
                case "9": reportMenu(); break;
                case "10": searchSortMenu(); break;
                case "0": running = false; break;
                default: System.out.println("Invalid option. Try again.");
            }
        }
        // Save all data to file on exit
        saveBooks();
        saveBorrowers();
        saveTransactions();
        System.out.println("Goodbye!");
    }

    private static void saveBooks() {
        try {
            bookInventory.saveToFile(BOOKS_FILE);
        } catch (Exception e) {
            System.out.println("[Error saving books to file]");
        }
    }

    private static void saveBorrowers() {
        try {
            borrowerRegistry.saveToFile(BORROWERS_FILE);
        } catch (Exception e) {
            System.out.println("[Error saving borrowers to file]");
        }
    }

    private static void saveTransactions() {
        try {
            lendingTracker.saveToFile(TRANSACTIONS_FILE);
        } catch (Exception e) {
            System.out.println("[Error saving transactions to file]");
        }
    }

    private static void printMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Add Book");
        System.out.println("2. Remove Book");
        System.out.println("3. List All Books");
        System.out.println("4. Add Borrower");
        System.out.println("5. List All Borrowers");
        System.out.println("6. Borrow Book");
        System.out.println("7. Return Book");
        System.out.println("8. Check Overdue & Update Fines");
        System.out.println("9. Reports");
        System.out.println("10. Search/Sort Books");
        System.out.println("0. Exit");
        System.out.print("Select option: ");
    }

    private static void reportMenu() {
        System.out.println("\nReports:");
        System.out.println("1. Most Borrowed Books (This Month)");
        System.out.println("2. Borrowers with Highest Fines");
        System.out.println("3. Inventory by Category");
        System.out.print("Select report: ");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                List<library.model.Book> mostBorrowed = library.report.ReportGenerator.mostBorrowedBooks(
                    lendingTracker.getAllTransactions(), bookInventory.listAllBooks(), java.time.LocalDate.now().getMonthValue(), java.time.LocalDate.now().getYear());
                System.out.println("Most Borrowed Books:");
                mostBorrowed.forEach(System.out::println);
                break;
            case "2":
                List<library.model.Borrower> topFines = library.report.ReportGenerator.topFines(borrowerRegistry.listAllBorrowers());
                System.out.println("Borrowers with Highest Fines:");
                topFines.forEach(System.out::println);
                break;
            case "3":
                java.util.Map<String, Integer> catMap = library.report.ReportGenerator.inventoryByCategory(bookInventory.listAllBooks());
                System.out.println("Inventory by Category:");
                for (var entry : catMap.entrySet()) {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                }
                break;
            default:
                System.out.println("Invalid report option.");
        }
    }

    private static void searchSortMenu() {
        System.out.println("\nSearch/Sort Books:");
        System.out.println("1. Search by Title (Linear)");
        System.out.println("2. Search by ISBN (Binary, sorted by ISBN)");
        System.out.println("3. Sort by Title (Selection Sort)");
        System.out.println("4. Sort by Year (Merge Sort)");
        System.out.print("Select option: ");
        String choice = scanner.nextLine();
        List<library.model.Book> books = bookInventory.listAllBooks();
        switch (choice) {
            case "1":
                System.out.print("Enter title: ");
                String title = scanner.nextLine();
                library.model.Book found = library.util.SearchUtils.linearSearchByTitle(books, title);
                System.out.println(found != null ? found : "Book not found.");
                break;
            case "2":
                books.sort(Comparator.comparing(library.model.Book::getIsbn));
                System.out.print("Enter ISBN: ");
                String isbn = scanner.nextLine();
                library.model.Book foundIsbn = library.util.SearchUtils.binarySearchByIsbn(books, isbn);
                System.out.println(foundIsbn != null ? foundIsbn : "Book not found.");
                break;
            case "3":
                library.util.SortUtils.selectionSortByTitle(books);
                System.out.println("Books sorted by title:");
                books.forEach(System.out::println);
                break;
            case "4":
                library.util.SortUtils.mergeSortByYear(books);
                System.out.println("Books sorted by year:");
                books.forEach(System.out::println);
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private static void overdueMenu() {
        lendingTracker.updateOverdueFines(borrowerRegistry);
        List<Transaction> overdue = lendingTracker.getOverdueTransactions();
        if (overdue.isEmpty()) {
            System.out.println("No overdue books.");
        } else {
            System.out.println("Overdue books:");
            for (Transaction t : overdue) {
                System.out.println(t);
            }
        }
        System.out.println("Fines updated for overdue borrowers.");
    }

    private static void addBookMenu() {
        System.out.print("Title: "); String title = scanner.nextLine().trim();
        if (title.isEmpty()) {
            System.out.println("[Error] Book title cannot be empty. Please enter a valid title.");
            return;
        }
        System.out.print("Author: "); String author = scanner.nextLine().trim();
        if (author.isEmpty()) {
            System.out.println("[Error] Author cannot be empty. Please enter a valid author name.");
            return;
        }
        System.out.print("ISBN: "); String isbn = scanner.nextLine().trim();
        if (isbn.isEmpty()) {
            System.out.println("[Error] ISBN cannot be empty. Please enter a valid ISBN.");
            return;
        }
        if (bookInventory.getBookByIsbn(isbn) != null) {
            System.out.println("[Error] A book with this ISBN already exists. Please use a unique ISBN.");
            return;
        }
        System.out.print("Category: "); String category = scanner.nextLine().trim();
        if (category.isEmpty()) {
            System.out.println("[Error] Category cannot be empty. Please enter a valid category.");
            return;
        }
        System.out.print("Year: ");
        int year;
        try {
            year = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("[Error] Invalid year. Please enter a valid numeric year (e.g., 2024).");
            return;
        }
        System.out.print("Publisher: "); String publisher = scanner.nextLine().trim();
        if (publisher.isEmpty()) {
            System.out.println("[Error] Publisher cannot be empty. Please enter a valid publisher.");
            return;
        }
        System.out.print("Shelf Location: "); String shelf = scanner.nextLine().trim();
        if (shelf.isEmpty()) {
            System.out.println("[Error] Shelf location cannot be empty. Please enter a valid shelf location.");
            return;
        }
        Book book = new Book(title, author, isbn, category, year, publisher, shelf);
        bookInventory.addBook(book);
        System.out.println("Book added.");
    }

    private static void removeBookMenu() {
        System.out.print("Enter ISBN to remove: ");
        String isbn = scanner.nextLine().trim();
        if (isbn.isEmpty()) {
            System.out.println("[Error] ISBN cannot be empty. Please enter a valid ISBN.");
            return;
        }
        Book removed = bookInventory.removeBook(isbn);
        if (removed != null) {
            System.out.println("Book removed: " + removed);
        } else {
            System.out.println("[Error] Book not found. Please check the ISBN and try again.");
        }
    }

    private static void listBooksMenu() {
        List<Book> books = bookInventory.listAllBooks();
        if (books.isEmpty()) System.out.println("No books in inventory.");
        else books.forEach(System.out::println);
    }

    private static void addBorrowerMenu() {
        System.out.print("Name: "); String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("[Error] Name cannot be empty. Please enter a valid name.");
            return;
        }
        System.out.print("ID Number: "); String id = scanner.nextLine().trim();
        if (id.isEmpty()) {
            System.out.println("[Error] ID cannot be empty. Please enter a valid ID.");
            return;
        }
        if (borrowerRegistry.getBorrowerById(id) != null) {
            System.out.println("[Error] A borrower with this ID already exists. Please use a unique ID.");
            return;
        }
        System.out.print("Contact Info: "); String contact = scanner.nextLine().trim();
        if (contact.isEmpty()) {
            System.out.println("[Error] Contact info cannot be empty. Please enter valid contact information.");
            return;
        }
        Borrower borrower = new Borrower(name, id, contact);
        borrowerRegistry.addBorrower(borrower);
        System.out.println("Borrower added.");
    }

    private static void listBorrowersMenu() {
        List<Borrower> borrowers = borrowerRegistry.listAllBorrowers();
        if (borrowers.isEmpty()) System.out.println("No borrowers registered.");
        else borrowers.forEach(System.out::println);
    }

    private static void borrowBookMenu() {
        System.out.print("Borrower ID: "); String borrowerId = scanner.nextLine().trim();
        if (borrowerId.isEmpty()) {
            System.out.println("[Error] Borrower ID cannot be empty. Please enter a valid ID.");
            return;
        }
        System.out.print("Book ISBN: "); String isbn = scanner.nextLine().trim();
        if (isbn.isEmpty()) {
            System.out.println("[Error] Book ISBN cannot be empty. Please enter a valid ISBN.");
            return;
        }
        Borrower borrower = borrowerRegistry.getBorrowerById(borrowerId);
        Book book = bookInventory.getBookByIsbn(isbn);
        if (borrower == null) {
            System.out.println("[Error] Borrower not found. Please check the ID and try again.");
            return;
        }
        if (book == null) {
            System.out.println("[Error] Book not found. Please check the ISBN and try again.");
            return;
        }
        if (borrower.getBorrowedBooks().contains(isbn)) {
            System.out.println("[Error] Borrower already has this book on loan.");
            return;
        }
        borrower.addBorrowedBook(isbn);
        Transaction t = new Transaction(isbn, borrowerId, java.time.LocalDate.now(), null, "BORROWED");
        lendingTracker.borrowBook(t);
        System.out.println("Book borrowed.");
    }

    private static void returnBookMenu() {
        System.out.print("Enter your Borrower ID: ");
        String borrowerId = scanner.nextLine().trim();
        Borrower borrower = borrowerRegistry.getBorrowerById(borrowerId);
        if (borrower == null) {
            System.out.println("[Error] Borrower not found. Please check the ID and try again.");
            return;
        }
        List<String> borrowedBooks = borrower.getBorrowedBooks();
        if (borrowedBooks.isEmpty()) {
            System.out.println("You have no books to return.");
            return;
        }
        System.out.println("Books you have borrowed:");
        for (String isbn : borrowedBooks) {
            Book book = bookInventory.getBookByIsbn(isbn);
            System.out.println("- " + (book != null ? book : isbn));
        }
        System.out.print("Enter ISBN of the book to return: ");
        String isbnToReturn = scanner.nextLine().trim();
        if (!borrowedBooks.contains(isbnToReturn)) {
            System.out.println("[Error] You have not borrowed this book.");
            return;
        }
        // Find the corresponding transaction (most recent BORROWED or OVERDUE for this book and borrower)
        Transaction toReturn = null;
        List<Transaction> allTx = lendingTracker.getAllTransactions();
        for (int i = allTx.size() - 1; i >= 0; i--) {
            Transaction t = allTx.get(i);
            if (t.getBookIsbn().equals(isbnToReturn) && t.getBorrowerId().equals(borrowerId)
                && (t.getStatus().equals("BORROWED") || t.getStatus().equals("OVERDUE"))) {
                toReturn = t;
                break;
            }
        }
        if (toReturn == null) {
            System.out.println("[Error] No active transaction found for this book.");
            return;
        }
        toReturn.setStatus("RETURNED");
        toReturn.setReturnDate(java.time.LocalDate.now());
        borrower.removeBorrowedBook(isbnToReturn);
        System.out.println("Book returned: " + (bookInventory.getBookByIsbn(isbnToReturn) != null ? bookInventory.getBookByIsbn(isbnToReturn) : isbnToReturn));
    }
}
