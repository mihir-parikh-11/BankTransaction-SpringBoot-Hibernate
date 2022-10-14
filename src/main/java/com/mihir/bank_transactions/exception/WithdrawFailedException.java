package com.mihir.bank_transactions.exception;

public class WithdrawFailedException extends ATMCardException {

	public WithdrawFailedException(String message) {
		super(message);
	}

}
