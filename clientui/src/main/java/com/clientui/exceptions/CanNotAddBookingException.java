package com.clientui.exceptions;

public class CanNotAddBookingException extends RuntimeException {

	public CanNotAddBookingException (String message) {
		super(message);
	}
}
