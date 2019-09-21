package com.billing.view.company;

import java.net.URL;
import java.util.ResourceBundle;

import com.billing.model.Company;
import com.billing.service.CompanyService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CompanyController implements Initializable{
	

	@FXML TextField nameId;
	@FXML TextField panNoId;
	@FXML TextField bankNameId;
	@FXML TextField bankAcNoId;
	@FXML TextField bankIfscId;

	@FXML Label msg;

	private CompanyService comServ;
	

	public void update(){
		
		  Company company = comServ.getByNameAndPanNo(nameId.getText(), panNoId.getText());
		  
		  company.setName(nameId.getText());
		  company.setPanNo(panNoId.getText());
		  company.setBankName(bankNameId.getText());
		  company.setBankAcNo(bankAcNoId.getText());
		  company.setBankIfsc(bankIfscId.getText());
		  
		  comServ.update(company); 
		  
		  msg.setVisible(true);
		 
	}

	public void setCompanyName(String name){
		msg.setVisible(false);
		this.nameId.setText(name);
		Company company = comServ.getByName(nameId.getText());
		if(company != null){
			this.nameId.setText(company.getName());
			this.panNoId.setText(company.getPanNo());
			this.bankNameId.setText(company.getBankName());
			this.bankAcNoId.setText(company.getBankAcNo());
			this.bankIfscId.setText(company.getBankIfsc());
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		msg.setVisible(false);
		comServ = CompanyService.getInstance();
	}

}
