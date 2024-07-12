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

                // Validates user enters numbers only
                int choice = 0;
                try {
                	choice = scanner.nextInt();
                } catch (Exception exception) {
                	System.err.println("\nUse numbers, please.");
                	exception.printStackTrace();
                }
                
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
                        System.out.println("\nInvalid choice. Please select a valid option.");
                        break;
                }
            } catch (Exception exception) {
                System.err.println("\nAn unexpected error has occurred");
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
            String isbn = scanner.nextLine();

            Book book = new Book(title, author, isbn, true);
            bookDAO.addBook(book);
            System.out.println("\nBook added successfully.");
        } catch (Exception exception) {
            System.err.println("\nFailed to add book.");
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
            String newIsbn = scanner.nextLine();
            if (newIsbn != null) {
                existingBook.setIsbn(newIsbn);
            }
            
            System.out.print("New Available (true/false): ");
            String newAvailableStr = scanner.nextLine();
            if (!newAvailableStr.isBlank()) {
                boolean newAvailable = Boolean.parseBoolean(newAvailableStr);
                existingBook.setAvailable(newAvailable);
            }

            bookDAO.updateBook(existingBook);
            System.out.println("\nBook updated successfully.");
        } catch (Exception exception) {
            System.err.println("\nFailed to update book.");
            exception.printStackTrace();
        }
    }
    
    private static void deleteBook(Scanner scanner, BookDAO bookDAO) {
        try {
            System.out.println("\nEnter ISBN to Delete");
            System.out.print("ISBN: ");
            String isbn = scanner.nextLine().trim();
            
            List<Book> books = bookDAO.findByIsbn(isbn);
            
            if (books.isEmpty()) {
                System.out.println("\nNo books found with ISBN: " + isbn);
            } else {
                System.out.println("\nBooks found with ISBN " + isbn + ":");
                	bookDAO.deleteBook(isbn);
                	System.out.println("\nBook deleted successfully.");
                
            }    
        } catch (Exception exception) {
            System.err.println("\nFailed to delete book.");
            exception.printStackTrace();
        }
    }
    
    private static void findAllBooks(BookDAO bookDAO) {
        try {
            List<Book> books = bookDAO.findAll();
            
            if (books.isEmpty()) {
            	System.out.println("\nNo books found");
            } else {
            	System.out.println("\n--------------------------"); 
                for (Book book : books) {
                	System.out.println("Title: " + book.getTitle());
                	System.out.println("Author: " + book.getAuthor());
                	System.out.println("ISBN: " + book.getIsbn());
                	System.out.println("Available: " + book.isAvailable());
                
                	System.out.println("--------------------------");       
                }
            }
        } catch (Exception exception) {
            System.err.println("\nFailed to retrieve books.");
            exception.printStackTrace();
        }
    }
    
    private static void findBookByTitle(Scanner scanner, BookDAO bookDAO) {
        try {
            System.out.println("\nEnter Title to Search: ");
            String title = scanner.nextLine();
            List<Book> books = bookDAO.findByTitle(title);
            	
            System.out.println("\n--------------------------");
            for (Book book : books) {
            	System.out.println("Title: " + book.getTitle());
            	System.out.println("Author: " + book.getAuthor());
            	System.out.println("ISBN: " + book.getIsbn());
            	System.out.println("Avialable: " + book.isAvailable());
            	
            	System.out.println("--------------------------");
            }
        } catch (Exception exception) {
            System.err.println("\nFailed to retrieve books by title.");
            exception.printStackTrace();
        }
    }

    private static void findBookByAuthor(Scanner scanner, BookDAO bookDAO) {
        try {
            System.out.println("\nEnter Author to Search:");
            String author = scanner.nextLine();
            List<Book> books = bookDAO.findByAuthor(author);
            
            System.out.println("\n--------------------------");
            for (Book book : books) {
            	System.out.println("Title: " + book.getTitle());
            	System.out.println("Author: " + book.getAuthor());
            	System.out.println("ISBN: " + book.getIsbn());
            	System.out.println("Available: " + book.isAvailable());
            	
            	System.out.println("--------------------------");
            }
        } catch (Exception exception) {
            System.err.println("\nFailed to retrieve books by author.");
            exception.printStackTrace();
        }
    }

    private static void findBookByIsbn(Scanner scanner, BookDAO bookDAO) {
        try {
            System.out.println("\nEnter ISBN to Search");
            System.out.print("ISBN: ");
            String isbn = scanner.nextLine().trim();
            
            List<Book> books = bookDAO.findByIsbn(isbn);
            
            if (books.isEmpty()) {
                System.out.println("\nNo books found with ISBN: " + isbn);
            } else {
                System.out.println("\nBooks found with ISBN " + isbn + ":");
                for (Book book : books) {
                    System.out.println("ID: " + book.getId() + " | " + "Title: " + " | " + book.getTitle() +
                    		" | " + "Author: " + book.getAuthor() + " | " + "Available? " + book.isAvailable());
                }
            }
        } catch (Exception exception) {
            System.err.println("\nFailed to search for books by ISBN.");
            exception.printStackTrace();
        }
    }
}
