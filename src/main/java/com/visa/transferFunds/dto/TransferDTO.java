package com.visa.transferFunds.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonAlias;

//import javax.validation.constraints.Min;
//import javax.validation.constraints.NotNull;

import com.visa.transferFunds.enums.Currency;

public final class TransferDTO {
	
	//@NotNull @Min(1)
	private final float amount;
	//@NotNull
	private final Currency currency;
	//@NotNull @Min(1)
	@JsonAlias("origin_account")
	private final long originAccount;
	//@NotNull @Min(1)
	@JsonAlias("destination_account")
	private final long destinationAccount;
	private final String description;

	public TransferDTO(final float amount, final Currency currency, final long originAccount, final long destinationAccount, final String description) {
		super();
		this.amount = amount;
		this.currency = currency;
		this.originAccount = originAccount;
		this.destinationAccount = destinationAccount;
		this.description = description;
	}

	public float getAmount() 			{	return amount;	}

	public Currency getCurrency() 		{	return currency;	}

	public long getOriginAccount() 		{	return originAccount;	}

	public long getDestinationAccount() 	{	return destinationAccount;	}

	public String getDescription() 		{	return description;	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, currency, description, destinationAccount, originAccount);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransferDTO other = (TransferDTO) obj;
		return Float.floatToIntBits(amount) == Float.floatToIntBits(other.amount) && currency == other.currency
				&& Objects.equals(description, other.description) && destinationAccount == other.destinationAccount
				&& originAccount == other.originAccount;
	}
	
}
