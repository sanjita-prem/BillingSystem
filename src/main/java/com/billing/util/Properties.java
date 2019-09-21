package com.billing.util;

import java.io.FileInputStream;
import java.io.InputStream;

public class Properties {
	private static java.util.Properties prop = new java.util.Properties();
	static{
		try {
			InputStream input = new FileInputStream("resorces/application.properties");
			prop.load(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String get(String key){
		return prop.getProperty(key);
	}
}
