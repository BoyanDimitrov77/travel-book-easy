package com.travel.book.easy.travelbookeasy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import it.ozimov.springboot.mail.configuration.EnableEmailTools;


@SpringBootApplication
@EnableEmailTools
//@ComponentScan(basePackages = {"com.travel.book.easy.travelbookeasy"})
//(exclude = {SecurityAutoConfiguration.class })
public class TravelBookEasyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelBookEasyApplication.class, args);
	}
}
