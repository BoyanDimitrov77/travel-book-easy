package com.travel.book.easy.travelbookeasy.api.dto;

import java.math.BigDecimal;

import com.travel.book.easy.travelbookeasy.db.model.UserCompanyRating;
import com.travel.book.easy.travelbookeasy.util.TravelBookEasyApp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRatingDto {

	private UserDto user;
	
	private BigDecimal rating;

	public UserRatingDto() {
		super();
	}

	public UserRatingDto(UserDto user, BigDecimal rating) {
		super();
		this.user = user;
		this.rating = rating;
	}
	
	public static UserRatingDto of (UserCompanyRating userCompanyRaiting) {
		return TravelBookEasyApp.ofNullable(userCompanyRaiting, ucr->UserRatingDto.builder()
				.user(UserDto.of(ucr.getId().getUser()))
				.rating(ucr.getRating())
				.build());
	}
	
	
}
