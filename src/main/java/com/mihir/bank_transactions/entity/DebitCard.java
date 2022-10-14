package com.mihir.bank_transactions.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DebitCard {

	@Id
	private long debitCardNumber;
	@Column(nullable = false, unique = true)
	private long accountNumber;
	private String cardHolderName;
	private String cardExpiryDate;
	private int cvvNumber;
	private int pinNumber;

	public DebitCard() {
		super();
	}

	public DebitCard(long debitCardNumber, long accountNumber, String cardHolderName, String cardExpiryDate,
			int cvvNumber, int pinNumber) {
		super();
		this.debitCardNumber = debitCardNumber;
		this.accountNumber = accountNumber;
		this.cardHolderName = cardHolderName;
		this.cardExpiryDate = cardExpiryDate;
		this.cvvNumber = cvvNumber;
		this.pinNumber = pinNumber;
	}

	@Override
	public String toString() {
		return "DebitCard [debitCardNumber=" + debitCardNumber + ", accountNumber=" + accountNumber
				+ ", cardHolderName=" + cardHolderName + ", cardExpiryDate=" + cardExpiryDate + ", cvvNumber="
				+ cvvNumber + ", pinNumber=" + pinNumber + "]";
	}

	public long getDebitCardNumber() {
		return debitCardNumber;
	}

	public void setDebitCardNumber(long debitCardNumber) {
		this.debitCardNumber = debitCardNumber;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public String getCardExpiryDate() {
		return cardExpiryDate;
	}

	public void setCardExpiryDate(String cardExpiryDate) {
		this.cardExpiryDate = cardExpiryDate;
	}

	public int getCvvNumber() {
		return cvvNumber;
	}

	public void setCvvNumber(int cvvNumber) {
		this.cvvNumber = cvvNumber;
	}

	public int getPinNumber() {
		return pinNumber;
	}

	public void setPinNumber(int pinNumber) {
		this.pinNumber = pinNumber;
	}

}
