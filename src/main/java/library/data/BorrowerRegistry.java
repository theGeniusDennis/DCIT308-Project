package library.data;

import library.model.Borrower;
import java.util.*;

public class BorrowerRegistry {
    private Map<String, Borrower> borrowersById = new HashMap<>();

    public void addBorrower(Borrower borrower) {
        borrowersById.put(borrower.getIdNumber(), borrower);
    }

    public Borrower getBorrowerById(String id) {
        return borrowersById.get(id);
    }

    public boolean removeBorrower(String id) {
        return borrowersById.remove(id) != null;
    }

    public List<Borrower> listAllBorrowers() {
        return new ArrayList<>(borrowersById.values());
    }

    // Recursive search by ID (for demonstration)
    public Borrower recursiveSearch(List<String> ids, int index) {
        if (index >= ids.size()) return null;
        Borrower b = borrowersById.get(ids.get(index));
        return (b != null) ? b : recursiveSearch(ids, index + 1);
    }
}
