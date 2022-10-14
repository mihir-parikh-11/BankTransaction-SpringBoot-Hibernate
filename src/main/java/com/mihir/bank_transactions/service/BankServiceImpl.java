package com.mihir.bank_transactions.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mihir.bank_transactions.customClass.CustomClass;
import com.mihir.bank_transactions.dao.BankDao;
import com.mihir.bank_transactions.dao.NetBankingDao;
import com.mihir.bank_transactions.entity.BankAccount;
import com.mihir.bank_transactions.entity.DebitCard;
import com.mihir.bank_transactions.entity.TransactionStatement;
import com.mihir.bank_transactions.entity.User;
import com.mihir.bank_transactions.modelClass.DebitCardMOdel;
import com.mihir.bank_transactions.modelClass.TransactionDetails;
import com.mihir.bank_transactions.modelClass.Transactions;
import com.mihir.bank_transactions.modelClass.TransferMoneyUsingApp;

@Service
public class BankServiceImpl implements BankService {

	@Autowired
	private BankDao bankDao;

	@Autowired
	private NetBankingDao netBankingDao;

	private DebitCard debitCard = null;
	private int otp;
	private double amount;
	private BankAccount fromAccount;
	private BankAccount toAccount;

	@Override
	public boolean openBankAccount(BankAccount account) {
		boolean isAdded = false;
		try {
			Thread.sleep(1);
			String acNumber = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
			account.setAccountNumber(Long.parseLong(acNumber));
			isAdded = bankDao.openBankAccount(account);

			if (isAdded) {
				// generate debit card save details in DB
				DebitCard card = generateDebitCardDetails(account);
				bankDao.saveDebitCard(card);

				// generate transaction details and save in db
				TransactionStatement statement = generaTransactionStatement(account, "successful");
				bankDao.saveTransaction(statement);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return isAdded;
	}

	@Override
	public DebitCard generateDebitCardDetails(BankAccount account) {

		DebitCard card = null;

		try {
			card = new DebitCard();
			card.setAccountNumber(account.getAccountNumber());
			card.setCardHolderName(account.getPerson().getName().toUpperCase());
			card.setCardExpiryDate(CustomClass.generateExpiryDate(new Date()));
			card.setCvvNumber(CustomClass.generateCvvNumber());
			card.setDebitCardNumber(CustomClass.generateNewDebitCardNumber());
			card.setPinNumber(CustomClass.getneratePinNumber());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return card;
	}

	@Override
	public TransactionStatement generaTransactionStatement(BankAccount account, String status) {

		TransactionStatement statement = null;

		try {
			statement = new TransactionStatement();

			statement.setTransactionId(CustomClass.geerateTransactionId());
			statement.setAccountNumber(account.getAccountNumber());
			statement.setTransactionDetails("money deposited using byCash");
			statement.setTransactionAmount(account.getBalance());
			statement.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd  HH-mm-ss").format(new Date()));
			statement.setTransactionType("credited");
			statement.setStatus(status);
			statement.setBalance(account.getBalance());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return statement;
	}

	@Override
	public BankAccount getAccountDetailsByAccountNumber(long accountNumber) {

		BankAccount account = bankDao.getAccountDetailsByAccountNumber(accountNumber);
		return account;
	}

	@Override
	public DebitCardMOdel getDebitCardDetailsByAccountNumber(long accountNumber) {
		DebitCardMOdel cardMOdel = null;

		DebitCard debitCard = bankDao.getDebitCardDetailsByAccountNumber(accountNumber);
		if (debitCard != null) {
			cardMOdel = new DebitCardMOdel(debitCard);
		}

		return cardMOdel;
	}

	@Override
	public TransactionDetails getTransactionDetailsByAccountNumber(long accountNumber) {
		TransactionDetails details = null;

		try {
			List<Transactions> listOfTransactions = new ArrayList<>();

			List<TransactionStatement> list = bankDao.getTransactionDetailsByAccountNumber(accountNumber);

			if (!list.isEmpty()) {
				for (TransactionStatement ts : list) {
					Transactions transactions = new Transactions(ts.getTransactionId(), ts.getTransactionDetails(),
							ts.getTransactionAmount(), ts.getTransactionDate(), ts.getTransactionType(), ts.getStatus(),
							ts.getBalance());
					listOfTransactions.add(transactions);
				}
			}
			details = new TransactionDetails(accountNumber, listOfTransactions);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return details;
	}

	@Override
	public TransactionDetails getTransactionDetailsByDurationByAccountNumber(long accountNumber, String startDate,
			String endDate) {
		TransactionDetails details = null;

		try {
			List<Transactions> listOfTransactions = new ArrayList<>();

			List<TransactionStatement> list = bankDao.getTransactionDetailsByDurationByAccountNumber(accountNumber,
					startDate, endDate);

			if (!list.isEmpty()) {
				for (TransactionStatement ts : list) {
					Transactions transactions = new Transactions(ts.getTransactionId(), ts.getTransactionDetails(),
							ts.getTransactionAmount(), ts.getTransactionDate(), ts.getTransactionType(), ts.getStatus(),
							ts.getBalance());
					listOfTransactions.add(transactions);
				}
			}
			details = new TransactionDetails(accountNumber, listOfTransactions);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return details;
	}

	@Override
	public TransactionDetails getTransactionByAcNumberByTransactionType(long accountNumber, String transactionType) {
		TransactionDetails details = null;

		try {

			details = getTransactionDetailsByAccountNumber(accountNumber);

			List<Transactions> list = details.getTransactions();

			Iterator<Transactions> iterator = list.iterator();

			while (iterator.hasNext()) {
				Transactions transactions = iterator.next();
				if (!transactions.getTransactionType().equalsIgnoreCase(transactionType)) {
					iterator.remove();
				}
			}

			details.setTransactions(list);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return details;
	}

	@Override
	public HashMap<String, Object> transBetweenTwoAcUsingApp(BankAccount fromAccount, BankAccount toAccount,
			TransferMoneyUsingApp transfer) {
		HashMap<String, Object> hm = new HashMap<>();
//		boolean isTransfer = false;

		try {

			this.fromAccount = fromAccount;
			this.toAccount = toAccount;
			this.amount = transfer.getAmount();

			long mobileNumber = fromAccount.getPerson().getMobileNumber();
			this.otp = ThreadLocalRandom.current().nextInt(999, 9999 + 1);

			hm.put("OTP", this.otp);
			hm.put("MobileNumber", mobileNumber);

//			fromAccount.setBalance(fromAccount.getBalance() - transfer.getAmount());
//			toAccount.setBalance(toAccount.getBalance() + transfer.getAmount());
//
//			boolean fromupdate = bankDao.updateAccountBalance(fromAccount);
//			boolean toupdate = bankDao.updateAccountBalance(toAccount);
//
//			if (fromupdate && toupdate) {
//				transactionStatementByApp(fromAccount, transfer.getAmount(), "debited", "successful", toAccount);
//				Thread.sleep(1);
//				transactionStatementByApp(fromAccount, transfer.getAmount(), "credited", "successful", toAccount);
//
//				isTransfer = true;
//			} else {
//				transactionStatementByApp(fromAccount, transfer.getAmount(), "debited", "fail", toAccount);
//			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return hm;
	}

	public boolean enterOTPInAppForTransaction(int otp) {
		boolean isTransfer = false;
		try {

			if (this.otp == otp) {

				this.fromAccount.setBalance(fromAccount.getBalance() - this.amount);
				this.toAccount.setBalance(toAccount.getBalance() + this.amount);

				bankDao.updateAccount(fromAccount);
				bankDao.updateAccount(toAccount);

				transactionStatementByApp(this.fromAccount, this.amount, "debited", "successful", this.toAccount);
				Thread.sleep(1);
				transactionStatementByApp(this.fromAccount, this.amount, "credited", "successful", this.toAccount);

				isTransfer = true;

			} else if (this.otp != 0 && otp != 0) {
				transactionStatementByApp(this.fromAccount, this.amount, "debited", "failed", this.toAccount);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.fromAccount = null;
			this.toAccount = null;
			this.amount = 0;
			this.otp = 0;
		}

		return isTransfer;
	}

	@Override
	public void transactionStatementByApp(BankAccount fromAccount, double amount, String type, String status,
			BankAccount toAccount) {
		TransactionStatement statement = null;

		try {
			statement = new TransactionStatement();

			statement.setTransactionId(CustomClass.geerateTransactionId());
			statement.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd  HH-mm-ss").format(new Date()));

			if (status.equalsIgnoreCase("successful")) {

				if (type.equalsIgnoreCase("debited")) {
					statement.setAccountNumber(fromAccount.getAccountNumber());
					statement.setTransactionDetails(
							"transferred money to account number " + toAccount.getAccountNumber());
					statement.setTransactionAmount(amount);
					statement.setTransactionType(type);
					statement.setStatus(status);
					statement.setBalance(fromAccount.getBalance());
				} else {
					statement.setAccountNumber(toAccount.getAccountNumber());
					statement.setTransactionDetails(
							"recived money from account number " + fromAccount.getAccountNumber());
					statement.setTransactionAmount(amount);
					statement.setTransactionType(type);
					statement.setStatus(status);
					statement.setBalance(toAccount.getBalance());
				}

			} else {

				statement.setAccountNumber(fromAccount.getAccountNumber());
				statement.setTransactionDetails(
						"transferred money Failed to account number " + toAccount.getAccountNumber());
				statement.setTransactionAmount(amount);
				statement.setTransactionType(type);
				statement.setStatus(status);
				statement.setBalance(fromAccount.getBalance());
			}

			bankDao.saveTransaction(statement);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public DebitCard getDebitCardDetailsByDebitCard(long debitCardNumber) {

		return bankDao.getDebitCardDetailsByDebitCard(debitCardNumber);
	}

	@Override
	public int paymentUsingDebitCard(DebitCard card, double amount) {
		int otp = 0;
		try {

			DebitCard presentCardDetails = getDebitCardDetailsByDebitCard(card.getDebitCardNumber());

			if (presentCardDetails != null) {
				boolean compareDebitCard = compareTwoDebitCardDetails(presentCardDetails, card);
				if (compareDebitCard) {
					this.debitCard = presentCardDetails;
					otp = ThreadLocalRandom.current().nextInt(999, 9999 + 1);
					this.otp = otp;
					this.amount = amount;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return otp;
	}

	@Override
	public boolean compareTwoDebitCardDetails(DebitCard presentCardDetails, DebitCard userEnterDetails) {
		boolean isValid = false;
		try {

			if (!(presentCardDetails.getCardExpiryDate().equals(userEnterDetails.getCardExpiryDate()))) {
				return false;
			} else if (!(presentCardDetails.getCvvNumber() == userEnterDetails.getCvvNumber()
					&& CustomClass.checkExpiryDate(presentCardDetails.getCardExpiryDate()))) {
				return false;
			} else if (!(presentCardDetails.getCardHolderName().equals(userEnterDetails.getCardHolderName()))) {
				return false;
			}
			isValid = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isValid;
	}

	@Override
	public boolean enterOtpPaymentUsingDebitCard(int otp) {
		boolean isCompleted = false;
		try {

			if (this.otp == otp) {

				BankAccount account = getAccountDetailsByAccountNumber(this.debitCard.getAccountNumber());
				if (account.getBalance() - 1000 >= this.amount) {

					account.setBalance(account.getBalance() - this.amount);

					isCompleted = bankDao.updateAccount(account);
					transactionStatementGenerateByDebitCard(account);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.otp = 0;
			this.debitCard = null;
			this.amount = 0;
		}

		return isCompleted;
	}

	@Override
	public void transactionStatementGenerateByDebitCard(BankAccount account) {

		TransactionStatement statement = null;
		try {
			statement = new TransactionStatement();

			statement.setTransactionId(CustomClass.geerateTransactionId());
			statement.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd  HH-mm-ss").format(new Date()));

			statement.setAccountNumber(account.getAccountNumber());
			statement.setTransactionDetails("debited money by using debitcard /" + this.debitCard.getDebitCardNumber());
			statement.setTransactionAmount(this.amount);
			statement.setTransactionType("debited");
			statement.setStatus("successful");
			statement.setBalance(account.getBalance());

			bankDao.saveTransaction(statement);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean withdrawMoneyUsingATMCard(BankAccount account, double amount) {
		boolean isWithdraw = false;
		try {

			account.setBalance(account.getBalance() - amount);
			isWithdraw = bankDao.updateAccount(account);
			transactionStatementATMWithdrwa(account, amount);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return isWithdraw;
	}

	@Override
	public void transactionStatementATMWithdrwa(BankAccount account, double amount) {

		TransactionStatement statement = null;
		try {
			statement = new TransactionStatement();

			statement.setTransactionId(CustomClass.geerateTransactionId());
			statement.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd  HH-mm-ss").format(new Date()));

			statement.setAccountNumber(account.getAccountNumber());
			statement.setTransactionDetails("withdrawed money by using ATM");
			statement.setTransactionAmount(amount);
			statement.setTransactionType("debited");
			statement.setStatus("successful");
			statement.setBalance(account.getBalance());
			bankDao.saveTransaction(statement);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public HashMap<String, Object> updateATMPinUsingOldPin(long debitCardNumber, int oldPin, int newPin) {
		HashMap<String, Object> hm = new HashMap<>();

		try {

			DebitCard debitCard = getDebitCardDetailsByDebitCard(debitCardNumber);

			if (debitCard.getPinNumber() == oldPin) {
				debitCard.setPinNumber(newPin);

				long mobileNumber = getAccountDetailsByAccountNumber(debitCard.getAccountNumber()).getPerson()
						.getMobileNumber();
				this.otp = ThreadLocalRandom.current().nextInt(999, 9999 + 1);

				hm.put("OTP", this.otp);
				hm.put("MobileNumber", mobileNumber);

				this.debitCard = debitCard;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return hm;
	}

	@Override
	public boolean enterOTPForupdateATMPin(int otp) {
		boolean isUpdate = false;
		try {

			if (this.otp == otp) {

				isUpdate = bankDao.updateDebitCard(debitCard);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.otp = 0;
			this.debitCard = null;
		}

		return isUpdate;
	}

	@Override
	public HashMap<String, Object> changeATMPinUsingAccountNumber(long debitCardNumber, long accountNumber,
			int newPin) {
		HashMap<String, Object> hm = new HashMap<>();

		try {

			DebitCard debitCard = getDebitCardDetailsByDebitCard(debitCardNumber);

			if (debitCard.getAccountNumber() == accountNumber) {
				debitCard.setPinNumber(newPin);

				long mobileNumber = getAccountDetailsByAccountNumber(debitCard.getAccountNumber()).getPerson()
						.getMobileNumber();
				this.otp = ThreadLocalRandom.current().nextInt(999, 9999 + 1);

				hm.put("OTP", this.otp);
				hm.put("MobileNumber", mobileNumber);

				this.debitCard = debitCard;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return hm;
	}

	@Override
	public boolean cashDepositeInAccount(BankAccount account, double amount) {
		boolean isDeposited = false;

		try {

			account.setBalance(account.getBalance() + amount);
			isDeposited = bankDao.updateAccount(account);
			if (isDeposited)
				generaTransactionStatementBYCash(account, amount);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return isDeposited;
	}

	public void generaTransactionStatementBYCash(BankAccount account, double amount) {

		TransactionStatement statement = null;

		try {
			statement = new TransactionStatement();

			statement.setTransactionId(CustomClass.geerateTransactionId());
			statement.setAccountNumber(account.getAccountNumber());
			statement.setTransactionDetails("money deposited using byCash");
			statement.setTransactionAmount(amount);
			statement.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd  HH-mm-ss").format(new Date()));
			statement.setTransactionType("credited");
			statement.setStatus("successful");
			statement.setBalance(account.getBalance());

			bankDao.saveTransaction(statement);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean deleteBankAccount(long accountNumber) {

		boolean isDeleted = false;

		try {

			BankAccount account = bankDao.getAccountDetailsByAccountNumber(accountNumber);

			if (account != null) {

				User user = netBankingDao.getUserByAccountNumber(accountNumber);

				if (user != null) {
					netBankingDao.deleteUserDetails(user);
				}
				DebitCard card = bankDao.getDebitCardDetailsByAccountNumber(accountNumber);
				bankDao.deleteDebitCardDetail(card);

				List<TransactionStatement> list = bankDao.getTransactionDetailsByAccountNumber(accountNumber);
				if (list.size() > 0) {

					for (TransactionStatement transactionStatement : list) {
						bankDao.deleteTransactionOfAccount(transactionStatement);
					}

				}

				isDeleted = bankDao.deleteBankAccount(account);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return isDeleted;
	}

}
