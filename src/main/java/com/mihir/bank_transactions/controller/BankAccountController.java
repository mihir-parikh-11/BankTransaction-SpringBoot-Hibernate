package com.mihir.bank_transactions.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mihir.bank_transactions.customClass.CustomClass;
import com.mihir.bank_transactions.entity.BankAccount;
import com.mihir.bank_transactions.entity.DebitCard;
import com.mihir.bank_transactions.exception.ATMCardExpiredException;
import com.mihir.bank_transactions.exception.ATMCardPinWrongException;
import com.mihir.bank_transactions.exception.ATMCardWithdrawLimitException;
import com.mihir.bank_transactions.exception.ATMOldPinWrongException;
import com.mihir.bank_transactions.exception.AccountBalanceLowException;
import com.mihir.bank_transactions.exception.AccountDeatilsWrongException;
import com.mihir.bank_transactions.exception.AccountNumberWrongException;
import com.mihir.bank_transactions.exception.BankAccountNotPresentException;
import com.mihir.bank_transactions.exception.BankDetailsWrorngException;
import com.mihir.bank_transactions.exception.DebitCardDetailsWrongException;
import com.mihir.bank_transactions.exception.DebitCardNotPresentException;
import com.mihir.bank_transactions.exception.DepositAmountZeroException;
import com.mihir.bank_transactions.exception.PaymentFailedException;
import com.mihir.bank_transactions.exception.ServerErrorException;
import com.mihir.bank_transactions.exception.TransactionDetailsNotPresentException;
import com.mihir.bank_transactions.exception.TransactonFailException;
import com.mihir.bank_transactions.exception.WithdrawFailedException;
import com.mihir.bank_transactions.exception.WrongOTPEnterException;
import com.mihir.bank_transactions.modelClass.ATMCard;
import com.mihir.bank_transactions.modelClass.DebitCardMOdel;
import com.mihir.bank_transactions.modelClass.TransactionDetails;
import com.mihir.bank_transactions.modelClass.TransferMoneyUsingApp;
import com.mihir.bank_transactions.service.BankService;

@RestController
public class BankAccountController {

	@Autowired
	private BankService service;

	@PostMapping(value = "/openBankAccount")
	public ResponseEntity<Boolean> openBankAccount(@Valid @RequestBody BankAccount account) {

		boolean isAdded = service.openBankAccount(account);

		if (isAdded) {
			return new ResponseEntity<Boolean>(isAdded, HttpStatus.OK);
		} else {
			throw new BankDetailsWrorngException("Bank Account deatils wrong please check the deatils ", account);
		}
	}

	@GetMapping(value = "/getAccountDetailsByAccountNumber")
	public ResponseEntity<BankAccount> getAccountDetailsByAccountNumber(@RequestParam long accountNumber) {

		BankAccount account = service.getAccountDetailsByAccountNumber(accountNumber);

		if (account != null) {
			return new ResponseEntity<BankAccount>(account, HttpStatus.OK);
		} else {
			throw new BankAccountNotPresentException("Bank Account not present at accountNumber : " + accountNumber);
		}
	}

	@GetMapping(value = "/getDebitCardDetailsByAccountNumber/{accountNumber}")
	public ResponseEntity<DebitCardMOdel> getDebitCardDetailsByAccountNumber(@PathVariable long accountNumber) {

		DebitCardMOdel debitCard = service.getDebitCardDetailsByAccountNumber(accountNumber);

		if (debitCard != null) {
			return new ResponseEntity<DebitCardMOdel>(debitCard, HttpStatus.OK);
		} else {
			throw new DebitCardNotPresentException("Debit card not present at account Number : " + accountNumber);
		}

	}

	@GetMapping(value = "getTransactionDetailsBYAccountNumber")
	public ResponseEntity<TransactionDetails> getTransactionDetailsByAccountNumber(@RequestParam long accountNumber) {

		TransactionDetails details = service.getTransactionDetailsByAccountNumber(accountNumber);

		if (!details.getTransactions().isEmpty()) {
			return new ResponseEntity<TransactionDetails>(details, HttpStatus.OK);
		} else {
			throw new BankAccountNotPresentException("Bank Account not present at accountNumber : " + accountNumber);
		}

	}

	@GetMapping(value = "getTransactionDetailsByDurationByAccountNumber")
	public ResponseEntity<TransactionDetails> getTransactionDetailsByDurationByAccountNumber(
			@RequestParam long accountNumber, @RequestParam String startDate, @RequestParam String endDate) {

		BankAccount account = service.getAccountDetailsByAccountNumber(accountNumber);

		if (account != null) {
			TransactionDetails details = service.getTransactionDetailsByDurationByAccountNumber(accountNumber,
					startDate, endDate);

			if (!details.getTransactions().isEmpty()) {
				return new ResponseEntity<TransactionDetails>(details, HttpStatus.OK);
			} else {

				throw new TransactionDetailsNotPresentException(
						"Transactions deatils not present between " + startDate + " to " + endDate + " date");
			}

		} else {
			throw new BankAccountNotPresentException("Bank Account not present at accountNumber : " + accountNumber);
		}

	}

