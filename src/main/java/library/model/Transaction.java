package library.model;

import java.time.LocalDate;

public class Transaction {
    private String bookIsbn;
    private String borrowerId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private String status; // e.g., "BORROWED", "RETURNED", "OVERDUE"

    public Transaction(String bookIsbn, String borrowerId, LocalDate borrowDate, LocalDate returnDate, String status) {
        this.bookIsbn = bookIsbn;
        this.borrowerId = borrowerId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public String getBookIsbn() { return bookIsbn; }
    public void setBookIsbn(String bookIsbn) { this.bookIsbn = bookIsbn; }
    public String getBorrowerId() { return borrowerId; }
    public void setBorrowerId(String borrowerId) { this.borrowerId = borrowerId; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("Transaction: Book ISBN=%s, Borrower ID=%s, Borrowed=%s, Return=%s, Status=%s",
                bookIsbn, borrowerId, borrowDate, returnDate, status);
    }
}
