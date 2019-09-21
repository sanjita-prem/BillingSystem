package com.billing.view.bill;

import com.billing.util.AbstractFxmlView;

public class BillmanageView extends AbstractFxmlView{
	private static final BillmanageView INSTANCE = new BillmanageView();

	private BillmanageView(){}

	public static BillmanageView getInstance(){
		return INSTANCE;
	}
}
