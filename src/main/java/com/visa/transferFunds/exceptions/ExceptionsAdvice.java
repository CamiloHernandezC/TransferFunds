package com.visa.transferFunds.exceptions;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.visa.transferFunds.dto.ErrorDTO;
import com.visa.transferFunds.enums.Error;

@ControllerAdvice
public class ExceptionsAdvice {
	
	@ResponseBody
	@ExceptionHandler(InsufficientFundsException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	ErrorDTO handleInsufficientFunds(InsufficientFundsException ex) {
		return new ErrorDTO(Error.INSUFICIENT_FUNDS);
	}
	
	@ResponseBody
    @ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
    private ErrorDTO handleEntityNotFound(EntityNotFoundException ex){
        return new ErrorDTO(Error.ACCOUNT_NOT_FOUND);
    }
	
	@ResponseBody
    @ExceptionHandler(TransfersPerDayExceededException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
    private ErrorDTO TransfersPerDayExceeded(TransfersPerDayExceededException ex){
        return new ErrorDTO(Error.TRANSFERS_PER_DAY_EXCEEDED);
    }
	
	
}
