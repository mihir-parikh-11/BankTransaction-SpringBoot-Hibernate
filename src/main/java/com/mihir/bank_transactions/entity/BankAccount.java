package com.mihir.bank_transactions.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class BankAccount {

	@Id
	private long accountNumber;

	@NotNull(message = "account type is required")
	private String accountType;

	@Min(value = 500, message = "minimum balance 500 required")
	private double balance;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "panCardNumber")
	@NotNull(message = "Person details required")
	private Person person;

	@NotNull(message = "IFSC Code is required")
	private String ifscCode;

	
	
	public BankAccount() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "BankAccount [accountNumber=" + accountNumber + ", accountType=" + accountType + ", balance=" + balance
				+ ", person=" + person + ", ifscCode=" + ifscCode + "]";
	}

	public BankAccount(long accountNumber, @NotNull(message = "account type is required") String accountType,
			@Min(value = 500, message = "minimum balance 500 required") double balance,
			@NotNull(message = "Person details required") Person person,
			@NotNull(message = "IFSC Code is required") String ifscCode) {
		super();
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.balance = balance;
		this.person = person;
		this.ifscCode = ifscCode;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}



}
