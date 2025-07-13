package library.report;

import library.model.Book;
import library.model.Borrower;
import library.model.Transaction;
import java.util.*;

public class ReportGenerator {
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

    public static List<Borrower> topFines(List<Borrower> borrowers) {
        List<Borrower> sorted = new ArrayList<>(borrowers);
        sorted.sort((a, b) -> Double.compare(b.getFinesOwed(), a.getFinesOwed()));
        return sorted;
    }

    public static Map<String, Integer> inventoryByCategory(List<Book> books) {
        Map<String, Integer> map = new HashMap<>();
        for (Book b : books) {
            map.put(b.getCategory(), map.getOrDefault(b.getCategory(), 0) + 1);
        }
        return map;
    }
}
