package com.billing.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DbUtil {

	private static Connection dbConnection = null;

	public static Connection getConnection() {
		if (dbConnection != null) {
			return dbConnection;
		} else {
			try(InputStream inputStream = DbUtil.class.getClassLoader().getResourceAsStream("application.properties"))
			{
				Properties properties = new Properties();
				properties.load(inputStream);

				String dbDriver = StringPropertyReplacer.replaceProperties(properties.getProperty("db.driver"));
				Class.forName(dbDriver);
				String connectionUrl = StringPropertyReplacer.replaceProperties(properties.getProperty("connection.url"));
				dbConnection = DriverManager.getConnection(connectionUrl);				

			} catch (Exception e) {
				e.printStackTrace();
			}
			return dbConnection;
		}
	}

	/*
	 * private static Driver getJdbcDriver(Properties properties) throws
	 * ClassNotFoundException{ if(driver == null){ ExtensionLoader<Driver> loader =
	 * new ExtensionLoader<Driver>(); driver =
	 * loader.LoadClass(properties.getProperty("lib.db.path"),
	 * properties.getProperty("db.driver"), Driver.class); } return driver; }
	 */

}
