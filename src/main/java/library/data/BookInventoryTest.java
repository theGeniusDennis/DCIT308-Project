package library.data;

import library.model.Book;
import java.util.*;

public class BookInventoryTest {
    public static void main(String[] args) {
        BookInventory inventory = new BookInventory();
        Book b1 = new Book("Java Basics", "Alice", "111", "Programming", 2020, "TechPub", "A1");
        Book b2 = new Book("Python 101", "Bob", "222", "Programming", 2021, "CodeHouse", "A2");
        Book b3 = new Book("History of Ashaiman", "C. Mensah", "333", "History", 2018, "LocalPub", "B1");

        // Add books
        inventory.addBook(b1);
        inventory.addBook(b2);
        inventory.addBook(b3);

        // List all books
        System.out.println("All books:");
        for (Book b : inventory.listAllBooks()) System.out.println(b);

        // Get by ISBN
        System.out.println("\nGet by ISBN 222:");
        System.out.println(inventory.getBookByIsbn("222"));

        // Get by category
        System.out.println("\nBooks in Programming:");
        for (Book b : inventory.getBooksByCategory("Programming")) System.out.println(b);

        // Remove a book
        System.out.println("\nRemove ISBN 111:");
        Book removed = inventory.removeBook("111");
        System.out.println("Removed: " + removed);

        // List all books after removal
        System.out.println("\nAll books after removal:");
        for (Book b : inventory.listAllBooks()) System.out.println(b);
    }
}
