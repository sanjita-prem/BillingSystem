package com.billing.view.login;

import java.io.IOException;

import com.billing.service.UserService;
import com.billing.util.AbstractFxmlView;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class LoginView extends AbstractFxmlView{
	private static final LoginView INSTANCE = new LoginView();
	
	private UserService userService;

	private LoginView(){
		userService = UserService.getInstance();
	}

	public static LoginView getInstance(){
		return INSTANCE;
	}
	@Override
	public Parent getView() {
		if(userService.getMaxUserId() == 0) {
			Parent root = null;
			try {
				root = FXMLLoader.load(getClass().getResource("register.fxml"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return root;
		}else
			return super.getView();
	}
}
