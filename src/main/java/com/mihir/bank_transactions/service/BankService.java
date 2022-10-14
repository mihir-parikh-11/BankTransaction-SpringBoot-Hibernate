package com.mihir.bank_transactions.service;

import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestParam;

import com.mihir.bank_transactions.entity.BankAccount;
import com.mihir.bank_transactions.entity.DebitCard;
import com.mihir.bank_transactions.entity.TransactionStatement;
import com.mihir.bank_transactions.modelClass.DebitCardMOdel;
import com.mihir.bank_transactions.modelClass.TransactionDetails;
import com.mihir.bank_transactions.modelClass.TransferMoneyUsingApp;

public interface BankService {

	public boolean openBankAccount(BankAccount account);

	public DebitCard generateDebitCardDetails(BankAccount account);

	public TransactionStatement generaTransactionStatement(BankAccount account, String status);

	public BankAccount getAccountDetailsByAccountNumber(long accountNumber);

	public DebitCardMOdel getDebitCardDetailsByAccountNumber(long accountNumber);

	public TransactionDetails getTransactionDetailsByAccountNumber(long accountNumber);

	public TransactionDetails getTransactionDetailsByDurationByAccountNumber(long accountNumber, String startDate,
			String endDate);

	public TransactionDetails getTransactionByAcNumberByTransactionType(long accountNumber, String status);

	public HashMap<String, Object> transBetweenTwoAcUsingApp(BankAccount fromAccount, BankAccount toAccount,
			TransferMoneyUsingApp transfer);
	
	public boolean enterOTPInAppForTransaction(int otp) ;

	public void transactionStatementByApp(BankAccount fromAccount, double amount, String type, String status,
			BankAccount toAccount);

	public int paymentUsingDebitCard(DebitCard card, double amount);

	public boolean compareTwoDebitCardDetails(DebitCard presentCardDetails, DebitCard userEnterDetails);

	public DebitCard getDebitCardDetailsByDebitCard(long debitCardNumber);

	public boolean enterOtpPaymentUsingDebitCard(int otp);

	public void transactionStatementGenerateByDebitCard(BankAccount account);

	public boolean withdrawMoneyUsingATMCard(BankAccount account, double amount);

	public void transactionStatementATMWithdrwa(BankAccount account, double amount);

	public HashMap<String, Object> updateATMPinUsingOldPin(long debitCardNumber, int oldPin, int newPin);
	
	public HashMap<String, Object> changeATMPinUsingAccountNumber(long debitCardNumber, long accountNumber, int newPin);
	
	public boolean enterOTPForupdateATMPin(@RequestParam int otp);
	
	public boolean cashDepositeInAccount(BankAccount account,double amount);
	
	public boolean deleteBankAccount(long accountNumber);

}
