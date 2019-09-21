package com.billing.view.welcome;

import java.net.URL;
import java.util.ResourceBundle;

import com.billing.AppContext;
import com.billing.util.AbstractFxmlView;
import com.billing.view.bill.BillView;
import com.billing.view.bill.BillmanageView;
import com.billing.view.customer.CustomerView;
import com.billing.view.ower.OwnerView;
import com.billing.view.user.UserView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;


public class WelcomeController implements Initializable{

	@FXML private BorderPane mainPane;
	private UserView userView = UserView.getInstance();
	private OwnerView ownerView = OwnerView.getInstance();
	private CustomerView customerView = CustomerView.getInstance();
	private BillView billView = BillView.getInstance();
	private BillmanageView billManageView = BillmanageView.getInstance();

	public void manageUserLogin(ActionEvent e){
		if(AppContext.isCurrentUserAdmin())
			renderView(userView);
		else {
			AppContext.displayPermissionDialog();
		}
	}

	public void manageCompanyOwner(){
		renderView(ownerView);
	}

	public void manageCustomer(){
		renderView(customerView);
	}
	public void billGen(){
		renderView(billView);
	}
	public void billManage(){
		renderView(billManageView);
	}


	private void renderView(AbstractFxmlView view){
		Parent parent = view.getView();//null;
		if(view instanceof BillView
				|| view instanceof CustomerView
				|| view instanceof BillmanageView){
			parent = view.loadAndgetView();
		}else{
			parent = view.getView();
		}
		mainPane.setCenter(parent);
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Parent parent = BillView.getInstance().getView();
		mainPane.setCenter(parent);
	}
	
	public void onClose() {
		System.exit(0);
	}
}
