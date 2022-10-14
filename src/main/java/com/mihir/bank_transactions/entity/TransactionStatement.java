package com.mihir.bank_transactions.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TransactionStatement {

	@Id
	private long transactionId;

	private long accountNumber;
	private String transactionDetails;
	private double transactionAmount;
	private String transactionDate;
	private String transactionType; // c/d
	private String status; // s/f
	private double balance;

	public TransactionStatement() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TransactionStatement(long transactionId, long accountNumber, String transactionDetails,
			double transactionAmount, String transactionDate, String transactionType, String status, double balance) {
		super();
		this.transactionId = transactionId;
		this.accountNumber = accountNumber;
		this.transactionDetails = transactionDetails;
		this.transactionAmount = transactionAmount;
		this.transactionDate = transactionDate;
		this.transactionType = transactionType;
		this.status = status;
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "TransactionStatement [transactionId=" + transactionId + ", accountNumber=" + accountNumber
				+ ", transactionDetails=" + transactionDetails + ", transactionAmount=" + transactionAmount
				+ ", transactionDate=" + transactionDate + ", transactionType=" + transactionType + ", status=" + status
				+ ", balance=" + balance + "]";
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getTransactionDetails() {
		return transactionDetails;
	}

	public void setTransactionDetails(String transactionDetails) {
		this.transactionDetails = transactionDetails;
	}

	public double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

}
