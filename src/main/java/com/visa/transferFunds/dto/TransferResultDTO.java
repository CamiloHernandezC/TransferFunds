package com.visa.transferFunds.dto;

import java.util.Objects;

import com.visa.transferFunds.enums.Currency;

public final class TransferResultDTO {

	private String id;
	private double taxCollected;
	private Currency currency;
	private double amount;
	
	public TransferResultDTO() {}
	
	
	public String getId() 			{return id;}
	public double getTaxCollected() {return taxCollected;}
	public Currency getCurrency() 	{return currency;}
	public double getAmount() 		{return amount;}


	public void setCurrency(Currency currency) 	{this.currency = currency;}
	public void setAmount(double amount) 		{this.amount = amount;}
	public void setId(final String id) 						{this.id = id;}
	public void setTaxCollected(final double taxCollected) 	{this.taxCollected = taxCollected;}


	@Override
	public int hashCode() {
		return Objects.hash(amount, currency, id, taxCollected);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransferResultDTO other = (TransferResultDTO) obj;
		return Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount) && currency == other.currency
				&& Objects.equals(id, other.id)
				&& Double.doubleToLongBits(taxCollected) == Double.doubleToLongBits(other.taxCollected);
	}
	
}
