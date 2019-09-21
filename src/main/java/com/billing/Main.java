package com.billing;

import com.billing.util.DBSystemConfig;
import com.billing.view.login.LoginView;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage stage) throws Exception {

		notifyPreloader(new Preloader.StateChangeNotification(Preloader.StateChangeNotification.Type.BEFORE_START));

		stage.setTitle("Billing System");
		stage.setScene(new Scene(LoginView.getInstance().getView()));
		stage.setResizable(false);
		stage.centerOnScreen();
		stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/green-icon.png")));
		stage.show();
	}

	public static void main(String[] args) {
		DBSystemConfig.copyDBFile();
		DBSystemConfig.copyFile("bill1.jrxml");
		launch(args);
	}

}
