package com.visa.transferFunds.consumingrest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.visa.transferFunds.enums.Currency;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class FixerExchange {

	private boolean success;
	private Currency base;
	private Rates rates;
	
	public FixerExchange() {
		this.success = false;
		rates = new Rates();
	}
	
	public boolean isSuccess()	{return success;}
	public Currency getBase() 	{return base;}
	public Rates getRates() 	{return rates;}

	public void setSuccess(boolean success) {this.success = success;}
	public void setBase(Currency base) 		{this.base = base;}
	public void setRates(Rates rates) 		{this.rates = rates;}
	
	

	@Override
	public String toString() {
		return "FixerExchange [success=" + success + ", base=" + base + ", rates=" + rates.toString() + "]";
	}
	
}