	/**
	 *This API is created for //////////////////////
	 * @param accountNumber
	 * @param transactionType
	 * @return
	 */
	@GetMapping(value = "getTransactionByAcNumberByTransactionType")
	public ResponseEntity<TransactionDetails> getTransactionByAcNumberByTransactionType(
			@RequestParam long accountNumber, @RequestParam String transactionType) {
		
		if("debit".equals(transactionType) || transactionType.equals("credit")) {
			
			BankAccount account = service.getAccountDetailsByAccountNumber(accountNumber);

			if (account != null) {
				TransactionDetails details = service.getTransactionByAcNumberByTransactionType(accountNumber,
						transactionType);

				if (!details.getTransactions().isEmpty()) {
					return new ResponseEntity<TransactionDetails>(details, HttpStatus.OK);
				} else {

					throw new TransactionDetailsNotPresentException(
							"Transactions deatils not present for transaction status is " + transactionType);
				}

			} else {
				throw new BankAccountNotPresentException("Bank Account not present at accountNumber : " + accountNumber);
			}
			
		}else {
			throw new TransactonFailException("wrong transaction type");
		}

		

	}

	@PostMapping(value = "transBetweenTwoAcUsingApp")
	public ResponseEntity<HashMap<String, Object>> transBetweenTwoAcUsingApp(
			@RequestBody TransferMoneyUsingApp transfer) {

		HashMap<String, Object> hm = new HashMap<>();

		// money transfer from account
		BankAccount fromAccount = service.getAccountDetailsByAccountNumber(transfer.getFromAccountNumber());

		// money Receive to account
		BankAccount toAccount = service.getAccountDetailsByAccountNumber(transfer.getToAccountNumber());

		if (toAccount != null && toAccount.getIfscCode().equalsIgnoreCase(transfer.getToIfscCode())) {

			if ((fromAccount.getBalance() - 1000) >= transfer.getAmount()) {

				HashMap<String, Object> isTransfer = service.transBetweenTwoAcUsingApp(fromAccount, toAccount,
						transfer);

				if (!isTransfer.isEmpty()) {

					hm.put("4-Digit OTP sent via SMS on your phone " + isTransfer.get("MobileNumber"),
							isTransfer.get("OTP"));
					return new ResponseEntity<HashMap<String, Object>>(hm, HttpStatus.OK);

				} else {
					service.transactionStatementByApp(fromAccount, transfer.getAmount(), "debit", "fail", toAccount);
					throw new TransactonFailException("Transaction failed");
				}

			} else {
				service.transactionStatementByApp(fromAccount, transfer.getAmount(), "debit", "fail", toAccount);
				throw new AccountBalanceLowException("Account Balance is to low", "Transaction Fail");
			}

		} else {
			throw new AccountDeatilsWrongException("Account details wrong", transfer.getToAccountNumber(),
					transfer.getToIfscCode(), "Transaction Fail");
		}

	}

	@PostMapping(value = "/enterOTPInAppForTransaction")
	public ResponseEntity<Boolean> enterOTPInAppForTransaction(@RequestParam int otp) {

		boolean isUpdated = service.enterOTPInAppForTransaction(otp);

		if (isUpdated) {
			return new ResponseEntity<Boolean>(isUpdated, HttpStatus.OK);

		} else {
			throw new WrongOTPEnterException("Transaction Fail");
		}

	}

	@PostMapping(value = "/paymentUsingDebitCard")
	public ResponseEntity<Map<String, Integer>> paymentUsingDebitCard(@RequestBody DebitCard card,
			@RequestParam double amount) {
		HashMap<String, Integer> hm = new HashMap<>();
		int otp = service.paymentUsingDebitCard(card, amount);

		if (otp != 0) {
			hm.put("OTP", otp);
			return new ResponseEntity<>(hm, HttpStatus.OK);
		} else {
			throw new DebitCardDetailsWrongException("Debit card details wrong");
		}
	}

	@GetMapping(value = "/enterOtpPaymentUsingDebitCard")
	public ResponseEntity<Boolean> enterOtpPaymentUsingDebitCard(@RequestParam int otp) {

		boolean isCompleted = service.enterOtpPaymentUsingDebitCard(otp);

		if (isCompleted) {
			return new ResponseEntity<Boolean>(isCompleted, HttpStatus.OK);
		} else {
			throw new PaymentFailedException("Payment failed");
		}
	}

