package com.visa.transferFunds.entities;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.visa.transferFunds.enums.Currency;

@Entity
public class TransferResult {

	@Id
	@GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
	private String id;
	private double taxCollected;
	@Enumerated(EnumType.STRING)
	private Currency currency;
	private double amount;
	
	public TransferResult() {}
	
	public TransferResult(double taxCollected, Currency currency, double amount) {
		this.taxCollected = taxCollected;
		this.currency = currency;
		this.amount = amount;
	}


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
		TransferResult other = (TransferResult) obj;
		return Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount) && currency == other.currency
				&& Objects.equals(id, other.id)
				&& Double.doubleToLongBits(taxCollected) == Double.doubleToLongBits(other.taxCollected);
	}
	
	
	
}
