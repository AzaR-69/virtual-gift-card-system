package com.virtual.gift.card.exceptions;

public class InvalidBalanceException extends Exception {
	public InvalidBalanceException(String errorMessage) {
        super(errorMessage);
    }
}
