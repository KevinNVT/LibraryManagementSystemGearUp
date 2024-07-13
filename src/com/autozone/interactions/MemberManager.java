package com.autozone.interactions;

import java.util.List;
import java.util.Scanner;

import com.autozone.dao.BookDAO;
import com.autozone.dao.MemberDAO;
import com.autozone.models.Book;
import com.autozone.models.Member;

	public class MemberManager {

	 private MemberDAO memberDAO;
	 
	 public MemberManager(MemberDAO memberDAO) {
		 this.memberDAO = memberDAO;
	 }
	 
	 public static void manageMembers(Scanner scanner, MemberDAO memberDAO) {
		 boolean running = true;

	        while (running) {
	            try {
	                System.out.println("\nMember Management");
	                System.out.println("1. Add Member");
	                System.out.println("2. Update Member");
	                System.out.println("3. Delete Member");
	                System.out.println("4. Find All Members");
	                System.out.println("5. Find Member by ID");
	                System.out.println("6. Find Member by Name");
	                System.out.println("7. Return to Main Menu");
	                
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
	                        addMember(scanner, memberDAO);
	                        break;
	                    case 2:
	                    	updateMember(scanner, memberDAO);
	                        break;
	                    case 3:
	                    	deleteMember(scanner, memberDAO);
	                        break;
	                    case 4:
	                    	findAllMemebers(memberDAO);
	                        break;
	                    case 5:
	                    	findMemberByMemberId(scanner, memberDAO);
	                        break;
	                    case 6:
	                    	findMemberByName(scanner, memberDAO);
	                        break;
	                    case 7:
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

	    private static void addMember(Scanner scanner, MemberDAO memberDAO) {
	        try {
	            System.out.println("\nEnter Member Details:");
	            System.out.print("Member name: ");
	            String member_name = scanner.nextLine();
	            System.out.print("Member ID: ");
	            String member_id = scanner.nextLine();

	            Member member = new Member(member_name, member_id);
	            memberDAO.addMember(member);
	            System.out.println("\nMember added successfully.");
	        } catch (Exception exception) {
	            System.err.println("\nFailed to add Member.");
	            exception.printStackTrace();
	        }
	    }
	    
	    private static void updateMember(Scanner scanner, MemberDAO memberDAO) {
	        try {
	            System.out.println("\nEnter Member's ID to update:");
	            System.out.print("Member's ID: ");
	            String input = scanner.nextLine().trim();

	            if (input == null || input.isEmpty()) {
	                System.err.println("\nInvalid input. Please enter a valid Member ID.");
	                return;
	            }

	            List<Member> members = memberDAO.findByMemberId(input);
	            if (members.isEmpty()) {
	                System.out.println("\nMember with ID " + input + " not found.");
	                return;
	            }

	            Member existingMember = members.get(0);
	            String originalId = existingMember.getMember_id(); 

	            System.out.println("\nCurrent Member Details:");
	            System.out.println("Name: " + existingMember.getMember_name());
	            System.out.println("ID: " + existingMember.getMember_id());

	            // User is able to leaves the answer blank to keep the current data
	            System.out.println("\nEnter Updated Member Details (Leave blank to keep current):");
	            System.out.print("New Name: ");
	            String newName = scanner.nextLine();
	            if (!newName.isBlank()) {
	                existingMember.setMember_name(newName);
	            }

	            System.out.print("New ID: ");
	            String newId = scanner.nextLine();
	            if (!newId.isBlank()) {
	                existingMember.setMember_id(newId);
	            }

	            System.out.println("\nUpdating member with original ID: " + originalId);
	            memberDAO.updateMember(existingMember, originalId); 
	            System.out.println("\nMember updated successfully.");
	        } catch (Exception exception) {
	            System.err.println("\nFailed to update member.");
	            exception.printStackTrace();
	        }
	    }
	    
	    private static void deleteMember(Scanner scanner, MemberDAO memberDAO) {
	        try {
	            System.out.println("\nEnter Member ID to Delete:");
	            int id = scanner.nextInt();
	            scanner.nextLine();

	            memberDAO.deleteMember(id);
	            System.out.println("\nMember deleted successfully.");
	        } catch (Exception exception) {
	            System.err.println("\nFailed to delete Member.");
	            exception.printStackTrace();
	        }
	    }
	    
	    private static void findAllMemebers(MemberDAO memberDAO) {
	        try {
	            List<Member> members = memberDAO.findAll();
	            
	            if (members.isEmpty()) {
	            	System.out.println("\nNo members found.");
	            } else {
	            	System.out.println("\n--------------------------"); 
		            for (Member member : members) {
		            	System.out.println("Name: " + member.getMember_name());
		            	System.out.println("ID: " + member.getMember_id());
		            
		            	System.out.println("--------------------------");
		            }
	            }
	        } catch (Exception exception) {
	            System.err.println("\nFailed to retrieve members.");
	            exception.printStackTrace();
	        }
	    }
	    
	    // Method not accessible by GUI but used for other verifications
	    private static void findMemberByMemberId(Scanner scanner, MemberDAO memberDAO) {
	        try {
	            System.out.println("\nEnter Member's ID: ");
	            System.out.print("ID: ");
	            String member_id = scanner.nextLine().trim();
	            
	            List<Member> members = memberDAO.findByMemberId(member_id);
	            
	            if (members.isEmpty()) {
	                System.out.println("\nNo members found with ID: " + member_id);
	            } else {
	                System.out.println("\nBooks found with ISBN " + member_id + ":");
	                for (Member member : members) {
	                    System.out.println("ID: " + member.getId() + " | " + "Name: " + member.getMember_name() +
	                    		" | " + "Member ID: " + member.getMember_id());
	                }
	            }
	        } catch (Exception exception) {
	            System.err.println("\nFailed to search for members by ID.");
	            exception.printStackTrace();
	        }
	    }

	    private static void findMemberByName(Scanner scanner, MemberDAO memberDAO) {
	        try {
	            System.out.println("\nEnter Member's name:");
	            String name = scanner.nextLine();
	            List<Member> members = memberDAO.findByName(name);
	            
	            System.out.println("--------------------------");
	            for (Member member : members) {
	            	System.out.println("Name: " + member.getMember_name());
	            	System.out.println("ID: " + member.getMember_id());
	            
	            	System.out.println("--------------------------");

	            }
	        } catch (Exception exception) {
	            System.err.println("\nFailed to retrieve members by name.");
	            exception.printStackTrace();
	        }
	    }
}
