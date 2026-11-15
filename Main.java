import java.util.Scanner;

/**
 * Main class provides an easy-to-use menu-driven console application.
 * Perfect for 1st-year demonstrations and practical assignments.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        System.out.println("============================================================");
        System.out.println("        📚 CAMPUS LIBRARY MANAGEMENT SYSTEM 📚            ");
        System.out.println("============================================================");

        boolean running = true;

        while (running) {
            System.out.println("\n----------------------- MAIN MENU -----------------------");
            System.out.println(" 1. View All Books");
            System.out.println(" 2. Add New Book");
            System.out.println(" 3. Search for a Book");
            System.out.println(" 4. Issue a Book");
            System.out.println(" 5. Return a Book");
            System.out.println(" 6. Exit");
            System.out.print("👉 Enter your choice (1-6): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.println("\n--- Current Books in Library ---");
                    library.viewAllBooks();
                    break;

                case "2":
                    System.out.println("\n--- Add a New Book ---");
                    try {
                        System.out.print("Enter Book ID (e.g., 105): ");
                        int id = Integer.parseInt(scanner.nextLine().trim());

                        System.out.print("Enter Book Title: ");
                        String title = scanner.nextLine().trim();

                        System.out.print("Enter Book Author: ");
                        String author = scanner.nextLine().trim();

                        if (title.isEmpty() || author.isEmpty()) {
                            System.out.println("❌ Title and Author cannot be empty!");
                        } else {
                            library.addBook(new Book(id, title, author));
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Invalid ID! Please enter a valid number (e.g., 105).");
                    }
                    break;

                case "3":
                    System.out.println("\n--- Search Book ---");
                    System.out.print("Enter Book ID, Title, or Author keyword: ");
                    String keyword = scanner.nextLine().trim();
                    if (keyword.isEmpty()) {
                        System.out.println("❌ Search keyword cannot be empty!");
                    } else {
                        library.searchBook(keyword);
                    }
                    break;

                case "4":
                    System.out.println("\n--- Issue a Book ---");
                    try {
                        System.out.print("Enter Book ID to issue: ");
                        int id = Integer.parseInt(scanner.nextLine().trim());
                        library.issueBook(id);
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Invalid ID! Please enter a number.");
                    }
                    break;

                case "5":
                    System.out.println("\n--- Return a Book ---");
                    try {
                        System.out.print("Enter Book ID to return: ");
                        int id = Integer.parseInt(scanner.nextLine().trim());
                        library.returnBook(id);
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Invalid ID! Please enter a number.");
                    }
                    break;

                case "6":
                    System.out.println("\nThank you for using the Library Management System. Have a great day!");
                    running = false;
                    break;

                default:
                    System.out.println("❌ Invalid option! Please enter a number between 1 and 6.");
                    break;
            }
        }

        scanner.close();
    }
}
