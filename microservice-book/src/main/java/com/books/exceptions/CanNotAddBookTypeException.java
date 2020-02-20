package com.books.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class CanNotAddBookTypeException extends RuntimeException {

	public CanNotAddBookTypeException(String message) {
		super(message);
	}
}
