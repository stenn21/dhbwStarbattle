package com.starbattle.client.resource;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;

public class ClientConfiguration {

	private static Properties properties;
	private static URL path = ClientConfiguration.class
			.getResource("/com/starbattle/client/resource/config/client.properties");


	public static void loadConfiguration() {
		properties = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(path.getPath());
			// load a properties file
			properties.load(input);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static Properties get() {
		return properties;
	}

	public static void saveProperties() {
		OutputStream output = null;
		try {
			output = new FileOutputStream(path.getPath());

			// save properties to project root folder
			properties.store(output, null);
			
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

}
