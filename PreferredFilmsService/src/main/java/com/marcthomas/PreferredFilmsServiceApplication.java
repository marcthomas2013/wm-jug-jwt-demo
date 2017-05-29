package com.marcthomas;

import com.marcthomas.model.PublicCredentials;
import com.marcthomas.services.SecretService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class PreferredFilmsServiceApplication implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(PreferredFilmsServiceApplication.class);

	@Autowired
	private SecretService secretService;

	public static void main(String[] args) {
		SpringApplication.run(PreferredFilmsServiceApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		PublicCredentials authenicationServicePublicCredentials = restTemplate.getForObject("http://localhost:8080/get-public-key", PublicCredentials.class);
		if (authenicationServicePublicCredentials != null) {
			log.info("Adding public credentials for the authentication service");
			secretService.addPublicCreds(authenicationServicePublicCredentials);
		}
	}
}
