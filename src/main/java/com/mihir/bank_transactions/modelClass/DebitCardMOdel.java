package com.mihir.bank_transactions.modelClass;

import com.mihir.bank_transactions.entity.DebitCard;

public class DebitCardMOdel {

	private long debitCardNumber;
	private long accountNumber;
	private String cardHolderName;
	private String cardExpiryDate;

	public DebitCardMOdel(DebitCard card) {

		this.accountNumber = card.getAccountNumber();
		this.debitCardNumber = card.getDebitCardNumber();
		this.cardHolderName = card.getCardHolderName();
		this.cardExpiryDate = card.getCardExpiryDate();

	}

	public DebitCardMOdel() {
		super();
		// TODO Auto-generated constructor stub
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

}
