package library.data;

import library.model.Book;
import java.util.*;
import library.util.FileManager;

/**
 * BookInventory manages the collection of books in the library.
 * It provides fast lookup by ISBN and grouping by category using custom data structures.
 * Supports file-based persistence for saving/loading the inventory.
 */
public class BookInventory {
    // Maps ISBN to Book for fast lookup
    private Map<String, Book> booksByIsbn = new HashMap<>();
    // Maps category to list of books for grouping
    private Map<String, List<Book>> booksByCategory = new HashMap<>();

    /**
     * Adds a book to the inventory, updating both ISBN and category maps.
     * @param book The book to add
     */
    public void addBook(Book book) {
        booksByIsbn.put(book.getIsbn(), book);
        booksByCategory.computeIfAbsent(book.getCategory(), k -> new ArrayList<>()).add(book);
    }

    /**
     * Removes a book by ISBN from the inventory and its category list.
     * @param isbn The ISBN of the book to remove
     * @return The removed Book, or null if not found
     */
    public Book removeBook(String isbn) {
        Book removed = booksByIsbn.remove(isbn);
        if (removed != null) {
            List<Book> catList = booksByCategory.get(removed.getCategory());
            if (catList != null) catList.remove(removed);
        }
        return removed;
    }

    /**
     * Retrieves a book by its ISBN.
     * @param isbn The ISBN to search for
     * @return The Book if found, else null
     */
    public Book getBookByIsbn(String isbn) {
        return booksByIsbn.get(isbn);
    }

    /**
     * Lists all books in the inventory.
     * @return List of all books
     */
    public List<Book> listAllBooks() {
        return new ArrayList<>(booksByIsbn.values());
    }

    /**
     * Gets all books in a given category.
     * @param category The category to filter by
     * @return List of books in the category
     */
    public List<Book> getBooksByCategory(String category) {
        return booksByCategory.getOrDefault(category, Collections.emptyList());
    }

    // --- File Persistence ---

    /**
     * Saves the current inventory to a file, one book per line (pipe-delimited).
     * @param filename The file to write to
     * @throws Exception if file writing fails
     */
    public void saveToFile(String filename) throws Exception {
        List<String> lines = new ArrayList<>();
        for (Book b : listAllBooks()) {
            lines.add(String.join("|",
                b.getTitle(),
                b.getAuthor(),
                b.getIsbn(),
                b.getCategory(),
                String.valueOf(b.getYear()),
                b.getPublisher(),
                b.getShelfLocation()
            ));
        }
        FileManager.writeLines(filename, lines);
    }

    /**
     * Loads the inventory from a file, clearing existing data first.
     * Each line must have 7 fields: title|author|isbn|category|year|publisher|shelfLocation
     * @param filename The file to read from
     * @throws Exception if file reading or parsing fails
     */
    public void loadFromFile(String filename) throws Exception {
        booksByIsbn.clear();
        booksByCategory.clear();
        List<String> lines = FileManager.readLines(filename);
        for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts.length == 7) {
                Book b = new Book(parts[0], parts[1], parts[2], parts[3], Integer.parseInt(parts[4]), parts[5], parts[6]);
                addBook(b);
            }
        }
    }
}
