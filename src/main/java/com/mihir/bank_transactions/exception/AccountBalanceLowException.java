package com.mihir.bank_transactions.exception;

public class AccountBalanceLowException extends RuntimeException {
	
	private String transactionStatus;

	public AccountBalanceLowException(String message,String transactionStatus) {
		super(message);
		this.transactionStatus=transactionStatus;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	
	

}
