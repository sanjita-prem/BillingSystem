package com.billing.model;

//@Table(name="customer")
public class Customer {

	//@Column(name="customer_id")
	private int customerId;

	private String name;

	private String email;
	private int phone1;
	private int phone2;
	private String state;
	private String city;
	private String address;
	private int pin;
	private String created;
	//@Column(name="alias_name")
	private String aliasName;
	//@Column(name="pan_no")
	private String panNo;
	//@Column(name="gst_no")
	private String gstNo;
	private int active;

	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		if(phone1 == null || phone1.trim().isEmpty())
			this.phone1 = 0;
		else
			this.phone1 = Integer.parseInt(phone1);
	}
	public void setPhone1(int phone1) {
		this.phone1 = phone1;
	}
	public int getPhone2() {
		return phone2;
	}
	public void setPhone2(int phone2) {
		this.phone2 = phone2;
	}
	public void setPhone2(String phone2) {
		if(phone2 == null || phone2.trim().isEmpty())
			this.phone2 = 0;
		else
			this.phone2 = Integer.parseInt(phone2);
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String adress) {
		this.address = adress;
	}
	public int getPin() {
		return pin;
	}
	public void setPin(int pin) {
		this.pin = pin;
	}
	public void setPin(String pin) {
		if(pin == null || pin.trim().isEmpty())
			this.pin = 0;
		else
			this.pin = Integer.parseInt(pin);
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	public String getPanNo() {
		return panNo;
	}
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}
	public String getGstNo() {
		return gstNo;
	}
	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
}
