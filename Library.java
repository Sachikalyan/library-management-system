import java.util.ArrayList;

/**
 * Library class handles all library operations.
 * Demonstrates: ArrayList collection, searching, and business logic.
 */
public class Library {
    private ArrayList<Book> books;

    // Constructor initializes the list and pre-populates default books for easy testing
    public Library() {
        books = new ArrayList<>();
        addDefaultBooks();
    }

    // Pre-populate with sample books
    private void addDefaultBooks() {
        books.add(new Book(101, "Java: The Complete Reference", "Herbert Schildt"));
        books.add(new Book(102, "Head First Java", "Kathy Sierra"));
        books.add(new Book(103, "Clean Code", "Robert C. Martin"));
        books.add(new Book(104, "Data Structures and Algorithms", "Mark Allen Weiss"));
    }

    // 1. Add a new book
    public void addBook(Book newBook) {
        // Prevent duplicate IDs
        for (Book b : books) {
            if (b.getId() == newBook.getId()) {
                System.out.println("❌ Error: A book with ID " + newBook.getId() + " already exists!");
                return;
            }
        }
        books.add(newBook);
        System.out.println("✅ Book added successfully: " + newBook.getTitle());
    }

    // 2. View all books
    public void viewAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available in the library.");
            return;
        }

        printTableDivider();
        System.out.printf("| %-5s | %-32s | %-22s | %-10s |\n", "ID", "Title", "Author", "Status");
        printTableDivider();

        for (Book book : books) {
            book.displayBook();
        }
        printTableDivider();
    }

    // 3. Search book by Title, Author, or ID
    public void searchBook(String query) {
        boolean found = false;
        String lowerQuery = query.toLowerCase();

        printTableDivider();
        System.out.printf("| %-5s | %-32s | %-22s | %-10s |\n", "ID", "Title", "Author", "Status");
        printTableDivider();

        for (Book book : books) {
            if (String.valueOf(book.getId()).equals(query) ||
                book.getTitle().toLowerCase().contains(lowerQuery) ||
                book.getAuthor().toLowerCase().contains(lowerQuery)) {
                book.displayBook();
                found = true;
            }
        }
        printTableDivider();

        if (!found) {
            System.out.println("⚠️ No matching books found for search query: \"" + query + "\"");
        }
    }

    // 4. Issue a book
    public void issueBook(int bookId) {
        Book book = findBookById(bookId);

        if (book == null) {
            System.out.println("❌ Book with ID " + bookId + " does not exist.");
            return;
        }

        if (book.isIssued()) {
            System.out.println("⚠️ Sorry! The book '" + book.getTitle() + "' is already issued.");
        } else {
            book.setIssued(true);
            System.out.println("✅ Success! Book '" + book.getTitle() + "' has been issued.");
        }
    }

    // 5. Return a book
    public void returnBook(int bookId) {
        Book book = findBookById(bookId);

        if (book == null) {
            System.out.println("❌ Book with ID " + bookId + " does not exist.");
            return;
        }

        if (!book.isIssued()) {
            System.out.println("⚠️ This book was never issued. It is already available in the library.");
        } else {
            book.setIssued(false);
            System.out.println("✅ Success! Book '" + book.getTitle() + "' has been returned.");
        }
    }

    // Helper method to look up a book by ID
    private Book findBookById(int id) {
        for (Book b : books) {
            if (b.getId() == id) {
                return b;
            }
        }
        return null;
    }

    private void printTableDivider() {
        System.out.println("+-------+----------------------------------+------------------------+------------+");
    }
}
