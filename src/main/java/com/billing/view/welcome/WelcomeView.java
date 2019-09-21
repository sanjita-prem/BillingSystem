package com.billing.view.welcome;

import com.billing.util.AbstractFxmlView;

public class WelcomeView extends AbstractFxmlView{
	private static final WelcomeView INSTANCE = new WelcomeView();

	private WelcomeView(){}

	public static WelcomeView getInstance(){
		return INSTANCE;
	}
}
