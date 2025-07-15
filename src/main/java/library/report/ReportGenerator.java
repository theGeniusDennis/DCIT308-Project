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
}
