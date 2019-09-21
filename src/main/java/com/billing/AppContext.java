package com.billing;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;

public interface AppContext {
	final static String SEP = File.separator;
	static final String APP_SYSTEM_PATH = System.getProperty("user.home")+SEP+"AppData"+SEP+"Local"+SEP+"Billing System"+SEP;
	//ROLE
	static final String NORMAL = "NORMAL",
			ADMIN = "ADMIN";
	//keys
	static final String CURRENT_USER_NAME = "currentUserName";
	static final String CURRENT_USER_ROLE = "currentUserRole";
	
	//bill location
	static final String BILL_LOCATION = "billLocation";
	
	static final Map<String, String> CONTEXT = new HashMap<>();
	
	public static String getValue(String key) {
		return CONTEXT.get(key);
	}
	
	public static String setContext(String key, String val) {
		return CONTEXT.put(key, val);
	}
	
	public static boolean isCurrentUserAdmin() {
		return ADMIN.equals(getValue(CURRENT_USER_ROLE));
	}
	
	public static void displayPermissionDialog() {
		Alert a = new Alert(AlertType.NONE); 
		a.setAlertType(AlertType.WARNING); 
		a.setHeaderText("Permission Denied");
		a.setContentText("You don't have permission");
        a.show(); 
	}
	
	public static void displayAlert(final String msg) {
		Alert alert = new Alert(AlertType.NONE, msg, ButtonType.OK);
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		alert.show();
		return;
	}
	
}
