package library.data;

import library.model.Borrower;
import java.util.*;
import library.util.FileManager;

/**
 * BorrowerRegistry manages all borrowers in the library system.
 * Provides fast lookup by ID and supports file-based persistence.
 */
public class BorrowerRegistry {
    // Maps borrower ID to Borrower object for fast lookup
    private Map<String, Borrower> borrowersById = new HashMap<>();

    /**
     * Adds a borrower to the registry.
     * @param borrower The borrower to add
     */
    public void addBorrower(Borrower borrower) {
        borrowersById.put(borrower.getIdNumber(), borrower);
    }

    /**
     * Retrieves a borrower by their ID.
     * @param id The ID to search for
     * @return The Borrower if found, else null
     */
    public Borrower getBorrowerById(String id) {
        return borrowersById.get(id);
    }

    /**
     * Removes a borrower by ID.
     * @param id The ID of the borrower to remove
     * @return true if removed, false if not found
     */
    public boolean removeBorrower(String id) {
        return borrowersById.remove(id) != null;
    }

    /**
     * Lists all borrowers in the registry.
     * @return List of all borrowers
     */
    public List<Borrower> listAllBorrowers() {
        return new ArrayList<>(borrowersById.values());
    }

    /**
     * Recursively searches for a borrower by a list of IDs (demonstration of recursion).
     * @param ids List of IDs to search
     * @param index Current index in the list
     * @return The Borrower if found, else null
     */
    public Borrower recursiveSearch(List<String> ids, int index) {
        if (index >= ids.size()) return null;
        Borrower b = borrowersById.get(ids.get(index));
        return (b != null) ? b : recursiveSearch(ids, index + 1);
    }

    // --- File Persistence ---

    /**
     * Saves all borrowers to a file, one per line (pipe-delimited, with borrowed books comma-separated).
     * @param filename The file to write to
     * @throws Exception if file writing fails
     */
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

    /**
     * Loads all borrowers from a file, clearing existing data first.
     * Each line: name|id|contact|fines|isbn1,isbn2,...
     * @param filename The file to read from
     * @throws Exception if file reading or parsing fails
     */
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
