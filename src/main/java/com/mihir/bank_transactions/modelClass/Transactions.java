package com.mihir.bank_transactions.modelClass;

public class Transactions {

	private long transactionId;
	private String transactionDetails;
	private double transactionAmount;
	private String transactionDate;
	private String transactionType;
	private String status;
	private double balance;

	
	@Override
	public String toString() {
		return "Transactions [transactionId=" + transactionId + ", transactionDetails=" + transactionDetails
				+ ", transactionAmount=" + transactionAmount + ", transactionDate=" + transactionDate
				+ ", transactionType=" + transactionType + ", status=" + status + ", balance=" + balance + "]";
	}

	public Transactions() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Transactions(long transactionId, String transactionDetails, double transactionAmount, String transactionDate,
			String transactionType, String status, double balance) {
		super();
		this.transactionId = transactionId;
		this.transactionDetails = transactionDetails;
		this.transactionAmount = transactionAmount;
		this.transactionDate = transactionDate;
		this.transactionType = transactionType;
		this.status = status;
		this.balance = balance;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
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

}
