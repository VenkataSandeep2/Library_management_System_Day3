import java.util.ArrayList;
import java.util.Scanner;

class Book {
    private int id;
    private String title;
    private String author;
    private boolean isIssued;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isIssued = false;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isIssued() { return isIssued; }

    public void setIssued(boolean issued) {
        isIssued = issued;
    }

    @Override
    public String toString() {
        return "Book ID: " + id + ", Title: " + title + ", Author: " + author +
               ", Status: " + (isIssued ? "Issued" : "Available");
    }
}

class User {
    private int id;
    private String name;
    private ArrayList<Book> borrowedBooks = new ArrayList<>();

    // Constructor
    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    @Override
    public String toString() {
        return "User ID: " + id + ", Name: " + name + ", Borrowed Books: " + borrowedBooks.size();
    }
}

class Library {
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
        System.out.println("✅ Book added: " + book.getTitle());
    }

    public void addUser(User user) {
        users.add(user);
        System.out.println("✅ User added: " + user.getName());
    }

    public void viewBooks() {
        if (books.isEmpty()) {
            System.out.println("⚠ No books in library.");
        } else {
            System.out.println("\n--- Library Books ---");
            for (Book b : books) {
                System.out.println(b);
            }
        }
    }

    public void viewUsers() {
        if (users.isEmpty()) {
            System.out.println("⚠ No users registered.");
        } else {
            System.out.println("\n--- Library Users ---");
            for (User u : users) {
                System.out.println(u);
            }
        }
    }

    public void issueBook(int bookId, int userId) {
        Book book = findBookById(bookId);
        User user = findUserById(userId);

        if (book == null) {
            System.out.println("⚠ Book not found!");
            return;
        }
        if (user == null) {
            System.out.println("⚠ User not found!");
            return;
        }
        if (book.isIssued()) {
            System.out.println("⚠ Book already issued!");
            return;
        }

        book.setIssued(true);
        user.borrowBook(book);
        System.out.println("✅ Book issued to " + user.getName());
    }

    public void returnBook(int bookId, int userId) {
        Book book = findBookById(bookId);
        User user = findUserById(userId);

        if (book == null || user == null) {
            System.out.println("⚠ Invalid book or user ID.");
            return;
        }
        if (!book.isIssued()) {
            System.out.println("⚠ Book is not issued.");
            return;
        }

        book.setIssued(false);
        user.returnBook(book);
        System.out.println("✅ Book returned by " + user.getName());
    }
    private Book findBookById(int id) {
        for (Book b : books) {
            if (b.getId() == id) return b;
        }
        return null;
    }

    private User findUserById(int id) {
        for (User u : users) {
            if (u.getId() == id) return u;
        }
        return null;
    }
}

class Day3
{
    private static Scanner sc = new Scanner(System.in);
    private static Library library = new Library();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("\n=== Library Management System ===");
            System.out.println("1. Add Book");
            System.out.println("2. Add User");
            System.out.println("3. View Books");
            System.out.println("4. View Users");
            System.out.println("5. Issue Book");
            System.out.println("6. Return Book");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1: addBook(); break;
                case 2: addUser(); break;
                case 3: library.viewBooks(); break;
                case 4: library.viewUsers(); break;
                case 5: issueBook(); break;
                case 6: returnBook(); break;
                case 7: running = false; System.out.println("👋 Exiting... Goodbye!"); break;
                default: System.out.println("⚠ Invalid choice! Try again.");
            }
        }
    }

    private static void addBook() {
        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Book Title: ");
        String title = sc.nextLine();
        System.out.print("Enter Book Author: ");
        String author = sc.nextLine();

        library.addBook(new Book(id, title, author));
    }

    private static void addUser() {
        System.out.print("Enter User ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter User Name: ");
        String name = sc.nextLine();

        library.addUser(new User(id, name));
    }

    private static void issueBook() {
        System.out.print("Enter Book ID to issue: ");
        int bookId = sc.nextInt();
        System.out.print("Enter User ID: ");
        int userId = sc.nextInt();

        library.issueBook(bookId, userId);
    }

    private static void returnBook() {
        System.out.print("Enter Book ID to return: ");
        int bookId = sc.nextInt();
        System.out.print("Enter User ID: ");
        int userId = sc.nextInt();

        library.returnBook(bookId, userId);
    }
}
