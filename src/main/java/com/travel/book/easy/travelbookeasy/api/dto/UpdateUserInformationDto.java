package com.travel.book.easy.travelbookeasy.api.dto;

import com.travel.book.easy.travelbookeasy.db.model.User;
import com.travel.book.easy.travelbookeasy.util.TravelBookEasyApp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserInformationDto {

	private String fullName;

	private String email;

	public UpdateUserInformationDto() {
		super();
	}

	public UpdateUserInformationDto(String fullName, String email) {
		super();
		this.fullName = fullName;
		this.email = email;
	}
	
	public static UpdateUserInformationDto of(User user) {
		return TravelBookEasyApp.ofNullable(user, u -> UpdateUserInformationDto.builder()
				.fullName(u.getFullName())
				.email(u.getEmail())
				.build());
				
	}
}
