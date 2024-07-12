package com.autozone.interactions;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import com.autozone.dao.BookDAO;
import com.autozone.dao.LoanDAO;
import com.autozone.models.Book;
import com.autozone.models.Loan;
import com.autozone.models.Member;

public class LoanManager {

	 private LoanDAO loanDAO;
	 
	 public LoanManager(LoanDAO loanDAO) {
		 this.loanDAO = loanDAO;
	 }
	 
	 public static void manageLoans(Scanner scanner, LoanDAO loanDAO) {
		 boolean running = true;

	        while (running) {
	            try {
	                System.out.println("\nBook Management");
	                System.out.println("1. Add Loan");
	                System.out.println("2. Return Loan");
	                System.out.println("3. Loan history");
	                System.out.println("4. Check for availableness");
	                System.out.println("5. Return to Main Menu");

	                // Validates the user enters numbers only
	                int choice = 0;
	                try {
	                	choice = scanner.nextInt();
	                } catch (Exception exception) {
	                	System.err.println("\nInvalid choice. Please select a valid option.");
	                	exception.printStackTrace();
	                }
	                
	                scanner.nextLine(); 

	                switch (choice) {
	                    case 1:
	                        addLoan(scanner, loanDAO);
	                        break;
	                    case 2:
	                    	// returnLoan(scanner, loanDAO);
	                        break;
	                    case 3:
	                    	loanHistory(scanner, loanDAO);
	                        break;
	                    case 4:
	                    	checkAvailableness(scanner, loanDAO);
	                        break;
	                    case 5:
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
	 
	    private static void addLoan(Scanner scanner, LoanDAO loanDAO) {
	        try {
	            System.out.println("\nEnter Loan Details:");
	            System.out.print("Book ID: ");
	            int book_id = scanner.nextInt();
	            System.out.print("Member ID: ");
	            int member_id = scanner.nextInt();	            
	            Date loan_date = new java.sql.Date(System.currentTimeMillis());
	            System.out.print("Current time is " + loan_date);
	            scanner.nextLine(); 
	            
	            boolean available = loanDAO.checkAvailableness (book_id);         
	            
	            if (available) {
		            Loan loan = new Loan(book_id, member_id, loan_date, null, false);
		            loanDAO.addLoan(loan);
		            System.out.println("\nLoan added successfully.");
	            } else {
	            	System.out.println("\nBook with ID: " + book_id + "is NOT available");     
	            }
	        } catch (Exception exception) {
	            System.err.println("Failed to add loan.");
	            exception.printStackTrace();
	        }
	    }
	    
	    private static void loanHistory(Scanner scanner, LoanDAO loanDAO) {
	        try {
	            System.out.println("\nEnter Member ID:");
	            int member_id = scanner.nextInt();	            
	            scanner.nextLine(); 
	            
	            List<Loan> loans = loanDAO.history(member_id);
	            for (Loan loan : loans) {
	            	System.out.println("Name: " + loan.getMember_id());
	            	
	            	System.out.println("--------------------------");
	            }
	        } catch (Exception exception) {
	            System.err.println("Failed to find member");
	            exception.printStackTrace();
	        }
	    }
	    
	    private static void checkAvailableness(Scanner scanner, LoanDAO loanDAO) {
	        try {
	            System.out.println("\nEnter Book ID:");
	            int book_id = scanner.nextInt();	            
	            scanner.nextLine(); 
	            
	            boolean available = loanDAO.checkAvailableness (book_id);
	    
	            if (available) {
	                System.out.println("Book with ID: " + book_id + "is available");
	            } else {
	            	System.out.println("Book with ID: " + book_id + "is NOT available");     
	            }
	        } catch (Exception exception) {
	            System.err.println("Failed to find book.");
	            exception.printStackTrace();
	        }
	    }
	 
	 
}
