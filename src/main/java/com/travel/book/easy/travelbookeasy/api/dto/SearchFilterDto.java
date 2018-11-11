package com.travel.book.easy.travelbookeasy.api.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchFilterDto {

	private String locationFrom;

	private String locationTo;

	@JsonFormat(pattern = "dd-MM-yyyy", timezone = "UTC")
	private Date date;

	private Boolean sortByPrice;

	private Boolean sortByRating;

	public SearchFilterDto() {
		super();
	}

	public SearchFilterDto(String locationFrom, String locationTo, Date date, Boolean sortByPrice,
			Boolean sortByRating) {
		super();
		this.locationFrom = locationFrom;
		this.locationTo = locationTo;
		this.date = date;
		this.sortByPrice = sortByPrice;
		this.sortByRating = sortByRating;
	}

}
