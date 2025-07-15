package library.util;

import library.model.Book;
import java.util.List;

/**
 * SortUtils provides static sorting algorithms for books.
 * Includes selection sort (by title) and merge sort (by year).
 */
public class SortUtils {
    /**
     * Sorts a list of books in-place by title using selection sort (case-insensitive).
     * @param books List of books to sort
     */
    public static void selectionSortByTitle(List<Book> books) {
        int n = books.size();
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (books.get(j).getTitle().compareToIgnoreCase(books.get(minIdx).getTitle()) < 0) {
                    minIdx = j;
                }
            }
            Book temp = books.get(minIdx);
            books.set(minIdx, books.get(i));
            books.set(i, temp);
        }
    }

    /**
     * Sorts a list of books in-place by year using merge sort.
     * @param books List of books to sort
     */
    public static void mergeSortByYear(List<Book> books) {
        if (books.size() > 1) {
            int mid = books.size() / 2;
            List<Book> left = books.subList(0, mid);
            List<Book> right = books.subList(mid, books.size());
            mergeSortByYear(left);
            mergeSortByYear(right);
            merge(books, left, right);
        }
    }

    /**
     * Helper method to merge two sorted lists of books by year.
     */
    private static void merge(List<Book> books, List<Book> left, List<Book> right) {
        int i = 0, j = 0, k = 0;
        while (i < left.size() && j < right.size()) {
            if (left.get(i).getYear() <= right.get(j).getYear()) {
                books.set(k++, left.get(i++));
            } else {
                books.set(k++, right.get(j++));
            }
        }
        while (i < left.size()) books.set(k++, left.get(i++));
        while (j < right.size()) books.set(k++, right.get(j++));
    }
}
