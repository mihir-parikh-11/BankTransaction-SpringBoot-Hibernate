package com.mihir.bank_transactions.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mihir.bank_transactions.customClass.CustomClass;
import com.mihir.bank_transactions.dao.BankDao;
import com.mihir.bank_transactions.dao.NetBankingDao;
import com.mihir.bank_transactions.entity.BankAccount;
import com.mihir.bank_transactions.entity.TransactionStatement;
import com.mihir.bank_transactions.entity.User;
import com.mihir.bank_transactions.modelClass.LoginUser;

@Service
public class NetBankingServiceImpl implements NetBankingService {

	@Autowired
	private NetBankingDao netBankingDao;

	@Autowired
	private BankDao bankDao;

	private int otp;
	private User user;
	private boolean loginStatus;
	private User loginUser;
	private BankAccount loginAccount;
	private boolean validateOTP;
	private BankAccount toAccount;
	private double amount;
	private boolean validateIFSCCode;

	@Override
	public void clearData() {
		this.otp = 0;
		this.user = null;
		this.loginStatus = false;
		this.loginUser = null;
		this.validateOTP = false;
		this.amount = 0;
	}

	@Override
	public User getUserDeatilsByUsername(String userName) {

		return netBankingDao.getUserDeatilsByUsername(userName);
	}

