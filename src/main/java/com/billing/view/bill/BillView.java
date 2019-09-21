package com.billing.view.bill;

import com.billing.util.AbstractFxmlView;

public class BillView extends AbstractFxmlView{
	private static final BillView INSTANCE = new BillView();

	private BillView(){}

	public static BillView getInstance(){
		return INSTANCE;
	}
}
