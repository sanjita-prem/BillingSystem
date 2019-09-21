package com.billing.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class BillDetailBean {
	
	private final SimpleStringProperty rDescription;
	private final SimpleStringProperty rHSNCode;
	private final SimpleIntegerProperty rQTY;
	private final SimpleDoubleProperty rRate;
	private final SimpleStringProperty rPer;
	private final SimpleStringProperty rDiscVal;
	private final SimpleDoubleProperty rDiscAmt;
	private final SimpleDoubleProperty rAmount;
	
	public BillDetailBean(String rDescription, String rHSNCode, int rQTY,
			double rRate, String rPer, String rDiscVal,
			double rDiscAmt, double rAmount) {
		super();
		this.rDescription = new SimpleStringProperty(rDescription);
		this.rHSNCode = new SimpleStringProperty(rHSNCode);
		this.rQTY = new SimpleIntegerProperty(rQTY);
		this.rRate = new SimpleDoubleProperty(rRate);
		this.rPer = new SimpleStringProperty(rPer);
		this.rDiscVal = new SimpleStringProperty(rDiscVal);
		this.rDiscAmt = new SimpleDoubleProperty(rDiscAmt);
		this.rAmount = new SimpleDoubleProperty(rAmount);
	}

	public String getRDescription() {
		return rDescription.get();
	}
	public void setRDescription(String desc){
		this.rDescription.set(desc);
	}
	
	public String getRHSNCode() {
		return rHSNCode.get();
	}
	public void setRHSNCode(String hsnCode){
		this.rHSNCode.set(hsnCode);
	}

	public int getRQTY() {
		return rQTY.get();
	}
	public void setRQTY(int qty){
		this.rQTY.set(qty);
	}
	
	public double getRRate() {
		return rRate.get();
	}
	public void setRRate(double rate){
		this.rRate.set(rate);
	}

	public String getRPer() {
		return rPer.get();
	}
	public void setRPer(String per){
		this.rPer.set(per);
	}
	
	public String getRDiscVal() {
		return rDiscVal.get();
	}
	public void setRDiscVal(String discVal){
		this.rDiscVal.set(discVal);
	}

	public double getRDiscAmt() {
		return rDiscAmt.get();
	}
	public void setRDiscAmt(double discAmt){
		this.rDiscAmt.set(discAmt);
	}

	public double getRAmount() {
		return rAmount.get();
	}
	public void setRAmount(double amount){
		this.rAmount.set(amount);
	}

	@Override
	public String toString() {
		return "BillDetailBean [rDescription=" + rDescription.get() + ", rHSNCode=" + rHSNCode.get() + ", rQTY=" + rQTY.get() + ", rRate="
				+ rRate.get() + ", rPer=" + rPer.get() + ", rDiscVal=" + rDiscVal.get() + ", rDiscAmt=" + rDiscAmt.get() + ", rAmount="
				+ rAmount.get() + "]";
	}
	
}
