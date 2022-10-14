package com.mihir.bank_transactions.service;

import java.util.HashMap;

import com.mihir.bank_transactions.entity.User;
import com.mihir.bank_transactions.modelClass.LoginUser;

public interface NetBankingService {
	
	public User getUserDeatilsByUsername(String userName) ;
	
	public HashMap<String, Object> saveUserDetailsBeforeVerify(User user);
	
	public boolean enterOTPForCreateUserAccout(int otp);
	
	public void clearData() ;
	
	public Object loginNetBanking(LoginUser user);
	
	public Object changeUserPasswordUsingOldPassword(String oldPassword,String newPassword);
	
	public HashMap<String, Object> forgotUserPassword(String userName);
	
	public boolean checkOTPForForgotPassword(int otp);
	
	public boolean updateNewPassword(String newPaasword);
	
	public boolean transferMoneyUsingNetBanking(long accountNumber, String IFSCCode, double amount);
	
	public HashMap<String, Object> verifyNetBankingTransactionPin(String transactionPin);
	
	public Object verifyOTPForTransaction(int otp);
	
	
	
}
