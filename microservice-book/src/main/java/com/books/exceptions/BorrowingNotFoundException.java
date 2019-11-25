package com.books.exceptions;

public class BorrowingNotFoundException extends RuntimeException {

	public BorrowingNotFoundException (String message) {
		super(message);
	}
}
