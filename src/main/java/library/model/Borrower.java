package library.model;

import java.util.ArrayList;
import java.util.List;

public class Borrower {
    private String name;
    private String idNumber;
    private String contactInfo;
    private double finesOwed;
    private List<String> borrowedBooks; // List of borrowed book ISBNs

    public Borrower(String name, String idNumber, String contactInfo) {
        this.name = name;
        this.idNumber = idNumber;
        this.contactInfo = contactInfo;
        this.finesOwed = 0.0;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getIdNumber() { return idNumber; }
    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }
    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
    public double getFinesOwed() { return finesOwed; }
    public void setFinesOwed(double finesOwed) { this.finesOwed = finesOwed; }
    public List<String> getBorrowedBooks() { return borrowedBooks; }
    public void addBorrowedBook(String isbn) { borrowedBooks.add(isbn); }
    public void removeBorrowedBook(String isbn) { borrowedBooks.remove(isbn); }

    @Override
    public String toString() {
        return String.format("%s (ID: %s), Contact: %s, Fines: %.2f, Borrowed: %s", name, idNumber, contactInfo, finesOwed, borrowedBooks);
    }
}
