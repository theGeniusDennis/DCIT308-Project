package library.util;

import library.model.Book;
import java.util.List;

public class SearchUtils {
    // Linear search by title
    public static Book linearSearchByTitle(List<Book> books, String title) {
        for (Book b : books) {
            if (b.getTitle().equalsIgnoreCase(title)) return b;
        }
        return null;
    }

    // Binary search by ISBN (assumes sorted by ISBN)
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
