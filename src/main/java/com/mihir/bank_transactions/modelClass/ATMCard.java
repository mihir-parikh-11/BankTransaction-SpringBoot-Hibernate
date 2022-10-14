package com.mihir.bank_transactions.modelClass;

public class ATMCard {

	private long debitCardNumber;
	private int pinNumber;
	private double amount;

	public ATMCard() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ATMCard(long debitCardNumber, int pinNumber, double amount) {
		super();
		this.debitCardNumber = debitCardNumber;
		this.pinNumber = pinNumber;
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "ATMCard [debitCardNumber=" + debitCardNumber + ", pinNumber=" + pinNumber + ", amount=" + amount + "]";
	}

	public long getDebitCardNumber() {
		return debitCardNumber;
	}

	public void setDebitCardNumber(long debitCardNumber) {
		this.debitCardNumber = debitCardNumber;
	}

	public int getPinNumber() {
		return pinNumber;
	}

	public void setPinNumber(int pinNumber) {
		this.pinNumber = pinNumber;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
