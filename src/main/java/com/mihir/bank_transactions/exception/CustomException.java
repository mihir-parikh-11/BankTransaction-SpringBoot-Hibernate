package com.mihir.bank_transactions.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomException {
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<HashMap<String, Object>> MissingServletRequestParameterException(MissingServletRequestParameterException exception){
		
		HashMap<String, Object> hm = new HashMap<>();

		hm.put("message", "Please enter request parameter value");

		return new ResponseEntity<HashMap<String, Object>>(hm, HttpStatus.OK);
		
	}
	

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<HashMap<String, Object>> methodArgumentNotValidException(
			MethodArgumentNotValidException exception) {

		HashMap<String, Object> hm = new HashMap<>();
		hm.put("Time", new Date());

		BindingResult bindingResult = exception.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		for (FieldError fieldError : fieldErrors) {
			hm.put(fieldError.getField(), fieldError.getDefaultMessage());
		}

		return new ResponseEntity<HashMap<String, Object>>(hm, HttpStatus.OK);
	}

	@ExceptionHandler(BankDetailsWrorngException.class)
	public ResponseEntity<HashMap<String, Object>> bankDetailsWrorngException(BankDetailsWrorngException exception) {

		HashMap<String, Object> hm = new HashMap<>();
		hm.put("Time", new Date());
		hm.put("message", exception.getMessage());
		hm.put("Account Deatils", exception.getAccount());

		return new ResponseEntity<HashMap<String, Object>>(hm, HttpStatus.OK);
	}

	@ExceptionHandler(BankAccountNotPresentException.class)
	public ResponseEntity<HashMap<String, Object>> bankAccountNotPresentException(
			BankAccountNotPresentException exception) {

		HashMap<String, Object> hm = new HashMap<>();
		hm.put("Time", new Date());
		hm.put("message", exception.getMessage());

		return new ResponseEntity<HashMap<String, Object>>(hm, HttpStatus.OK);
	}

	@ExceptionHandler(DebitCardNotPresentException.class)
	public ResponseEntity<HashMap<String, Object>> debitCardNotPresentException(
			DebitCardNotPresentException exception) {

		HashMap<String, Object> hm = new HashMap<>();
		hm.put("Time", new Date());

		hm.put("message", exception.getMessage());

		return new ResponseEntity<HashMap<String, Object>>(hm, HttpStatus.OK);
	}

	@ExceptionHandler(TransactionDetailsNotPresentException.class)
	public ResponseEntity<HashMap<String, Object>> transactionDetailsNotPresentException(
			TransactionDetailsNotPresentException exception) {

		HashMap<String, Object> hm = new HashMap<>();

		hm.put("Time", new Date());
		hm.put("message", exception.getMessage());

		return new ResponseEntity<HashMap<String, Object>>(hm, HttpStatus.OK);
	}

	@ExceptionHandler(AccountDeatilsWrongException.class)
	public ResponseEntity<HashMap<String, Object>> AccountDeatilsWrongException(
			AccountDeatilsWrongException exception) {

		HashMap<String, Object> hm = new HashMap<>();

		hm.put("Time", new Date());
		hm.put("message", exception.getMessage());
		hm.put("Account Number", exception.getAccountNumber());
		hm.put("IFSC Code", exception.getIfscCode());
		hm.put("Transaction Status", exception.getTransactionStatus());

		return new ResponseEntity<HashMap<String, Object>>(hm, HttpStatus.OK);
	}

	@ExceptionHandler(AccountBalanceLowException.class)
	public ResponseEntity<HashMap<String, Object>> accountBalanceLowException(AccountBalanceLowException exception) {
		HashMap<String, Object> hm = new HashMap<>();

		hm.put("Time", new Date());
		hm.put("message", exception.getMessage());
		hm.put("Transaction Status", exception.getTransactionStatus());

		return new ResponseEntity<HashMap<String, Object>>(hm, HttpStatus.OK);

	}

	@ExceptionHandler(TransactonFailException.class)
	public ResponseEntity<HashMap<String, Object>> transactonFailException(TransactonFailException exception) {
		HashMap<String, Object> hm = new HashMap<>();

		hm.put("Time", new Date());
		hm.put("message", exception.getMessage());

		return new ResponseEntity<HashMap<String, Object>>(hm, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(DebitCardDetailsWrongException.class)
	public ResponseEntity<HashMap<String, Object>> debitCardDetailsWrongException(
			DebitCardDetailsWrongException exception) {

		HashMap<String, Object> hm = new HashMap<>();
		hm.put("Time", new Date());
		hm.put("message", exception.getMessage());

		return new ResponseEntity<HashMap<String, Object>>(hm, HttpStatus.OK);
	}

	@ExceptionHandler(PaymentFailedException.class)
	public ResponseEntity<HashMap<String, Object>> paymentFailedException(PaymentFailedException exception) {

		HashMap<String, Object> hm = new HashMap<>();
		hm.put("Time", new Date());
		hm.put("message", exception.getMessage());

		return new ResponseEntity<HashMap<String, Object>>(hm, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ATMCardWithdrawLimitException.class)
	public ResponseEntity<HashMap<String, Object>> aTMCardWithdrawLimitException(
			ATMCardWithdrawLimitException exception) {

		HashMap<String, Object> hm = new HashMap<>();
		hm.put("Time", new Date());
		hm.put("message", exception.getMessage());

		return new ResponseEntity<HashMap<String, Object>>(hm, HttpStatus.OK);
	}

	@ExceptionHandler(ATMCardExpiredException.class)
	public ResponseEntity<HashMap<String, Object>> aTMCardExpiredException(ATMCardExpiredException exception) {

		HashMap<String, Object> hm = new HashMap<>();
		hm.put("Time", new Date());
		hm.put("message", exception.getMessage());

		return new ResponseEntity<HashMap<String, Object>>(hm, HttpStatus.OK);
	}

	@ExceptionHandler(ATMCardPinWrongException.class)
	public ResponseEntity<HashMap<String, Object>> aTMCardPinWrongException(ATMCardPinWrongException exception) {

		HashMap<String, Object> hm = new HashMap<>();
		hm.put("Time", new Date());
		hm.put("message", exception.getMessage());

		return new ResponseEntity<HashMap<String, Object>>(hm, HttpStatus.OK);
	}

	@ExceptionHandler(WithdrawFailedException.class)
	public ResponseEntity<HashMap<String, Object>> withdrawFailedException(WithdrawFailedException exception) {

		HashMap<String, Object> hm = new HashMap<>();
		hm.put("Time", new Date());
		hm.put("message", exception.getMessage());

		return new ResponseEntity<HashMap<String, Object>>(hm, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ATMOldPinWrongException.class)
	public ResponseEntity<HashMap<String, Object>> aTMOldPinWrongException(ATMOldPinWrongException exception) {

		HashMap<String, Object> hm = new HashMap<>();
		hm.put("Time", new Date());
		hm.put("message", exception.getMessage());

		return new ResponseEntity<HashMap<String, Object>>(hm, HttpStatus.OK);
	}

	@ExceptionHandler(WrongOTPEnterException.class)
	public ResponseEntity<HashMap<String, Object>> wrongOTPEnterException(WrongOTPEnterException exception) {

		HashMap<String, Object> hm = new HashMap<>();
		hm.put("Time", new Date());
		hm.put("message", exception.getMessage());

		return new ResponseEntity<HashMap<String, Object>>(hm, HttpStatus.OK);
	}

	@ExceptionHandler(AccountNumberWrongException.class)
	public ResponseEntity<HashMap<String, Object>> accountNumberWrongException(AccountNumberWrongException exception) {

		HashMap<String, Object> hm = new HashMap<>();
		hm.put("Time", new Date());
		hm.put("message", exception.getMessage());

		return new ResponseEntity<HashMap<String, Object>>(hm, HttpStatus.OK);
	}

	@ExceptionHandler(ServerErrorException.class)
	public ResponseEntity<HashMap<String, Object>> serverErrorException(ServerErrorException exception) {

		HashMap<String, Object> hm = new HashMap<>();
		hm.put("Time", new Date());
		hm.put("message", exception.getMessage());

		return new ResponseEntity<HashMap<String, Object>>(hm, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(DepositAmountZeroException.class)
	public ResponseEntity<HashMap<String, Object>> depositAmountZeroException(DepositAmountZeroException exception) {

		HashMap<String, Object> hm = new HashMap<>();
		hm.put("Time", new Date());
		hm.put("message", exception.getMessage());

		return new ResponseEntity<HashMap<String, Object>>(hm, HttpStatus.OK);
	}
	
	@ExceptionHandler(UsernameAlreadyExistException.class)
	public ResponseEntity<HashMap<String, Object>> usernameAlreadyExistException(UsernameAlreadyExistException exception) {

		HashMap<String, Object> hm = new HashMap<>();
		hm.put("Time", new Date());
		hm.put("message", exception.getMessage());

		return new ResponseEntity<HashMap<String, Object>>(hm, HttpStatus.OK);
	}

}
