package com.books.exceptions;

public class BorrowingInvalidRenewalException extends RuntimeException {

	public BorrowingInvalidRenewalException (String message ) {
		super (message);
	}
}
