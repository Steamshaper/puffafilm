package org.steamshaper.ai.puffafilm.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {

	public static Properties getProperties(String file)
			throws FileNotFoundException, IOException {
		Properties propertiesFile = new Properties();
		propertiesFile.load(new FileInputStream(file));
		return propertiesFile;
	}

	public static String getProperty(String file, String property)
			throws FileNotFoundException, IOException {
		Properties propertyFile = getProperties(file);
		return propertyFile.getProperty(property);
	}

}
