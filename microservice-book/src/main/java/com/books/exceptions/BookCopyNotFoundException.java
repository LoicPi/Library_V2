package com.books.exceptions;

public class BookCopyNotFoundException extends RuntimeException {

	public BookCopyNotFoundException(String message) {
		super(message);
	}
}
