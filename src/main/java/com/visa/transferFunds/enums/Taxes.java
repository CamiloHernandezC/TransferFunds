package com.visa.transferFunds.enums;

public enum Taxes {
	HIGH_TAX(0.05),
	LOW_TAX(0.02);
	
	public static final float LIMIT_AMOUNT = 100;

	private final double taxRate;
	
	Taxes(final double taxRate) {
		this.taxRate = taxRate;
	}
	
	public double taxRate() {return taxRate;}

	
}
