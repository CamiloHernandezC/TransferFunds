package com.visa.transferFunds.entities;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.visa.transferFunds.utils.Constants;

@Entity
public final class Account {
	
	@Id
	private Long id;
	private double balance;
	private int remainingOperations;
	
	//Default constructor needed to initialize the database
	Account(){};
	
	public Account(final Long id,final  double balance,final  int remainingOperations) {
		super();
		this.id = id;
		this.balance = balance;
		this.remainingOperations = remainingOperations;
	}
	
	/**
	 * Create an account with remaining operations equal to Constants.MAX_OPERATIONS_PER_DAY
	 * @param id
	 * @param balance
	 */
	public Account(final Long id,final double balance) {
		this(id, balance, Constants.MAX_OPERATIONS_PER_DAY);
	}

	public Long getId() {return id;}

	public double getBalance() {return balance;}

	public int getRemainingOperations() {return remainingOperations;}
	
	public void setId(Long id) {this.id = id;}

	public void setBalance(double balance) {this.balance = balance;}

	public void setRemainingOperations(int remainingOperations) {this.remainingOperations = remainingOperations;}

	@Override
	public int hashCode() {
		return Objects.hash(balance, id, remainingOperations);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return Double.doubleToLongBits(balance) == Double.doubleToLongBits(other.balance)
				&& Objects.equals(id, other.id) && remainingOperations == other.remainingOperations;
	}
	
}
