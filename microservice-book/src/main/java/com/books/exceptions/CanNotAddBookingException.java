package com.books.exceptions;

public class CanNotAddBookingException extends RuntimeException {

	public CanNotAddBookingException (String message) {
		super(message);
	}
}
