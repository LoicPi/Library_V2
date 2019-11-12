package com.books.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CanNotAddBookException extends RuntimeException {

	public CanNotAddBookException (String message) {
		super(message);
	}
}
