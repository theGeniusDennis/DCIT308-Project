package library.report;

import library.model.Book;
import library.model.Borrower;
import library.model.Transaction;
import java.util.*;

/**
 * ReportGenerator provides static methods for generating library reports.
 * Includes most borrowed books, top fines, and inventory by category.
 */
public class ReportGenerator {
    /**
     * Returns a list of the most borrowed books for a given month and year, sorted by borrow count.
     * @param transactions List of all transactions
     * @param books List of all books
     * @param month Month to filter (1-12)
     * @param year Year to filter
     * @return List of most borrowed books (most frequent first)
     */
    public static List<Book> mostBorrowedBooks(List<Transaction> transactions, List<Book> books, int month, int year) {
        Map<String, Integer> borrowCount = new HashMap<>();
        for (Transaction t : transactions) {
            if (t.getBorrowDate().getMonthValue() == month && t.getBorrowDate().getYear() == year) {
                borrowCount.put(t.getBookIsbn(), borrowCount.getOrDefault(t.getBookIsbn(), 0) + 1);
            }
        }
        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(borrowCount.entrySet());
        sorted.sort((a, b) -> b.getValue() - a.getValue());
        List<Book> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : sorted) {
            for (Book b : books) {
                if (b.getIsbn().equals(entry.getKey())) {
                    result.add(b);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Returns a list of borrowers sorted by fines owed (descending).
     * @param borrowers List of all borrowers
     * @return List of borrowers with highest fines first
     */
    public static List<Borrower> topFines(List<Borrower> borrowers) {
        List<Borrower> sorted = new ArrayList<>(borrowers);
        sorted.sort((a, b) -> Double.compare(b.getFinesOwed(), a.getFinesOwed()));
        return sorted;
    }

    /**
     * Returns a map of book categories to inventory count.
     * @param books List of all books
     * @return Map of category name to number of books
     */
    public static Map<String, Integer> inventoryByCategory(List<Book> books) {
        Map<String, Integer> map = new HashMap<>();
        for (Book b : books) {
            map.put(b.getCategory(), map.getOrDefault(b.getCategory(), 0) + 1);
        }
        return map;
    }

    /**
     * Returns a list of the most active borrowers (by borrow count) for a given month and year.
     * @param transactions List of all transactions
     * @param borrowers List of all borrowers
     * @param month Month to filter (1-12), or 0 for all months
     * @param year Year to filter, or 0 for all years
     * @return List of borrowers sorted by borrow count (most active first)
     */
    public static List<Borrower> mostActiveBorrowers(List<Transaction> transactions, List<Borrower> borrowers, int month, int year) {
        Map<String, Integer> borrowCount = new HashMap<>();
        for (Transaction t : transactions) {
            boolean match = true;
            if (month > 0 && t.getBorrowDate().getMonthValue() != month) match = false;
            if (year > 0 && t.getBorrowDate().getYear() != year) match = false;
            if (match) {
                borrowCount.put(t.getBorrowerId(), borrowCount.getOrDefault(t.getBorrowerId(), 0) + 1);
            }
        }
        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(borrowCount.entrySet());
        sorted.sort((a, b) -> b.getValue() - a.getValue());
        List<Borrower> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : sorted) {
            for (Borrower b : borrowers) {
                if (b.getIdNumber().equals(entry.getKey())) {
                    result.add(b);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Returns a map of year-month (YYYY-MM) to number of books borrowed in that month.
     * @param transactions List of all transactions
     * @return Map of YYYY-MM string to borrow count
     */
    public static Map<String, Integer> borrowingTrends(List<Transaction> transactions) {
        Map<String, Integer> trends = new TreeMap<>();
        for (Transaction t : transactions) {
            String key = t.getBorrowDate().getYear() + "-" + String.format("%02d", t.getBorrowDate().getMonthValue());
            trends.put(key, trends.getOrDefault(key, 0) + 1);
        }
        return trends;
    }

    /**
     * Returns a list of books that have never been borrowed.
     * @param books List of all books
     * @param transactions List of all transactions
     * @return List of books never borrowed
     */
    public static List<Book> booksNeverBorrowed(List<Book> books, List<Transaction> transactions) {
        Set<String> borrowedIsbns = new HashSet<>();
        for (Transaction t : transactions) {
            borrowedIsbns.add(t.getBookIsbn());
        }
        List<Book> neverBorrowed = new ArrayList<>();
        for (Book b : books) {
            if (!borrowedIsbns.contains(b.getIsbn())) {
                neverBorrowed.add(b);
            }
        }
        return neverBorrowed;
    }
}
