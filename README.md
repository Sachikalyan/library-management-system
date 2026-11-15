# 📚 Simple Library Management System (Core Java)

A beginner-friendly, clean, and interactive console-based Library Management System designed specifically for **1st-year computer science/engineering students**.

---

## 🎯 Features
1. **View All Books:** Displays all available and issued books in a clean, formatted table.
2. **Add New Book:** Allows the librarian/user to add new books with an ID, Title, and Author.
3. **Search for Books:** Search any book instantly by ID, Title keyword, or Author name.
4. **Issue Book:** Mark a book as issued (checks if it's already taken).
5. **Return Book:** Mark an issued book as returned to the library.
6. **Pre-loaded Sample Data:** Includes 4 popular programming books out of the box so you can test it immediately without typing new data first.

---

## 🧠 OOP & Java Concepts Used (Great for Viva / Oral Exams)

| Concept | Where it is used in the project |
| :--- | :--- |
| **Classes & Objects** | `Book`, `Library`, and `Main` classes. Instances of `Book` represent physical books. |
| **Encapsulation** | Fields in `Book` (`id`, `title`, `author`, `isIssued`) are `private`. They are accessed via public getters and setters. |
| **Collections (`ArrayList`)** | `ArrayList<Book>` dynamically manages the list of books without fixed array size limits. |
| **Loops & Conditionals** | `while` loop for keeping the menu alive, `switch-case` for navigation, and `for-each` loops for iterating over books. |
| **Input Handling & Validation** | `Scanner` is used with `try-catch` blocks to catch non-numeric ID errors (`NumberFormatException`). |

---

## 📁 Project Structure
```text
LibraryManagementSystem/
│
├── Book.java       # Represents a Book entity (data & getters/setters)
├── Library.java    # Manages the list of books & operations (add, view, issue, return, search)
├── Main.java       # User interface (Menu-driven console using Scanner)
└── README.md       # Documentation and running guide
```

---

## 🚀 How to Run the Project

### Option A: Using Command Prompt / PowerShell / Terminal
1. Open your terminal and navigate to the project directory:
   ```cmd
   cd "C:\Users\sachi\.gemini\antigravity\scratch\LibraryManagementSystem"
   ```
2. Compile all Java files:
   ```cmd
   javac *.java
   ```
3. Run the application:
   ```cmd
   java Main
   ```

### Option B: Using an IDE (VS Code, IntelliJ IDEA, Eclipse, NetBeans)
1. Open the `LibraryManagementSystem` folder in your IDE.
2. Open `Main.java`.
3. Click the **Run** or **Play ▶️** button.

---

## 🖥️ Sample Output
```text
============================================================
        📚 CAMPUS LIBRARY MANAGEMENT SYSTEM 📚            
============================================================

----------------------- MAIN MENU -----------------------
 1. View All Books
 2. Add New Book
 3. Search for a Book
 4. Issue a Book
 5. Return a Book
 6. Exit
👉 Enter your choice (1-6): 1

--- Current Books in Library ---
+-------+----------------------------------+------------------------+------------+
| ID    | Title                            | Author                 | Status     |
+-------+----------------------------------+------------------------+------------+
| 101   | Java: The Complete Reference     | Herbert Schildt        | Available  |
| 102   | Head First Java                  | Kathy Sierra           | Available  |
| 103   | Clean Code                       | Robert C. Martin       | Available  |
| 104   | Data Structures and Algorithms   | Mark Allen Weiss       | Available  |
+-------+----------------------------------+------------------------+------------+
```
