package com.mihir.bank_transactions.modelClass;

import java.util.List;


public class TransactionDetails {

	private long accountNumber;
	private List<Transactions> transactions;
	
	
	public TransactionDetails(long accountNumber, List<Transactions> transactions) {
		super();
		this.accountNumber = accountNumber;
		this.transactions = transactions;
	}
	public TransactionDetails() {
		super();
		
	}
	@Override
	public String toString() {
		return "TransactionDetails [accountNumber=" + accountNumber + ", transactions=" + transactions + "]";
	}
	public long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public List<Transactions> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transactions> transactions) {
		this.transactions = transactions;
	}

	

}
