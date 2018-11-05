package com.travel.book.easy.travelbookeasy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//(exclude = {SecurityAutoConfiguration.class })
public class TravelBookEasyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelBookEasyApplication.class, args);
	}
}
