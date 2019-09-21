package com.billing.view.company;

import com.billing.util.AbstractFxmlView;

public class CompanyView extends AbstractFxmlView{
	private static final CompanyView INSTANCE = new CompanyView();

	private CompanyView(){}

	public static CompanyView getInstance(){
		return INSTANCE;
	}
}
