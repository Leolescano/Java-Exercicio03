package org.kodigo.exception;

public class InvalidCreditCardException extends RuntimeException {
	public InvalidCreditCardException(String message) {
		super(message);
	}
}
