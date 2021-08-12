package com.visa.transferFunds.enums;

public enum Error {

	INSUFICIENT_FUNDS("000456", "insufficient-funds"),
	TRANSFERS_PER_DAY_EXCEEDED("000457", "transfer per day exceeded"),
	ACCOUNT_NOT_FOUND("000458", "Account not found");
	
	private final String errorCode;
	private final String message;

	Error(final String errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public String getErrorCode(){return errorCode;}

	public String getMessage() 	{return message;}
}
