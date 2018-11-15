package com.travel.book.easy.travelbookeasy.api.dto;

import java.math.BigDecimal;

import com.travel.book.easy.travelbookeasy.db.model.User;
import com.travel.book.easy.travelbookeasy.db.model.UserCompanyRaiting;
import com.travel.book.easy.travelbookeasy.util.TravelBookEasyApp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRaitingDto {

	private UserDto user;
	
	private BigDecimal raiting;

	public UserRaitingDto() {
		super();
	}

	public UserRaitingDto(UserDto user, BigDecimal raiting) {
		super();
		this.user = user;
		this.raiting = raiting;
	}
	
	public static UserRaitingDto of (UserCompanyRaiting userCompanyRaiting) {
		return TravelBookEasyApp.ofNullable(userCompanyRaiting, ucr->UserRaitingDto.builder()
				.user(UserDto.of(ucr.getId().getUser()))
				.raiting(ucr.getRaiting())
				.build());
	}
	
	
}
