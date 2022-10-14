package com.mihir.bank_transactions.controller;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mihir.bank_transactions.entity.User;
import com.mihir.bank_transactions.exception.ServerErrorException;
import com.mihir.bank_transactions.exception.UsernameAlreadyExistException;
import com.mihir.bank_transactions.exception.WrongOTPEnterException;
import com.mihir.bank_transactions.modelClass.LoginUser;
import com.mihir.bank_transactions.service.NetBankingService;

@RestController
public class NetBankingController {

	@Autowired
	NetBankingService netBankingService;

	@PostMapping(value = "/createNetBankingAccount")
	public ResponseEntity<HashMap<String, Object>> createNetBankingAccount(@Valid @RequestBody User user) {

		HashMap<String, Object> hm = null;
		User isExist = netBankingService.getUserDeatilsByUsername(user.getUserName());

		if (isExist == null) {

			HashMap<String, Object> servicemsg = netBankingService.saveUserDetailsBeforeVerify(user);

			if (servicemsg.size() > 1) {
				hm = new HashMap<>();
				hm.put("4-Digit OTP sent via SMS on your mobile number " + servicemsg.get("MobileNumber"),
						servicemsg.get("OTP"));
				return new ResponseEntity<HashMap<String, Object>>(hm, HttpStatus.OK);
			} else if (servicemsg.size() == 1) {
				hm = servicemsg;
				return new ResponseEntity<HashMap<String, Object>>(hm, HttpStatus.OK);
			} else {
				throw new ServerErrorException("Server error");
			}
		} else {
			throw new UsernameAlreadyExistException("Username already exist");
		}

	}

	@PostMapping(value = "/enterOTPForCreateUserAccout")
	public ResponseEntity<Boolean> enterOTPForCreateUserAccout(@RequestParam int otp) {

		if (otp != 0) {
			boolean isAdded = netBankingService.enterOTPForCreateUserAccout(otp);

			if (isAdded) {
				return new ResponseEntity<Boolean>(isAdded, HttpStatus.OK);
			} else {
				throw new WrongOTPEnterException("Wrong OTP entered");
			}
		} else {
			netBankingService.clearData();
			throw new WrongOTPEnterException("Wrong OTP entered");
		}

	}

	@PostMapping(value = "loginNetBanking")
	public ResponseEntity<Object> loginNetBanking(@RequestBody LoginUser user) {

		Object object = netBankingService.loginNetBanking(user);

		if (object instanceof Boolean) {
			boolean isLogin = (boolean) object;
			if (isLogin) {
				return new ResponseEntity<Object>(isLogin, HttpStatus.OK);
			} else {
				throw new ServerErrorException("Server error");
			}

		} else if (object instanceof HashMap) {
			return new ResponseEntity<Object>(object, HttpStatus.OK);
		} else {
			throw new ServerErrorException("Server error");
		}

	}

	@GetMapping(value = "/logOutNetBanking")
	public ResponseEntity<Boolean> logOutNetBanking() {

		netBankingService.clearData();

		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}

	@PostMapping(value = "changeUserPasswordUsingOldPassword")
	public ResponseEntity<Object> changeUserPasswordUsingOldPassword(@RequestParam String oldPassword,
			@RequestParam String newPassword) {

		Object object = netBankingService.changeUserPasswordUsingOldPassword(oldPassword, newPassword);

		if (object instanceof Boolean) {
			boolean isUpdated = (boolean) object;
			if (isUpdated) {
				return new ResponseEntity<Object>(isUpdated, HttpStatus.OK);
			} else {
				throw new ServerErrorException("Server error");
			}

		} else if (object instanceof HashMap) {
			return new ResponseEntity<Object>(object, HttpStatus.OK);
		} else {
			throw new ServerErrorException("Server error");
		}
	}

	@PostMapping(value = "/forgotUserPassword")
	public ResponseEntity<HashMap<String, Object>> forgotUserPassword(@RequestParam String userName) {
		HashMap<String, Object> hm = null;
		HashMap<String, Object> servicemsg = netBankingService.forgotUserPassword(userName);

		if (servicemsg.size() > 1) {
			hm = new HashMap<>();
			hm.put("4-Digit OTP sent via SMS on your mobile number " + servicemsg.get("MobileNumber"),
					servicemsg.get("OTP"));
			return new ResponseEntity<HashMap<String, Object>>(hm, HttpStatus.OK);
		} else if (servicemsg.size() == 1) {
			hm = servicemsg;
			return new ResponseEntity<>(hm, HttpStatus.OK);
		} else {
			throw new ServerErrorException("Server error");
		}

	}

	@PostMapping(value = "checkOTPForForgotPassword")
	public ResponseEntity<Boolean> checkOTPForForgotPassword(@RequestParam int otp) {

		boolean isTrue = netBankingService.checkOTPForForgotPassword(otp);

		if (isTrue) {
			return new ResponseEntity<>(isTrue, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(isTrue, HttpStatus.OK);
		}

	}

	@PostMapping(value = "/updateNewPassword")
	public ResponseEntity<Boolean> updateNewPassword(@RequestParam String newPassword) {

		boolean isUpdated = netBankingService.updateNewPassword(newPassword);

		if (isUpdated) {
			return new ResponseEntity<Boolean>(isUpdated, HttpStatus.OK);
		} else {
			throw new ServerErrorException("Server error");
		}

	}

	@PostMapping(value = "/transferMoneyUsingNetBanking")
	public ResponseEntity<Boolean> transferMoneyUsingNetBanking(@RequestParam long accountNumber,
			@RequestParam String IFSCCode, @RequestParam double amount) {

		boolean flag = netBankingService.transferMoneyUsingNetBanking(accountNumber, IFSCCode, amount);

		if (flag) {
			return new ResponseEntity<>(flag, HttpStatus.OK);
		} else {
			throw new ServerErrorException("Something went to wrong");
		}
	}

	@PostMapping(value = "/verifyNetBankingTransactionPin")
	public ResponseEntity<HashMap<String, Object>> verifyNetBankingTransactionPin(@RequestParam String transactionPin) {
		HashMap<String, Object> hm = null;

		HashMap<String, Object> serviceMsg = netBankingService.verifyNetBankingTransactionPin(transactionPin);

		if (serviceMsg.size() > 1) {
			hm = new HashMap<>();
			hm.put("4-Digit OTP sent via SMS on your mobile number " + serviceMsg.get("MobileNumber"),
					serviceMsg.get("OTP"));
			return new ResponseEntity<HashMap<String, Object>>(hm, HttpStatus.OK);

		} else if (serviceMsg.size() == 1) {
			hm = serviceMsg;
			return new ResponseEntity<HashMap<String, Object>>(hm, HttpStatus.OK);
		} else {
			throw new ServerErrorException("Something went to wrong");
		}

	}

	@PostMapping(value = "verifyOTPForTransaction")
	public ResponseEntity<Object> verifyOTPForTransaction(@RequestParam int otp) {

		Object object =netBankingService.verifyOTPForTransaction(otp);

		if (object instanceof Boolean) {

			if ((boolean) object) {
				return new ResponseEntity<Object>(object, HttpStatus.OK);
			} else {
				throw new ServerErrorException("Something went to wrong");
			}

		} else if (object instanceof HashMap) {
			return new ResponseEntity<Object>(object, HttpStatus.OK);
		} else {
			throw new ServerErrorException("Something went to wrong");
		}

	}

}
