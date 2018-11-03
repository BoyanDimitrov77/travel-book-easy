package com.travel.book.easy.travelbookeasy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class TravelBookEasyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelBookEasyApplication.class, args);
	}
}
