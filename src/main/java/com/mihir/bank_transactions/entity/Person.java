package com.mihir.bank_transactions.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Person {

	@Id
	private String panCardNumber;
	private String name;
	private String dateOfBirth;
	private long mobileNumber;
	private String address;
	private String location;

	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getPanCardNumber() {
		return panCardNumber;
	}

	public void setPanCardNumber(String panCardNumber) {
		this.panCardNumber = panCardNumber;
	}

	@Override
	public String toString() {
		return "Person [panCardNumber=" + panCardNumber + ", name=" + name + ", dateOfBirth=" + dateOfBirth
				+ ", mobileNumber=" + mobileNumber + ", address=" + address + ", location=" + location + "]";
	}

	public Person(String panCardNumber, String name, String dateOfBirth, long mobileNumber, String address,
			String location) {
		super();
		this.panCardNumber = panCardNumber;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.mobileNumber = mobileNumber;
		this.address = address;
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
