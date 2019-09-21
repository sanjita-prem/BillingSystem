package com.billing.view.ower;

import com.billing.util.AbstractFxmlView;

public class OwnerView extends AbstractFxmlView{
	private static final OwnerView INSTANCE = new OwnerView();

	private OwnerView(){}

	public static OwnerView getInstance(){
		return INSTANCE;
	}
}