	@Override
	public HashMap<String, Object> saveUserDetailsBeforeVerify(User user) {
		HashMap<String, Object> hm = new HashMap<>();

		try {
			BankAccount account = bankDao.getAccountDetailsByAccountNumber(user.getAccountNumber());

			if (account != null) {
				User existUser = netBankingDao.getUserByAccountNumber(user.getAccountNumber());

				if (existUser == null) {

					long mobileNumber = account.getPerson().getMobileNumber();

					this.otp = ThreadLocalRandom.current().nextInt(999, 9999 + 1);
					this.user = user;

					hm.put("OTP", this.otp);
					hm.put("MobileNumber", mobileNumber);

				} else {
					hm.put("message", "User already exist at account number : " + account.getAccountNumber());
				}

			} else {
				hm.put("message", "Account number is wrong");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return hm;

	}

	@Override
	public boolean enterOTPForCreateUserAccout(int otp) {
		boolean isAdded = false;
		try {

			if (otp == this.otp) {
				isAdded = netBankingDao.saveUserDetails(user);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			clearData();
		}

		return isAdded;
	}

	@Override
	public Object loginNetBanking(LoginUser user) {
		boolean flag = false;
		HashMap<String, String> hm = null;
		try {
			User presentUser = getUserDeatilsByUsername(user.getUserName());

			if (presentUser != null) {

				if (presentUser.getPassword().equals(user.getPassword())) {

					this.loginAccount = bankDao.getAccountDetailsByAccountNumber(presentUser.getAccountNumber());
					this.loginStatus = true;
					this.loginUser = presentUser;

					flag = true;
				} else {
					hm = new HashMap<>();
					hm.put("message", "passsword is wrong");
					return hm;
				}

			} else {
				hm = new HashMap<>();
				hm.put("message", "Username not exist");

				return hm;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	@Override
	public Object changeUserPasswordUsingOldPassword(String oldPassword, String newPassword) {

		boolean flag = false;
		HashMap<String, String> hm = null;

		try {

			if (this.loginStatus) {

				String presentPassword = this.loginUser.getPassword();

				if (presentPassword.equals(oldPassword)) {

					this.loginUser.setPassword(newPassword);
					flag = netBankingDao.updateUser(this.loginUser);

				} else {
					hm = new HashMap<>();
					hm.put("message", "old password is wrong");
					return hm;
				}
			} else {
				hm = new HashMap<>();
				hm.put("message", "Please log-in again session expired.");
				return hm;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	@Override
	public HashMap<String, Object> forgotUserPassword(String userName) {
		HashMap<String, Object> hm = null;

		try {
			User user = getUserDeatilsByUsername(userName);
			if (user != null) {
				hm = new HashMap<>();

				long mobileNumber = bankDao.getAccountDetailsByAccountNumber(user.getAccountNumber()).getPerson()
						.getMobileNumber();
				this.otp = ThreadLocalRandom.current().nextInt(999, 9999 + 1);
				this.user = user;

				hm.put("OTP", this.otp);
				hm.put("MobileNumber", mobileNumber);

			} else {
				hm = new HashMap<>();
				hm.put("message", "username invalid");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return hm;
	}

	@Override
	public boolean checkOTPForForgotPassword(int otp) {
		boolean isTrue = false;

		try {
			if (this.otp == otp) {
				isTrue = true;
				this.otp = 0;
			}
			this.validateOTP = isTrue;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isTrue;
	}

	@Override
	public boolean updateNewPassword(String newPaasword) {
		boolean isUpdated = false;
		try {

			if (this.validateOTP) {
				this.user.setPassword(newPaasword);
				isUpdated = netBankingDao.updateUser(this.user);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			clearData();
		}

		return isUpdated;
	}

	@Override
	public boolean transferMoneyUsingNetBanking(long accountNumber, String IFSCCode, double amount) {
		boolean flag = false;
		try {
			if (this.loginStatus) {

				this.toAccount = bankDao.getAccountDetailsByAccountNumber(accountNumber);
				this.amount = amount;
				checkIFSCcodetoAccount(IFSCCode);
				flag = true;
			} else {
				clearData();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	@Override
	public HashMap<String, Object> verifyNetBankingTransactionPin(String transactionPin) {
		HashMap<String, Object> hashMap = new HashMap<>();
		try {
			if (this.loginStatus) {

				if (this.loginUser.getTransactionPin().equals(transactionPin)) {

					long mobileNumber = this.loginAccount.getPerson().getMobileNumber();
					this.otp = ThreadLocalRandom.current().nextInt(999, 9999 + 1);

					hashMap.put("OTP", this.otp);
					hashMap.put("MobileNumber", mobileNumber);
				} else {
					hashMap.put("message", "Enter valid transaction pin");
				}

			} else {
				clearData();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return hashMap;
	}

	@Override
	public Object verifyOTPForTransaction(int otp) {
		boolean issuccessful = false;

		HashMap<String, String> hashMap = null;
		try {
			if (this.loginStatus) {

				if (this.otp == otp) {

					if (this.loginAccount.getBalance() - this.amount > 1000) {

						if (this.validateIFSCCode) {

							this.loginAccount.setBalance(this.loginAccount.getBalance() - this.amount);
							this.toAccount.setBalance(this.toAccount.getBalance() + this.amount);

							bankDao.updateAccount(this.loginAccount);
							bankDao.updateAccount(this.toAccount);

							issuccessful = true;

							transactionStatement(this.loginAccount, this.amount, "debited", "successful",
									this.toAccount);
							Thread.sleep(1);
							transactionStatement(this.loginAccount, this.amount, "credited", "successful",
									this.toAccount);

						} else {
							hashMap = new HashMap<>();
							hashMap.put("message", "Transaction Failed due to low balance");
							transactionStatement(this.loginAccount, this.amount, "debited", "failed", this.toAccount);

							return hashMap;
						}

					} else {

						hashMap = new HashMap<>();
						hashMap.put("message", "Transaction Failed due to low balance");
						transactionStatement(this.loginAccount, this.amount, "debited", "failed", this.toAccount);

						return hashMap;
					}

				} else {

					if (this.otp != 0) {
						hashMap = new HashMap<>();
						hashMap.put("message", "Transaction Failed");
						transactionStatement(this.loginAccount, this.amount, "debited", "failed", this.toAccount);

						return hashMap;
					}

				}

			} else {
				clearData();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.amount = 0;
			this.toAccount = null;
			this.otp = 0;
			this.validateIFSCCode = false;

		}
		return issuccessful;
	}

	public void checkIFSCcodetoAccount(String IFSCcode) {

		try {

			if (IFSCcode.equals(this.toAccount.getIfscCode())) {
				this.validateIFSCCode = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void transactionStatement(BankAccount fromAccount, double amount, String type, String status,
			BankAccount toAccount) {
		{
			TransactionStatement statement = null;

			try {
				statement = new TransactionStatement();

				statement.setTransactionId(CustomClass.geerateTransactionId());
				statement.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd  HH-mm-ss").format(new Date()));

				if (status.equalsIgnoreCase("successful")) {

					if (type.equalsIgnoreCase("debited")) {
						statement.setAccountNumber(loginAccount.getAccountNumber());
						statement.setTransactionDetails("transferred money to account number-"
								+ toAccount.getAccountNumber() + " by net banking");
						statement.setTransactionAmount(amount);
						statement.setTransactionType("debited");
						statement.setStatus("successful");
						statement.setBalance(loginAccount.getBalance());
					} else {
						statement.setAccountNumber(toAccount.getAccountNumber());
						statement.setTransactionDetails(
								"recived money from account number " + loginAccount.getAccountNumber());
						statement.setTransactionAmount(amount);
						statement.setTransactionType("credited");
						statement.setStatus("successful");
						statement.setBalance(toAccount.getBalance());
					}

				} else {

					statement.setAccountNumber(loginAccount.getAccountNumber());
					statement.setTransactionDetails("transferred money Failed to account number-"
							+ toAccount.getAccountNumber() + " by net banking");
					statement.setTransactionAmount(amount);
					statement.setTransactionType("debited");
					statement.setStatus("failed");
					statement.setBalance(loginAccount.getBalance());
				}

				bankDao.saveTransaction(statement);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
}
