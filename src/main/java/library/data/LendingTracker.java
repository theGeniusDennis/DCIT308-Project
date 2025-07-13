package library.data;

import library.model.Transaction;
import java.util.*;

public class LendingTracker {
    private Queue<Transaction> lendingQueue = new LinkedList<>();
    private Stack<Transaction> returnStack = new Stack<>();
    private List<Transaction> allTransactions = new ArrayList<>();

    public void borrowBook(Transaction transaction) {
        lendingQueue.add(transaction);
        allTransactions.add(transaction);
    }

    public Transaction processNextReturn() {
        if (!lendingQueue.isEmpty()) {
            Transaction t = lendingQueue.poll();
            t.setStatus("RETURNED");
            returnStack.push(t);
            return t;
        }
        return null;
    }

    public List<Transaction> getAllTransactions() {
        return allTransactions;
    }
}
