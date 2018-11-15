package com.travel.book.easy.travelbookeasy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {
	
	private String storageLocation = "upload-directory";
	
	@Bean
	public String  getStorageLocation() {
		return storageLocation;
	}

}
