package com.books.exceptions;

public class CanNotAddAuthorException extends RuntimeException {
	
	public CanNotAddAuthorException (String message) {
		super(message);
	}

}
