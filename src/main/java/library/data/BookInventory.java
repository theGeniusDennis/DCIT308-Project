package library.data;

import library.model.Book;
import java.util.*;

import library.util.FileManager;

public class BookInventory {
    private Map<String, Book> booksByIsbn = new HashMap<>();
    private Map<String, List<Book>> booksByCategory = new HashMap<>();

    public void addBook(Book book) {
        booksByIsbn.put(book.getIsbn(), book);
        booksByCategory.computeIfAbsent(book.getCategory(), k -> new ArrayList<>()).add(book);
    }

    public Book removeBook(String isbn) {
        Book removed = booksByIsbn.remove(isbn);
        if (removed != null) {
            List<Book> catList = booksByCategory.get(removed.getCategory());
            if (catList != null) catList.remove(removed);
        }
        return removed;
    }

    public Book getBookByIsbn(String isbn) {
        return booksByIsbn.get(isbn);
    }

    public List<Book> listAllBooks() {
        return new ArrayList<>(booksByIsbn.values());
    }

    public List<Book> getBooksByCategory(String category) {
        return booksByCategory.getOrDefault(category, Collections.emptyList());
    }

    // --- File Persistence ---
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
