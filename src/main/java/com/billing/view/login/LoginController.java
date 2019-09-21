package com.billing.view.login;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.billing.AppContext;
import com.billing.model.User;
import com.billing.service.UserService;
import com.billing.util.DateUtil;
import com.billing.view.welcome.WelcomeView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable{
	private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());
	@FXML
	private Label loginMsg;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private PasswordField confirmPassword;
	@FXML
	private Button loginButton;

	private UserService userService;

	public void login(ActionEvent e){
		loginMsg.setText("");
		if(username.getText().isEmpty() || password.getText().isEmpty()){
			loginMsg.setText("Username or Password required.");
			return;
		}
		User user = null;
		LOGGER.info("before login in USER : "+username.getText()+"/"+password.getText());
		user = userService.getUserByUserIdPassword(username.getText(), password.getText());
		if(user != null){
			LOGGER.info("logged in USER : "+username.getText()+"/"+password.getText());
			username.clear();
			password.clear();
			AppContext.setContext(AppContext.CURRENT_USER_NAME, user.getUserId());
			AppContext.setContext(AppContext.CURRENT_USER_ROLE, user.getRole());
			((Node)e.getSource()).getScene().getWindow().hide();
			Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			stage.setTitle("Welcome "+user.getUserId());
			stage.setScene(new Scene(WelcomeView.getInstance().getView()));
			stage.setResizable(true);
			stage.show();
		}else{
			loginMsg.setText("Login failed");
		}
	}
	
	public void register(ActionEvent e) {
		loginMsg.setText("");
		if(username.getText().isEmpty() || password.getText().isEmpty() || confirmPassword.getText().isEmpty()){
			loginMsg.setText("Please give fill all fields.");
			return;
		}else if(!password.getText().equals(confirmPassword.getText())) {
			loginMsg.setText("Passwords are not matching!!");
			return;
		}
		
		User user = new User();
		user.setUserId(username.getText());
		user.setPassword(password.getText());
		user.setActive(1);
		user.setCreated(DateUtil.getCurrentDateS());
		user.setRole(AppContext.ADMIN);
		
		user = userService.save(user);
		user = userService.getUserByUserIdPassword(user.getUserId(), user.getPassword());
		if(user != null){
			LOGGER.info("logged in USER : "+username.getText()+"/"+password.getText());
			username.clear();
			password.clear();
			AppContext.setContext(AppContext.CURRENT_USER_NAME, user.getUserId());
			AppContext.setContext(AppContext.CURRENT_USER_ROLE, user.getRole());
			((Node)e.getSource()).getScene().getWindow().hide();
			Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			stage.setTitle("Welcome "+user.getUserId());
			stage.setScene(new Scene(WelcomeView.getInstance().getView()));
			stage.setResizable(true);
			stage.show();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		userService = UserService.getInstance();
		AppContext.setContext(AppContext.BILL_LOCATION, "C:"+File.separator+"bills");
	}

}
