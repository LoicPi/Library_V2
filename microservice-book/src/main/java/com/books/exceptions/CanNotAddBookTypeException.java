package com.books.exceptions;

public class CanNotAddBookTypeException extends RuntimeException {

	public CanNotAddBookTypeException(String message) {
		super(message);
	}
}
