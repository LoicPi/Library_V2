package com.books.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class CanNotAddAuthorException extends RuntimeException {
	
	public CanNotAddAuthorException (String message) {
		super(message);
	}

}
