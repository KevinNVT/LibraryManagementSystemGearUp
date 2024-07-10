package com.autozone.models;

import com.autozone.annotations.Isbn;
import com.autozone.annotations.NotNull;

public class Book {

	private int id;
	@NotNull
	private String title;
	private String author;
	@Isbn
	private int isbn;
	private boolean available = true;
	
	public Book(String title, String author, int isbn, boolean available) {
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.available = available;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getIsbn() {
		return isbn;
	}

	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
}
