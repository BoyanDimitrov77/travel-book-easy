package com.travel.book.easy.travelbookeasy.api.dto;

import java.math.BigDecimal;

import com.travel.book.easy.travelbookeasy.db.model.HotelRoom;
import com.travel.book.easy.travelbookeasy.util.TravelBookEasyApp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HotelRoomDto {

	private long id;
	
	private String typeRoom;
	
	private BigDecimal price;

	
	public HotelRoomDto() {
		super();
	}
	
	public HotelRoomDto(long id, String typeRoom, BigDecimal price) {
		super();
		this.id = id;
		this.typeRoom = typeRoom;
		this.price = price;
	}

	
	public static HotelRoomDto of(HotelRoom hotelRoom) {
		return TravelBookEasyApp.ofNullable(hotelRoom, hr->HotelRoomDto.builder()
				.id(hr.getId())
				.typeRoom(hr.getTypeRoom())
				.price(hr.getPrice())
				.build());
	}
	
	
}
