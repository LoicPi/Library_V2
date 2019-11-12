package com.books.exceptions;

public class BookTypeNotFoundException extends RuntimeException {

	public BookTypeNotFoundException (String message) {
		super(message);
	}
}
