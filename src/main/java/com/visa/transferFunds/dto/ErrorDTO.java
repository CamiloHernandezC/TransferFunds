package com.visa.transferFunds.dto;

import java.util.Objects;

import com.visa.transferFunds.enums.Error;

public final class ErrorDTO {

	private final String errorCode;
	private final String message;
	
	public ErrorDTO(Error error) {
		this.errorCode = error.getErrorCode();
		this.message = error.getMessage();
	}

	public String getErrorCode() {return errorCode;}

	public String getMessage() {return message;}

	@Override
	public int hashCode() {
		return Objects.hash(errorCode, message);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ErrorDTO other = (ErrorDTO) obj;
		return Objects.equals(errorCode, other.errorCode) && Objects.equals(message, other.message);
	}
	
}
