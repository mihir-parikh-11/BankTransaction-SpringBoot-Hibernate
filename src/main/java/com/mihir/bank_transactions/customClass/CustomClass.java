package com.mihir.bank_transactions.customClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class CustomClass {

	public static String generateExpiryDate(Date date) {

		String expDate = new SimpleDateFormat("MM-yyyy").format(date);

		String[] expDates = expDate.split("-");

		int expdate = Integer.parseInt(expDates[expDates.length - 1]) + 10;

		expDates[expDates.length - 1] = String.valueOf(expdate);

		String expiryDate = expDates[0] + "-" + expDates[1];

		return expiryDate;

	}

	public static int generateCvvNumber() {

		String randomNumber = new SimpleDateFormat("SSS").format(new Date());
		int number = Integer.parseInt(randomNumber);

		return number;

	}

	public static long generateNewDebitCardNumber() {

		int number1 = ThreadLocalRandom.current().nextInt(999, 9999 + 1);
		int number2 = ThreadLocalRandom.current().nextInt(999, 9999 + 1);
		int number3 = ThreadLocalRandom.current().nextInt(999, 9999 + 1);

		String string = number1 + "" + number2 + "" + number3;

		long cardnumber = Long.parseLong(string);

		return cardnumber;
	}

	public static int getneratePinNumber() {

		int pin = ThreadLocalRandom.current().nextInt(999, 9999 + 1);

		return pin;

	}

	public static long geerateTransactionId() {

		try {
			Thread.sleep(1);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String id = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		long transactionId = Long.parseLong(id);

		return transactionId;

	}
	
	public static boolean checkExpiryDate(String expirydate) {
		
		String currentDate = new SimpleDateFormat("MM-yyy").format(new Date());
		
		
		String[] currentDates=currentDate.split("-");
		String[] expirydates=expirydate.split("-");

		if(Integer.parseInt(currentDates[1])<=Integer.parseInt(expirydates[1])) {
			if(Integer.parseInt(currentDates[1])==Integer.parseInt(expirydates[1])) {
				if(Integer.parseInt(currentDates[0])<=Integer.parseInt(expirydates[0])) {
					return true;
				}else {
					return false;
				}
			}else {
				return true;
			}
		}else {
			return false;
		}
		
	}

}
