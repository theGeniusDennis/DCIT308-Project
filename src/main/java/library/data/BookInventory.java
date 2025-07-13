package library.data;

import library.model.Book;
import java.util.*;

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
}
