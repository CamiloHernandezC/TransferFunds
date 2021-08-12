package com.visa.transferFunds.consumingrest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Rates {
	
	//This properties are needed even if the attribute is called as the currency, for example CAD
	@JsonProperty("CAD")
	private double valueCAD;
	@JsonProperty("USD")
	private double valueUSD;
	
	public Rates() {}

	public double valueCAD() 					{return valueCAD;}
	public void setvalueCAD(double valueCAD) 	{this.valueCAD = valueCAD;}
	public double valueUSD() 					{return valueUSD;}
	public void setValueUSD(double valueUSD) 	{this.valueUSD = valueUSD;}
	
	@Override
	public String toString() {
		return "Rates [CAD=" + valueCAD + ", USD=" + valueUSD +"]";
	}
	
	
	
}