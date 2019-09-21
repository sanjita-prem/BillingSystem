package com.billing.view.user;

import com.billing.util.AbstractFxmlView;

public class UserView extends AbstractFxmlView{
	private static final UserView INSTANCE = new UserView();

	private UserView(){}

	public static UserView getInstance(){
		return INSTANCE;
	}
}
