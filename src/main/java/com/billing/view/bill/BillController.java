package com.billing.view.bill;

import java.net.URL;
import java.util.ResourceBundle;

import com.billing.model.Customer;
import com.billing.util.DateUtil;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

public class BillController extends AbstractBillController{


	@FXML TextField buyer;
	@FXML TextField buyerName;
	@FXML TextArea buyerAddress;
	@FXML TextField buyerPan;
	@FXML TextField buyerGst;
	@FXML TextField buyerCity;
	@FXML CheckBox autoSave;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize();
		configureBillDetailTable();
		configureOwner();

		custMap = custServ.getAllCustomerMap();

		//for buyer
		buyer.setOnKeyReleased(event -> {
			  if (event.getCode() == KeyCode.ENTER){
				  setBuyerInfo(buyer.getText());
				  }
				});
		buyer.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
            	setBuyerInfo(buyer.getText());
            }
        });

		buyer.focusedProperty().addListener(new ChangeListener<Boolean>(){
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
		        if (!newPropertyValue){
		        	setBuyerInfo(buyer.getText());
		        }
		    }
		});
		configurePer();
		populateCell();
		configureTransport();
		configureInputField();
		buyerAuto.addAutoCompleteOptions(buyer, custMap.keySet());

	}

	protected void setBuyerInfo(String aliasName){
		Customer c = custMap.get(aliasName);
		if(null != c){
			setCustomerId(c.getCustomerId());
			setCustomerGST(c.getGstNo());
			buyerName.setText(c.getName());
			buyerAddress.setText(c.getAddress());
			buyerPan.setText(c.getPanNo());
			buyerGst.setText(c.getGstNo());
			buyerCity.setText(c.getCity());
			autoSave.setSelected(false);
			autoSave.setDisable(true);
		}else{
			buyerGst.setEditable(true);
			autoSave.setSelected(true);
			clearBuyer();
		}
	}
	protected void clearBuyer(){
		autoSave.setDisable(false);
		buyerName.clear();
		buyerAddress.clear();
		buyerPan.clear();
		buyerGst.clear();
		buyerCity.clear();

	}

	public void onAdd(){
		setCustomerGST(buyerGst.getText());
		onAddButtonClick();
		if(autoSave.selectedProperty().getValue()
				&& (!buyer.getText().isEmpty() || !buyerName.getText().isEmpty()))
		{
			Customer c = custMap.get(buyer.getText());
			if(c == null){
				c = new Customer();
				c.setName(buyerName.getText());
				c.setAddress(buyerAddress.getText());
				c.setAliasName(buyer.getText().isEmpty() ? buyerName.getText() : buyer.getText());
				c.setPanNo(buyerPan.getText());
				c.setGstNo(buyerGst.getText());
				c.setCity(buyerCity.getText());
				c.setCreated(DateUtil.getCurrentDateS());
				c.setActive(1);
				c = custServ.save(c);
				custMap.put(c.getAliasName(), c);
				setCustomerId(c.getCustomerId());
				setCustomerGST(c.getGstNo());
				log.info("Auto Save Customer : "+ c);
			}
		}
	}
}
