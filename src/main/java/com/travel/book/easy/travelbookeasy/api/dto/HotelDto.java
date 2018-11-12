package com.travel.book.easy.travelbookeasy.api.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.travel.book.easy.travelbookeasy.db.model.Hotel;
import com.travel.book.easy.travelbookeasy.util.TravelBookEasyApp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HotelDto {

	
	private long id;
	
	private String name;
	
	private LocationDto location;
	
	private String description;
	
	private List<PictureDto> hotelPictures;
	
	private List<HotelRoomDto> hotelRooms;

	public HotelDto() {
		super();
	}
	
	public HotelDto(long id, String name, LocationDto location, String description, List<PictureDto> hotelPictures,
			List<HotelRoomDto> hotelRooms) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.description = description;
		this.hotelPictures = hotelPictures;
		this.hotelRooms = hotelRooms;
	}

	public static HotelDto of(Hotel hotel) {
		return TravelBookEasyApp.ofNullable(hotel, h->HotelDto.builder()
				.id(h.getId())
				.name(h.getHotelName())
				.location(LocationDto.of(h.getLocation()))
				.description(h.getDescription())
				.hotelPictures(h.getHotelPictures() != null ? h.getHotelPictures().stream()
						.map(picture -> PictureDto.of(picture)).collect(Collectors.toList()) : new ArrayList<>())
				.hotelRooms(h.getHotelRooms().stream().map(hr->HotelRoomDto.of(hr)).collect(Collectors.toList()))
				.build());
	}
	
}
