package com.expeditors.dataapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class ExpeditorsDataappApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExpeditorsDataappApplication.class);

	public static void main(String[] args) {
		
		SpringApplication.run(ExpeditorsDataappApplication.class, args);
	}
}
