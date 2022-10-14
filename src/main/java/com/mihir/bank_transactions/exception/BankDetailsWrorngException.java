package com.mihir.bank_transactions.exception;

import com.mihir.bank_transactions.entity.BankAccount;

public class BankDetailsWrorngException extends RuntimeException {
	
	private String message;
	private BankAccount account;
	
	public BankDetailsWrorngException(String message, BankAccount account) {
		this.message=message;
		this.account=account;
	}

	public String getMessage() {
		return message;
	}

	public void setMsg(String msg) {
		this.message = msg;
	}

	public BankAccount getAccount() {
		return account;
	}

	public void setAccount(BankAccount account) {
		this.account = account;
	}
	
	

}
