package com.mihir.bank_transactions.dao;

import com.mihir.bank_transactions.entity.User;

public interface NetBankingDao {
	
	public User getUserDeatilsByUsername(String userName) ;
	
	public boolean saveUserDetails(User user);
	
	public User getUserByAccountNumber(long accountNumber);
	
	public boolean updateUser(User user);
	
	public boolean deleteUserDetails(User user);

}
