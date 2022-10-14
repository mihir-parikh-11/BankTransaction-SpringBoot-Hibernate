package com.mihir.bank_transactions.exception;

public class TransactionDetailsNotPresentException extends RuntimeException {

	public TransactionDetailsNotPresentException(String message) {
		super(message);
	}

}
