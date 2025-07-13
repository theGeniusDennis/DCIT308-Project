package library;

import library.data.BookInventory;
import library.data.BorrowerRegistry;
import library.data.LendingTracker;
import library.model.Book;
import library.model.Borrower;
import library.model.Transaction;
import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final BookInventory bookInventory = new BookInventory();
    private static final BorrowerRegistry borrowerRegistry = new BorrowerRegistry();
    private static final LendingTracker lendingTracker = new LendingTracker();

    public static void main(String[] args) {
        System.out.println("Welcome to the Ebenezer Community Library System!");
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1": addBookMenu(); break;
                case "2": removeBookMenu(); break;
                case "3": listBooksMenu(); break;
                case "4": addBorrowerMenu(); break;
                case "5": listBorrowersMenu(); break;
                case "6": borrowBookMenu(); break;
                case "7": returnBookMenu(); break;
                case "0": running = false; break;
                default: System.out.println("Invalid option. Try again.");
            }
        }
        System.out.println("Goodbye!");
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
        System.out.println("0. Exit");
        System.out.print("Select option: ");
    }

    private static void addBookMenu() {
        System.out.print("Title: "); String title = scanner.nextLine();
        System.out.print("Author: "); String author = scanner.nextLine();
        System.out.print("ISBN: "); String isbn = scanner.nextLine();
        System.out.print("Category: "); String category = scanner.nextLine();
        System.out.print("Year: "); int year = Integer.parseInt(scanner.nextLine());
        System.out.print("Publisher: "); String publisher = scanner.nextLine();
        System.out.print("Shelf Location: "); String shelf = scanner.nextLine();
        Book book = new Book(title, author, isbn, category, year, publisher, shelf);
        bookInventory.addBook(book);
        System.out.println("Book added.");
    }

    private static void removeBookMenu() {
        System.out.print("Enter ISBN to remove: ");
        String isbn = scanner.nextLine();
        Book removed = bookInventory.removeBook(isbn);
        if (removed != null) System.out.println("Book removed: " + removed);
        else System.out.println("Book not found.");
    }

    private static void listBooksMenu() {
        List<Book> books = bookInventory.listAllBooks();
        if (books.isEmpty()) System.out.println("No books in inventory.");
        else books.forEach(System.out::println);
    }

    private static void addBorrowerMenu() {
        System.out.print("Name: "); String name = scanner.nextLine();
        System.out.print("ID Number: "); String id = scanner.nextLine();
        System.out.print("Contact Info: "); String contact = scanner.nextLine();
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
        System.out.print("Borrower ID: "); String borrowerId = scanner.nextLine();
        System.out.print("Book ISBN: "); String isbn = scanner.nextLine();
        Borrower borrower = borrowerRegistry.getBorrowerById(borrowerId);
        Book book = bookInventory.getBookByIsbn(isbn);
        if (borrower == null) { System.out.println("Borrower not found."); return; }
        if (book == null) { System.out.println("Book not found."); return; }
        borrower.addBorrowedBook(isbn);
        Transaction t = new Transaction(isbn, borrowerId, java.time.LocalDate.now(), null, "BORROWED");
        lendingTracker.borrowBook(t);
        System.out.println("Book borrowed.");
    }

    private static void returnBookMenu() {
        Transaction t = lendingTracker.processNextReturn();
        if (t != null) {
            Borrower borrower = borrowerRegistry.getBorrowerById(t.getBorrowerId());
            if (borrower != null) borrower.removeBorrowedBook(t.getBookIsbn());
            t.setReturnDate(java.time.LocalDate.now());
            System.out.println("Book returned: " + t);
        } else {
            System.out.println("No books to return.");
        }
    }
}
