package com.mihir.bank_transactions.dao;

import java.util.List;

import com.mihir.bank_transactions.entity.BankAccount;
import com.mihir.bank_transactions.entity.DebitCard;
import com.mihir.bank_transactions.entity.TransactionStatement;

public interface BankDao {
	
	public boolean openBankAccount(BankAccount account);
	
	public void saveDebitCard(DebitCard debitCard);
	
	public void saveTransaction(TransactionStatement statement);
	
	public BankAccount getAccountDetailsByAccountNumber(long accountNumber);
	
	public DebitCard getDebitCardDetailsByAccountNumber(long accountNumber);
	
	public DebitCard getDebitCardDetailsByDebitCard(long debitCardNumber);
	
	public List<TransactionStatement> getTransactionDetailsByAccountNumber(long accountNumber);
	
	public List<TransactionStatement> getTransactionDetailsByDurationByAccountNumber(long accountNumber,   String startDate,  String endDate);
	
	public boolean updateAccount(BankAccount account);
	
	public boolean updateDebitCard(DebitCard card);
	
	public boolean deleteTransactionOfAccount(TransactionStatement statement);
	
	public boolean deleteBankAccount(BankAccount account);
	
	public boolean deleteDebitCardDetail (DebitCard card);

}
