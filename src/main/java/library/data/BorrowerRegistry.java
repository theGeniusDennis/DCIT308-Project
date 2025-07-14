package library.data;

import library.model.Borrower;
import java.util.*;

import library.util.FileManager;

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

    // --- File Persistence ---
    public void saveToFile(String filename) throws Exception {
        List<String> lines = new ArrayList<>();
        for (Borrower b : listAllBorrowers()) {
            // name|id|contact|fines|isbn1,isbn2,...
            String borrowed = String.join(",", b.getBorrowedBooks());
            lines.add(String.join("|",
                b.getName(),
                b.getIdNumber(),
                b.getContactInfo(),
                String.valueOf(b.getFinesOwed()),
                borrowed
            ));
        }
        FileManager.writeLines(filename, lines);
    }

    public void loadFromFile(String filename) throws Exception {
        borrowersById.clear();
        List<String> lines = FileManager.readLines(filename);
        for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts.length >= 4) {
                Borrower b = new Borrower(parts[0], parts[1], parts[2]);
                b.setFinesOwed(Double.parseDouble(parts[3]));
                if (parts.length == 5 && !parts[4].isEmpty()) {
                    String[] borrowed = parts[4].split(",");
                    for (String isbn : borrowed) b.addBorrowedBook(isbn);
                }
                addBorrower(b);
            }
        }
    }
}
