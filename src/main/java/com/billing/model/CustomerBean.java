package com.billing.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class CustomerBean {
	
	private final SimpleStringProperty rName;
	private final SimpleStringProperty rEmail;
	private final SimpleLongProperty rPhone1;
	private final SimpleLongProperty rPhone2;
	private final SimpleStringProperty rState;
	private final SimpleStringProperty rCity;
	private final SimpleStringProperty rAdress;
	private final SimpleIntegerProperty rPin;
	private final SimpleStringProperty rCreated;
	private final SimpleStringProperty rAliasName;
	private final SimpleStringProperty rPanNo;
	private final SimpleStringProperty rGstNo;
	private final SimpleIntegerProperty rActive;
	
	public CustomerBean(String name, String email, long phone1, long phone2, String state, String city, String adress,
			int pin, String created, String aliasName, String panNo, String gstNo, int active) {
		super();
		//this.rNo = new SimpleIntegerProperty(count++);
		this.rName= new SimpleStringProperty(name);
		this.rEmail= new SimpleStringProperty(email);
		this.rPhone1= new SimpleLongProperty(phone1);
		this.rPhone2= new SimpleLongProperty(phone2);
		this.rState= new SimpleStringProperty(state);
		this.rCity= new SimpleStringProperty(city);
		this.rAdress= new SimpleStringProperty(adress);
		this.rPin= new SimpleIntegerProperty(pin);
		this.rCreated= new SimpleStringProperty(created);
		this.rAliasName= new SimpleStringProperty(aliasName);
		this.rPanNo= new SimpleStringProperty(panNo);
		this.rGstNo= new SimpleStringProperty(gstNo);
		this.rActive= new SimpleIntegerProperty(active);
	}
	
	/*public int getRNo(){
		return rNo.get();
	}
	public void setRNo(int n){
		rNo.set(n);
	}*/
	public String getRName() {
		return rName.get();
	}
	public void setRName(String name) {
		rName.set(name);
	}
	public String getREmail() {
		return rEmail.get();
	}
	public void setREmail(String email) {
		this.rEmail.set(email);
	}
	public long getRPhone1() {
		return rPhone1.get();
	}
	public void setRPhone1(long phone1) {
		this.rPhone1.set(phone1);
	}
	public long getRPhone2() {
		return rPhone2.get();
	}
	public void setRPhone2(long phone2) {
		this.rPhone2.set(phone2);
	}
	public String getRState() {
		return rState.get();
	}
	public void setRState(String state) {
		this.rState.set(state);
	}
	public String getRCity() {
		return rCity.get();
	}
	public void setRCity(String city) {
		this.rCity.set(city);
	}
	public String getRAdress() {
		return rAdress.get();
	}
	public void setRAdress(String adress) {
		this.rAdress.get();
	}
	public int getRPin() {
		return rPin.get();
	}
	public void setPin(int pin) {
		this.rPin.set(pin);
	}
	public String getRCreated() {
		return rCreated.get();
	}
	public void setCreated(String created) {
		this.rCreated.set(created);;
	}
	public String getRAliasName() {
		return rAliasName.get();
	}
	public void setRAliasName(String aliasName) {
		this.rAliasName.set(aliasName);;
	}
	public String getRPanNo() {
		return rPanNo.get();
	}
	public void setRPanNo(String panNo) {
		this.rPanNo.set(panNo);
	}
	public String getRGstNo() {
		return rGstNo.get();
	}
	public void setRGstNo(String gstNo) {
		this.rGstNo.set(gstNo);
	}
	public int getRActive() {
		return rActive.get();
	}
	public void setRActive(int active) {
		this.rActive.set(active);
	}
}
