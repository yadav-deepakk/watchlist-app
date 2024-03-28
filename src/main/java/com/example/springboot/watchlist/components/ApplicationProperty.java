package com.example.springboot.watchlist.components;

import java.io.FileInputStream;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperty implements CommandLineRunner {

	@Autowired
	private Environment env;
	
	public String getOMDBURL() {
		String OMDB_URL = env.getProperty("omdb.url");
		System.out.println("OMDB_URL: " + OMDB_URL);
		return OMDB_URL; 
	}

	public String getOMDBAPIKey() {
		String OMDB_API_KEY = env.getProperty("omdb.api-key");
		System.out.println("OMDB_API_KEY: " + OMDB_API_KEY);
		return OMDB_API_KEY; 
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Printing all Application Propertes.");

		FileInputStream fis = new FileInputStream("src/main/resources/application.properties");
		Properties prop = new Properties();
		prop.load(fis);

		prop.forEach((propertyName, propertyVal) -> {
			System.out.println(propertyName + " => " + propertyVal);
		});
	}
}