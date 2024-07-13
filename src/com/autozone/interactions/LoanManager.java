package com.autozone.interactions;

import java.sql.Date;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.autozone.dao.LoanDAO;
import com.autozone.dao.MemberDAO;
import com.autozone.models.Loan;

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
	                    	returnLoan(scanner, loanDAO, null);
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
	            
	            Date loan_date = new Date(System.currentTimeMillis());
	            System.out.println("Current time is " + loan_date);
	            
	            boolean available = loanDAO.checkAvailableness (book_id);         
	            
	            if (available) {
		            Loan loan = new Loan(book_id, member_id, loan_date, null, false);
		            loanDAO.addLoan(loan);
		            System.out.println("\nLoan added successfully.");
	            } else {
	            	System.out.println("\nBook with ID: " + book_id + " is NOT available");     
	            }
	        } catch (Exception exception) {
	            System.err.println("Failed to add loan.");
	            exception.printStackTrace();
	        }
	    }
	    
	    private static void returnLoan(Scanner scanner, LoanDAO loanDAO, MemberDAO memberDAO) {
	        try {
	            System.out.println("\nEnter Member ID:");
	            String member_id = scanner.nextLine();
	                        
	            System.out.println("Enter Book ID:");
	            int book_id = scanner.nextInt();
	            scanner.nextLine(); 
	            
	            loanDAO.returnLoan(Integer.parseInt(member_id), book_id);
	        } catch (InputMismatchException e) {
	            System.err.println("Invalid input. Please enter numeric values for Book ID.");
	        } catch (SQLException exception) {
	            System.err.println("Failed to return loan.");
	            exception.printStackTrace();
	        }
	    }
	    
	    private static void loanHistory(Scanner scanner, LoanDAO loanDAO) {
	        try {
	            System.out.println("\nEnter Member ID:");
	            int member_id = scanner.nextInt();            
	            scanner.nextLine(); 
	            
	            List<Loan> loans = loanDAO.history(member_id);
	            System.out.println("--------------------------");
	            for (Loan loan : loans) {
	            	System.out.println("Member's ID: " + loan.getMember_id());
	                System.out.println("Loan ID: " + loan.getId());
	                System.out.println("Loan Date: " + loan.getLoan_date());
	                System.out.println("Return Date: " + loan.getReturn_date());
	                System.out.println("Returned: " + loan.isReturned());
	                
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
	                System.out.println("Book with ID: " + book_id + " is available");
	            } else {
	            	System.out.println("Book with ID: " + book_id + " is NOT available");     
	            }
	        } catch (Exception exception) {
	            System.err.println("Failed to find book.");
	            exception.printStackTrace();
	        }
	    }
}
