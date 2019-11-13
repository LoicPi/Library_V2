package com.books.exceptions;

public class CanNotAddBookCopyException extends RuntimeException {

	public CanNotAddBookCopyException (String message) {
		super(message);
	}
}
