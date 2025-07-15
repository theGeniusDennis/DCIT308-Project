package library.data;

import library.model.Transaction;
import java.util.*;
import library.util.FileManager;
import java.time.LocalDate;

/**
 * LendingTracker manages all lending and return transactions in the library.
 * Uses a queue for lending order, a stack for returns, and a list for all transactions.
 * Handles overdue logic and fine calculation.
 */
public class LendingTracker {
    // Queue for lending order (FIFO)
    private Queue<Transaction> lendingQueue = new LinkedList<>();
    // Stack for return order (LIFO, if needed)
    private Stack<Transaction> returnStack = new Stack<>();
    // List of all transactions (history)
    private List<Transaction> allTransactions = new ArrayList<>();
    // Overdue/fine policy
    private static final int OVERDUE_DAYS = 14;
    private static final double FINE_PER_DAY = 1.0;

    /**
     * Returns a list of overdue transactions (status BORROWED and overdue).
     * @return List of overdue transactions
     */
    public List<Transaction> getOverdueTransactions() {
        List<Transaction> overdue = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (Transaction t : allTransactions) {
            if ("BORROWED".equals(t.getStatus()) && t.getBorrowDate().plusDays(OVERDUE_DAYS).isBefore(today)) {
                overdue.add(t);
            }
        }
        return overdue;
    }

    /**
     * Updates fines for all overdue borrowers (requires registry).
     * Sets transaction status to OVERDUE and adds fine to borrower.
     * @param registry The BorrowerRegistry to update fines
     */
    public void updateOverdueFines(library.data.BorrowerRegistry registry) {
        LocalDate today = LocalDate.now();
        for (Transaction t : getOverdueTransactions()) {
            long daysOverdue = java.time.temporal.ChronoUnit.DAYS.between(t.getBorrowDate().plusDays(OVERDUE_DAYS), today);
            double fine = daysOverdue * FINE_PER_DAY;
            library.model.Borrower borrower = registry.getBorrowerById(t.getBorrowerId());
            if (borrower != null) {
                borrower.setFinesOwed(borrower.getFinesOwed() + fine);
                t.setStatus("OVERDUE");
            }
        }
    }

    /**
     * Adds a new borrow transaction to the queue and history.
     * @param transaction The borrow transaction
     */
    public void borrowBook(Transaction transaction) {
        lendingQueue.add(transaction);
        allTransactions.add(transaction);
    }

    /**
     * Processes the next return in the queue, marks as RETURNED, and pushes to return stack.
     * @return The returned Transaction, or null if queue is empty
     */
    public Transaction processNextReturn() {
        if (!lendingQueue.isEmpty()) {
            Transaction t = lendingQueue.poll();
            t.setStatus("RETURNED");
            returnStack.push(t);
            return t;
        }
        return null;
    }

    /**
     * Returns the list of all transactions (history).
     * @return List of all transactions
     */
    public List<Transaction> getAllTransactions() {
        return allTransactions;
    }

    // --- File Persistence ---
    public void saveToFile(String filename) throws Exception {
        List<String> lines = new ArrayList<>();
        for (Transaction t : allTransactions) {
            // isbn|borrowerId|borrowDate|returnDate|status
            lines.add(String.join("|",
                t.getBookIsbn(),
                t.getBorrowerId(),
                t.getBorrowDate().toString(),
                t.getReturnDate() == null ? "" : t.getReturnDate().toString(),
                t.getStatus()
            ));
        }
        FileManager.writeLines(filename, lines);
    }

    public void loadFromFile(String filename) throws Exception {
        lendingQueue.clear();
        returnStack.clear();
        allTransactions.clear();
        List<String> lines = FileManager.readLines(filename);
        for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts.length == 5) {
                LocalDate borrowDate = LocalDate.parse(parts[2]);
                LocalDate returnDate = parts[3].isEmpty() ? null : LocalDate.parse(parts[3]);
                Transaction t = new Transaction(parts[0], parts[1], borrowDate, returnDate, parts[4]);
                allTransactions.add(t);
                if (t.getStatus().equals("BORROWED")) lendingQueue.add(t);
                if (t.getStatus().equals("RETURNED")) returnStack.push(t);
            }
        }
    }
}
