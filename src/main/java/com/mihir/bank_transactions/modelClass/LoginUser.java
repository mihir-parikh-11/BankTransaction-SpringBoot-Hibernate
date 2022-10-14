package com.mihir.bank_transactions.modelClass;

import javax.validation.constraints.NotNull;

public class LoginUser {

	@NotNull(message = "username required")
	private String userName;

	@NotNull(message = "password required")
	private String password;

	public LoginUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoginUser(@NotNull(message = "username required") String userName,
			@NotNull(message = "password required") String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginUser [userName=" + userName + ", password=" + password + "]";
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

}
