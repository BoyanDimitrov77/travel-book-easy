package com.travel.book.easy.travelbookeasy.api.dto;

import com.travel.book.easy.travelbookeasy.db.model.Location;
import com.travel.book.easy.travelbookeasy.util.TravelBookEasyApp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationDto {

	private long id;

	private String name;

	public LocationDto() {
		super();
	}

	public LocationDto(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public static LocationDto of(Location location) {
		return TravelBookEasyApp.ofNullable(location,
				l -> LocationDto.builder().id(l.getId()).name(l.getName()).build());
	}
}
