package com.mihir.bank_transactions.exception;

public class AccountDeatilsWrongException extends RuntimeException {
	
	private String message;
	private long AccountNumber;
	private String ifscCode;
	private String transactionStatus;
	
	public AccountDeatilsWrongException(String message, long accountNumber, String ifscCode,String transactionStatus) {
		super();
		this.message = message;
		AccountNumber = accountNumber;
		this.ifscCode = ifscCode;
		this.transactionStatus=transactionStatus;
	}

	public String getMessage() {
		return message;
	}

	public long getAccountNumber() {
		return AccountNumber;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	
	
	

}
