package com.autozone.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.autozone.database.DatabaseConnection;
import com.autozone.models.Loan;
import com.autozone.utils.Validator;

public class LoanDAO {

	public void addLoan(Loan loan) throws SQLException, IllegalArgumentException, IllegalAccessException {
		Validator.validate(loan);
		
		String sql = "INSERT INTO tbl_loans (member_id, loan_date, return_date, returned) VALUES (?,?,?, ?)";
		
		//try-with-resources
		try(Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement psmt = conn.prepareStatement(sql)) {
			
			psmt.setInt(1, loan.getBook_id());
			psmt.setInt(2, loan.getMember_id());
			psmt.setDate(3, new java.sql.Date(loan.getLoan_date().getTime())); // psmt.setDate(3, new java.sql.Date(System.currentTimeMillis()));
			psmt.setNull(4, java.sql.Types.DATE);
			psmt.setBoolean(5, loan.isReturned());
			
			psmt.executeUpdate();
		} catch (SQLException exception) {
			System.err.println("Failed to add loan.");
	        exception.printStackTrace();
		}
	}
	
	public void returnLoan(Loan loan) throws SQLException, IllegalArgumentException, IllegalAccessException {
		Validator.validate(loan);
		
		String sql = "UPDATE tbl_loans SET SET return_date = ?, returned = ? WHERE id = ?";
		
		//try-with-resources
		try(Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement psmt = conn.prepareStatement(sql)) {
			
			psmt.setInt(1, loan.getMember_id());
			psmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));
			psmt.setBoolean(3, true);
			
			psmt.executeUpdate();
		}
	}
	
	public List<Loan> history(int member_id) throws SQLException, IllegalArgumentException, IllegalAccessException {
		
		String sql = "SELECT l.id, b.title, l.loan_date, l.return_date, l.returned "
				+ "FROM tbl_loans l "
				+ "JOIN tbl_books b ON b.id = ("
					+ "SELECT book_id "
					+ "FROM tbl_loans "
					+ "WHERE id = l.id"
					+ ") "
				+ "WHERE l.member_id = ?";
		
		List<Loan> loans = new ArrayList<Loan>();
		
		//try-with-resources
		try(Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement psmt = conn.prepareStatement(sql)) {
			
				psmt.setInt(1, member_id);
		    	ResultSet rs = psmt.executeQuery();
			
			while(rs.next()) {
				Loan loan = new Loan(
						rs.getInt("book_id"), 
						rs.getInt("member_id"), 
						rs.getDate("loan_date"), 
						rs.getDate("return_date"), 
						rs.getBoolean("availableness"));

				loan.setId(rs.getInt("id"));
				loans.add(loan);
			}
		}
		return loans;
	}
	
	public boolean checkAvailableness(int book_id) throws SQLException {
		
		boolean available = false;
		String sql = "SELECT available FROM tbl_books WHERE id = ?";
		
		//try-with-resources
		try(Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement psmt = conn.prepareStatement(sql)) {
			
				psmt.setInt(1, book_id);
			
			try (ResultSet resultSet = psmt.executeQuery()) {
	            if (resultSet.next()) {
	                available = resultSet.getBoolean("available");
	            } else {
	            	System.out.println("Book with ID " + book_id + " not found.");
	            }
	        }
	    } catch (SQLException Exception) {
	    	System.err.println("Error checking availability for book ID: " + book_id);
	        Exception.printStackTrace();
	    }
	    
	    return available;
	}	
}
