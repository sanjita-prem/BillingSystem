package com.billing.model;


public class BillDetail {

	private int billDetailId;
	//@Column(name="receipt_id")
	private String receiptId;

	private String description;
	private double rate;
	private int qty;
	//@Column(name="disc_val")
	private String discVal;
	//@Column(name="disc_amt")
	private double discAmt;
	//@Column(name="total_amt")
	private double totalAmt;
	//@Column(name="bill_date")
	private String billDate;
	//@Column(name="hsn_code")
	private String hsnCode;
	private String per;


	public String getPer() {
		return per;
	}
	public void setPer(String per) {
		this.per = per;
	}
	public String getHsnCode() {
		return hsnCode;
	}
	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}
	public String getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public String getDiscVal() {
		return discVal;
	}
	public void setDiscVal(String discType) {
		this.discVal = discType;
	}
	public double getDiscAmt() {
		return discAmt;
	}
	public void setDiscAmt(double discAmt) {
		this.discAmt = discAmt;
	}
	public double getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(double totalAmt) {
		this.totalAmt = totalAmt;
	}
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public int getBillDetailId() {
		return billDetailId;
	}
	public void setBillDetailId(int billDetailId) {
		this.billDetailId = billDetailId;
	}
	@Override
	public String toString() {
		return "BillDetail [billDetailId=" + billDetailId + ", receiptId=" + receiptId + ", description=" + description
				+ ", rate=" + rate + ", qty=" + qty + ", discVal=" + discVal + ", discAmt=" + discAmt + ", totalAmt="
				+ totalAmt + ", billDate=" + billDate + ", hsnCode=" + hsnCode + ", per=" + per + "]";
	}

}
