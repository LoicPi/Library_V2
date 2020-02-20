package com.books.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class CanNotAddBookingException extends RuntimeException {

	public CanNotAddBookingException (String message) {
		super(message);
	}
}
