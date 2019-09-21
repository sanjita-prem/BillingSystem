package com.billing.view.customer;

import com.billing.util.AbstractFxmlView;

public class CustomerView extends AbstractFxmlView{
	private static final CustomerView INSTANCE = new CustomerView();

	private CustomerView(){}

	public static CustomerView getInstance(){
		return INSTANCE;
	}
}
