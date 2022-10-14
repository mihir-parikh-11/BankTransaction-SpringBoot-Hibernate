package com.mihir.bank_transactions.modelClass;

public class TransferMoneyUsingApp {

	private long fromAccountNumber;
	private long toAccountNumber;
	private String toIfscCode;
	private double amount;


	public TransferMoneyUsingApp() {
		super();
	}


	public TransferMoneyUsingApp(long fromAccountNumber, long toAccountNumber, String toIfscCode, double amount) {
		super();
		this.fromAccountNumber = fromAccountNumber;
		this.toAccountNumber = toAccountNumber;
		this.toIfscCode = toIfscCode;
		this.amount = amount;
	}


	@Override
	public String toString() {
		return "TransferMoneyUsingApp [fromAccountNumber=" + fromAccountNumber + ", toAccountNumber=" + toAccountNumber
				+ ", toIfscCode=" + toIfscCode + ", amount=" + amount + "]";
	}


	public long getFromAccountNumber() {
		return fromAccountNumber;
	}


	public void setFromAccountNumber(long fromAccountNumber) {
		this.fromAccountNumber = fromAccountNumber;
	}


	public long getToAccountNumber() {
		return toAccountNumber;
	}


	public void setToAccountNumber(long toAccountNumber) {
		this.toAccountNumber = toAccountNumber;
	}


	

	public String getToIfscCode() {
		return toIfscCode;
	}


	public void setToIfscCode(String toIfscCode) {
		this.toIfscCode = toIfscCode;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}

	

}
