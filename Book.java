/**
 * Book class represents an individual book in the library.
 * Demonstrates OOP Concept: Encapsulation (private variables with public getters/setters).
 */
public class Book {
    private int id;
    private String title;
    private String author;
    private boolean isIssued;

    // Constructor to initialize book properties
    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isIssued = false; // Initially, all books are available in the library
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void setIssued(boolean isIssued) {
        this.isIssued = isIssued;
    }

    // Method to display a single book row in table format
    public void displayBook() {
        String status = isIssued ? "Issued" : "Available";
        System.out.printf("| %-5d | %-32s | %-22s | %-10s |\n", id, title, author, status);
    }
}
