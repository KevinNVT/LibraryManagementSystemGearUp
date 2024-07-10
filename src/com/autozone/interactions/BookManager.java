package com.autozone.interactions;

import com.autozone.dao.BookDAO;
import com.autozone.models.Book;
import java.util.List;
import java.util.Scanner;

import com.autozone.dao.BookDAO;
import com.autozone.models.Book;

public class BookManager {

	 private BookDAO bookDAO;
	 
	 public BookManager(BookDAO bookDAO) {
		 this.bookDAO = bookDAO;
	 }

    public static void manageBooks(Scanner scanner, BookDAO bookDAO) {
        boolean running = true;

        while (running) {
            try {
                System.out.println("\nBook Management");
                System.out.println("1. Add Book");
                System.out.println("2. Update Book");
                System.out.println("3. Delete Book");
                System.out.println("4. Find All Books");
                System.out.println("5. Find Book by Title");
                System.out.println("6. Find Book by Author");
                System.out.println("7. Find Book by ISBN");
                System.out.println("8. Return to Main Menu");

                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        addBook(scanner, bookDAO);
                        break;
                    case 2:
                    	updateBook(scanner, bookDAO);
                        break;
                    case 3:
                    	deleteBook(scanner, bookDAO);
                        break;
                    case 4:
                    	findAllBooks(bookDAO);
                        break;
                    case 5:
                    	findBookByTitle(scanner, bookDAO);
                        break;
                    case 6:
                    	findBookByAuthor(scanner, bookDAO);
                        break;
                    case 7:
                    	findBookByIsbn(scanner, bookDAO);
                        break;
                    case 8:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                        break;
                }
            } catch (Exception exception) {
                System.err.println("An unexpected error has occurred");
                exception.printStackTrace();
            }
        }
    }

    private static void addBook(Scanner scanner, BookDAO bookDAO) {
        try {
            System.out.println("\nEnter Book Details:");
            System.out.print("Title: ");
            String title = scanner.nextLine();
            System.out.print("Author: ");
            String author = scanner.nextLine();
            System.out.print("ISBN: ");
            int isbn = scanner.nextInt();

            Book book = new Book(title, author, isbn, true);
            bookDAO.addBook(book);
            System.out.println("Book added successfully.");
        } catch (Exception exception) {
            System.err.println("Failed to add book.");
            exception.printStackTrace();
        }
    }
    
    private static void updateBook(Scanner scanner, BookDAO bookDAO) {
        try {
            System.out.println("\nEnter Book ID to update:");
            System.out.print("ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            
            Book existingBook = bookDAO.findBookById(id);
            if (existingBook == null) {
                System.out.println("Book with ID " + id + " not found.");
                return;
            }
            
            System.out.println("\nCurrent Book Details:");
            System.out.println("Title: " + existingBook.getTitle());
            System.out.println("Author: " + existingBook.getAuthor());
            System.out.println("ISBN: " + existingBook.getIsbn());
            System.out.println("Available: " + existingBook.isAvailable());
            
            System.out.println("\nEnter Updated Book Details (Leave blank to keep current):");
            System.out.print("New Title: ");
            String newTitle = scanner.nextLine();
            if (!newTitle.isBlank()) {
                existingBook.setTitle(newTitle);
            }
            
            System.out.print("New Author: ");
            String newAuthor = scanner.nextLine();
            if (!newAuthor.isBlank()) {
                existingBook.setAuthor(newAuthor);
            }
            
            System.out.print("New ISBN: ");
            int newIsbn = scanner.nextInt();
            // I had to use autoboxing
            Integer newIsbnInteger = newIsbn;
            if (newIsbnInteger != null) {
                existingBook.setIsbn(newIsbn);
            }
            
            System.out.print("New Available (true/false): ");
            String newAvailableStr = scanner.nextLine();
            if (!newAvailableStr.isBlank()) {
                boolean newAvailable = Boolean.parseBoolean(newAvailableStr);
                existingBook.setAvailable(newAvailable);
            }

            bookDAO.updateBook(existingBook);
            System.out.println("Book updated successfully.");
        } catch (Exception exception) {
            System.err.println("Failed to update book.");
            exception.printStackTrace();
        }
    }
    
    private static void deleteBook(Scanner scanner, BookDAO bookDAO) {
        try {
            System.out.println("\nEnter Book ID to Delete:");
            int id = scanner.nextInt();
            scanner.nextLine();

            bookDAO.deleteBook(id);
            System.out.println("Book deleted successfully.");
        } catch (Exception exception) {
            System.err.println("Failed to delete book.");
            exception.printStackTrace();
        }
    }
    
    private static void findAllBooks(BookDAO bookDAO) {
        try {
            List<Book> books = bookDAO.findAll();
            
            if (books.isEmpty()) {
            	System.out.println("No books found");
            } else {
            	System.out.println("--------------------------"); 
                for (Book book : books) {
                	System.out.println("Title: " + book.getTitle());
                	System.out.println("Author: " + book.getAuthor());
                	System.out.println("ISBN: " + book.getIsbn());
                	System.out.println("Available: " + book.isAvailable());
                
                	System.out.println("--------------------------");       
                }
            }
        } catch (Exception exception) {
            System.err.println("Failed to retrieve books.");
            exception.printStackTrace();
        }
    }
    
    private static void findBookByTitle(Scanner scanner, BookDAO bookDAO) {
        try {
            System.out.println("\nEnter Title to Search: ");
            String title = scanner.nextLine();
            List<Book> books = bookDAO.findByTitle(title);
            	
            System.out.println("--------------------------");
            for (Book book : books) {
            	System.out.println("Title: " + book.getTitle());
            	System.out.println("Author: " + book.getAuthor());
            	System.out.println("ISBN: " + book.getIsbn());
            	System.out.println("Avialable: " + book.isAvailable());
            	
            	System.out.println("--------------------------");
            }
        } catch (Exception exception) {
            System.err.println("Failed to retrieve books by title.");
            exception.printStackTrace();
        }
    }

    private static void findBookByAuthor(Scanner scanner, BookDAO bookDAO) {
        try {
            System.out.println("\nEnter Author to Search:");
            String author = scanner.nextLine();
            List<Book> books = bookDAO.findByAuthor(author);
            
            System.out.println("--------------------------");
            for (Book book : books) {
            	System.out.println("Title: " + book.getTitle());
            	System.out.println("Author: " + book.getAuthor());
            	System.out.println("ISBN: " + book.getIsbn());
            	System.out.println("Available: " + book.isAvailable());
            	
            	System.out.println("--------------------------");
            }
        } catch (Exception exception) {
            System.err.println("Failed to retrieve books by author.");
            exception.printStackTrace();
        }
    }

    private static void findBookByIsbn(Scanner scanner, BookDAO bookDAO) {
        try {
            System.out.println("\nEnter ISBN to Search");
            System.out.print("ISBN: ");
            int isbn = scanner.nextInt();
            
            List<Book> books = bookDAO.findByIsbn(isbn);
            
            if (books.isEmpty()) {
                System.out.println("No books found with ISBN: " + isbn);
            } else {
                System.out.println("Books found with ISBN " + isbn + ":");
                for (Book book : books) {
                    System.out.println(book.getId() + " - " + book.getTitle() + " - " + book.getAuthor() + " - " + book.isAvailable());
                }
            }
        } catch (Exception exception) {
            System.err.println("Failed to search for books by ISBN.");
            exception.printStackTrace();
        }
    }
}
