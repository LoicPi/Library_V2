package com.books.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class BorrowingInvalidRenewalException extends RuntimeException {

	public BorrowingInvalidRenewalException (String message ) {
		super (message);
	}
}