	@PostMapping(value = "withdrawMoneyUsingATMCard")
	public ResponseEntity<Boolean> withdrawMoneyUsingATMCard(@RequestBody ATMCard atmCard) {

		DebitCard card = service.getDebitCardDetailsByDebitCard(atmCard.getDebitCardNumber());
		BankAccount account = service.getAccountDetailsByAccountNumber(card.getAccountNumber());

		if (card.getPinNumber() == atmCard.getPinNumber()) {

			if (CustomClass.checkExpiryDate(card.getCardExpiryDate())) {

				if (atmCard.getAmount() <= 10000) {

					if (account.getBalance() - 1000 >= atmCard.getAmount()) {

						boolean isWithdraw = service.withdrawMoneyUsingATMCard(account, atmCard.getAmount());

						if (isWithdraw) {
							return new ResponseEntity<Boolean>(isWithdraw, HttpStatus.OK);
						} else {
							throw new WithdrawFailedException("Fail to withdraw money.");
						}

					} else {
						throw new AccountBalanceLowException("Account Balance is to low", "Transaction Fail");
					}

				} else {
					throw new ATMCardWithdrawLimitException("ATM card Limit is 10000.");
				}

			} else {
				throw new ATMCardExpiredException("ATM Card Expired");
			}

		} else {
			throw new ATMCardPinWrongException("Wrong pin number.");
		}

	}

	@PostMapping(value = "/updateATMPinUsingOldPin")
	public ResponseEntity<HashMap<String, Object>> updateATMPinUsingOldPin(@RequestParam long debitCardNumber,
			@RequestParam int oldPin, @RequestParam int newPin) {

		HashMap<String, Object> hm = new HashMap<>();

		HashMap<String, Object> serviceHm = service.updateATMPinUsingOldPin(debitCardNumber, oldPin, newPin);
		System.out.println(serviceHm);

		if (!serviceHm.isEmpty()) {
			hm.put("4-Digit OTP sent via SMS on your phone " + serviceHm.get("MobileNumber"), serviceHm.get("OTP"));
			return new ResponseEntity<HashMap<String, Object>>(hm, HttpStatus.OK);
		} else {
			throw new ATMOldPinWrongException("ATM old pin wrong");
		}

	}

	@PostMapping(value = "/enterOTPForupdateATMPin")
	public ResponseEntity<Boolean> enterOTPForupdateATMPin(@RequestParam int otp) {

		boolean isUpdated = service.enterOTPForupdateATMPin(otp);

		if (isUpdated) {
			return new ResponseEntity<Boolean>(isUpdated, HttpStatus.OK);

		} else {
			throw new WrongOTPEnterException("Failed to update ATM pin");
		}

	}

	@PostMapping(value = "/changeATMPinUsingAccountNumber")
	public ResponseEntity<HashMap<String, Object>> changeATMPinUsingAccountNumber(@RequestParam long debitCardNumber,
			@RequestParam long accountNumber, @RequestParam int newPin) {
		HashMap<String, Object> hm = new HashMap<>();

		HashMap<String, Object> serviceHm = service.changeATMPinUsingAccountNumber(debitCardNumber, accountNumber,
				newPin);
		System.out.println(serviceHm);

		if (!serviceHm.isEmpty()) {
			hm.put("4-Digit OTP sent via SMS on your phone " + serviceHm.get("MobileNumber"), serviceHm.get("OTP"));
			return new ResponseEntity<HashMap<String, Object>>(hm, HttpStatus.OK);
		} else {
			throw new AccountNumberWrongException("Account number wrong");
		}

	}

	@PostMapping(value = "/cashDepositeInAccount")
	public ResponseEntity<Boolean> cashDepositeInAccount(@RequestParam long accountNumber,
			@RequestParam double amount) {

		BankAccount account = service.getAccountDetailsByAccountNumber(accountNumber);

		if (account != null && amount !=0) {

			boolean isDeposited = service.cashDepositeInAccount(account, amount);

			if (isDeposited) {
				return new ResponseEntity<Boolean>(isDeposited, HttpStatus.OK);
			} else {
				throw new ServerErrorException("Server error");
			}

		} else {
			if(amount == 0) {
				throw new DepositAmountZeroException("Enter deposite amount");
			}
			throw new AccountNumberWrongException("Account number wrong");
		}

	}
	
	
	@DeleteMapping(value = "/deleteBankAccount/{accountNumber}")
	public ResponseEntity<Boolean> deleteBankAccount(@PathVariable long accountNumber){
		
		System.out.println(accountNumber);
		boolean isDeleted=service.deleteBankAccount(accountNumber);
		
		
		if(isDeleted) {
			return new ResponseEntity<Boolean>(isDeleted, HttpStatus.OK);
		}else {
			throw new AccountNumberWrongException("Account number wrong");
		}
		
	}
	

}
