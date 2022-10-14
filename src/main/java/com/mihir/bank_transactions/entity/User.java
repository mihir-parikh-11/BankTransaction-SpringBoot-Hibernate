package com.mihir.bank_transactions.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class User {

	@Id
	@NotNull(message = "username required")
	private String userName;

	@NotNull(message = "password required")
	private String password;
	
	@Min(value = 1, message = "account number required")
	@Column(name = "accountnumber")
	private long accountNumber;

	@NotNull(message = "transaction pin required")
	private String transactionPin;

	@NotNull(message = "email-Id required")
	private String emailId;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String userName, String password, long accountNumber, String transactionPin, String emailId) {
		super();
		this.userName = userName;
		this.password = password;
		this.accountNumber = accountNumber;
		this.transactionPin = transactionPin;
		this.emailId = emailId;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", password=" + password + ", accountNumber=" + accountNumber
				+ ", transactionPin=" + transactionPin + ", emailId=" + emailId + "]";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getTransactionPin() {
		return transactionPin;
	}

	public void setTransactionPin(String transactionPin) {
		this.transactionPin = transactionPin;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}
