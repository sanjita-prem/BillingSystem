package com.billing.model;

//@Table(name="tax_rate")
public class TaxRate {
	//@Column(name="tax_id")
	private int taxId;

	private float sgst;
	private float cgst;
	private float igst;

	public float getSgst() {
		return sgst;
	}
	public void setSgst(float sgst) {
		this.sgst = sgst;
	}
	public float getCgst() {
		return cgst;
	}
	public void setCgst(float cgst) {
		this.cgst = cgst;
	}
	public float getIgst() {
		return igst;
	}
	public void setIgst(float igst) {
		this.igst = igst;
	}
	public int getTaxId() {
		return taxId;
	}
}
