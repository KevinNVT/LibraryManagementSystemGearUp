package com.autozone.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.autozone.database.DatabaseConnection;
import com.autozone.models.Book;
import com.autozone.utils.Validator;

public class BookDAO {

	public void addBook(Book book) throws SQLException, IllegalArgumentException, IllegalAccessException {
		Validator.validate(book);
		
		String sql = "INSERT INTO tbl_books (title, author, isbn, available) VALUES (?,?,?,?)";
		
		//try-with-resources
		try(Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement psmt = conn.prepareStatement(sql)) {
			
			psmt.setString(1, book.getTitle());
			psmt.setString(2, book.getAuthor());
			psmt.setString(3, book.getIsbn());
			psmt.setBoolean(4, book.isAvailable());
			
			/* Prints query and parameters. Test
	        System.out.println("Executing query: " + sql);
	        System.out.println("With parameters: " + book.getTitle() + ", " + book.getAuthor() + ", " + book.getIsbn() + ", " + book.isAvailable());
			*/
			
			psmt.executeUpdate();
		}	
	}
	
	public void updateBook(Book book) throws SQLException, IllegalArgumentException, IllegalAccessException {
		Validator.validate(book);
		
		String sql = "UPDATE tbl_books SET title = ?, author = ?, isbn = ?, available = ? WHERE id = ?";
		
		//try-with-resources
		try(Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement psmt = conn.prepareStatement(sql)) {
			
			psmt.setString(1, book.getTitle());
			psmt.setString(2, book.getAuthor());
			psmt.setString(3, book.getIsbn());
			psmt.setBoolean(4, book.isAvailable());
			psmt.setInt(5, book.getId());
			
			psmt.executeUpdate();
		}
	}
	
	
	public void deleteBook(int id) throws SQLException {
		String sql = "DELETE FROM tbl_books WHERE id = ?";

		//try-with-resources
		try(Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement psmt = conn.prepareStatement(sql)) {
			
			psmt.setInt(1, id);	
			psmt.executeUpdate();
		}	
	}
	
	public List<Book> findAll() throws SQLException {
		String sql = "SELECT * FROM tbl_books";
		List<Book> books = new ArrayList<Book>();

		//try-with-resources
		try(Connection conn = DatabaseConnection.getInstance().getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
				
			Book book = null;
			
			while(rs.next()) {
				book = new Book(
						rs.getString("title"), 
						rs.getString("author"), 
						rs.getString("isbn"), 
						rs.getBoolean("available")
				);

				book.setId(rs.getInt("id"));
				books.add(book);
			}
		}	
		return books;
	}
	
    public Book findBookById(int id) throws SQLException {
        String sql = "SELECT * FROM tbl_books WHERE id = ?";
        Book book = null;

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    book = new Book(
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("isbn"),
                            rs.getBoolean("available")
                    );
                    book.setId(rs.getInt("id"));
                }
            }
        }

        return book;
    }

	public List<Book> findByTitle(String title) throws SQLException {
	    String sql = "SELECT * FROM tbl_books WHERE title LIKE ?";
	    /*I instantiated the list without explicitly specifying the type of elements it will contain. 
	    This is a test for educational purposes*/
	    List<Book> books = new ArrayList<>();

	    // try-with-resources
	    try (Connection conn = DatabaseConnection.getInstance().getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setString(1, "%" + title + "%");
	        
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                Book book = new Book(
	                    rs.getString("title"),
	                    rs.getString("author"),
	                    rs.getString("isbn"),
	                    rs.getBoolean("available")
	                );
	                
	                book.setId(rs.getInt("id"));
	                books.add(book);
	            }
	        }
	    }
	    return books;
	}
	
	public List<Book> findByAuthor(String author) throws SQLException {
	    String sql = "SELECT * FROM tbl_books WHERE author LIKE ?";
	    /*I instantiated the list without explicitly specifying the type of elements it will contain. 
	    This is a test for educational purposes*/
	    List<Book> books = new ArrayList<>();

	    // try-with-resources
	    try (Connection conn = DatabaseConnection.getInstance().getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setString(1, "%" + author + "%");
	        
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                Book book = new Book(
	                    rs.getString("title"),
	                    rs.getString("author"),
	                    rs.getString("isbn"),
	                    rs.getBoolean("available")
	                );
	                
	                book.setId(rs.getInt("id"));
	                books.add(book);
	            }
	        }
	    }
	    return books;
	}
	
	public List<Book> findByIsbn(String isbn) throws SQLException, IllegalArgumentException, IllegalAccessException {
	    Validator.validate(isbn);
		
		String sql = "SELECT * FROM tbl_books WHERE isbn LIKE ?";
	    /*I instantiated the list without explicitly specifying the type of elements it will contain. 
	    This is a test for educational purposes*/
	    List<Book> books = new ArrayList<>();

	    // try-with-resources
	    try (Connection conn = DatabaseConnection.getInstance().getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setString(1, "%" + isbn + "%");
	        
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                Book book = new Book(
	                    rs.getString("title"),
	                    rs.getString("author"),
	                    rs.getString("isbn"),
	                    rs.getBoolean("available")
	                );
	                
	                book.setId(rs.getInt("id"));
	                books.add(book);
	            }
	        }
	    }
	    return books;
	}
}
