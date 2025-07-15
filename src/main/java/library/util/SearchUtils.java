package library.util;

import library.model.Book;
import java.util.List;

/**
 * SearchUtils provides static search algorithms for books.
 * Includes linear search (by title) and binary search (by ISBN).
 */
public class SearchUtils {
    /**
     * Performs a linear search for a book by title (case-insensitive).
     * @param books List of books to search
     * @param title Title to match
     * @return The Book if found, else null
     */
    public static Book linearSearchByTitle(List<Book> books, String title) {
        for (Book b : books) {
            if (b.getTitle().equalsIgnoreCase(title)) return b;
        }
        return null;
    }

    /**
     * Performs a binary search for a book by ISBN (assumes list is sorted by ISBN).
     * @param books List of books (sorted by ISBN)
     * @param isbn ISBN to search for
     * @return The Book if found, else null
     */
    public static Book binarySearchByIsbn(List<Book> books, String isbn) {
        int left = 0, right = books.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int cmp = books.get(mid).getIsbn().compareTo(isbn);
            if (cmp == 0) return books.get(mid);
            if (cmp < 0) left = mid + 1;
            else right = mid - 1;
        }
        return null;
    }
}
