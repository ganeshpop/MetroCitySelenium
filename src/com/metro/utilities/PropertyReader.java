package com.metro.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
	public static FileInputStream inputStream = null;
	public static Properties prop = null;

	public static String ReadProperty(String PropertyName) throws IOException{
		String PropertyValue;
		inputStream = new FileInputStream(System.getProperty("user.dir")+"/src/com/metro/config/config.properties");
		prop = new Properties();
		prop.load(inputStream);
		PropertyValue = prop.getProperty(PropertyName);
		return PropertyValue;
	}

	public static void main(String[] args) throws IOException{
		System.out.println(PropertyReader.ReadProperty("browser"));
		System.out.println(PropertyReader.ReadProperty("app_url"));
		System.out.println(PropertyReader.ReadProperty("chrome_driver_path"));
	}

}
