package com.travel.book.easy.travelbookeasy.api.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.travel.book.easy.travelbookeasy.db.model.HotelBook;
import com.travel.book.easy.travelbookeasy.util.TravelBookEasyApp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HotelBookingDto {
	
	private long id;

	private HotelRoomDto hotelRoom;

	private UserDto booker;

	private PaymentDto payment;

	private String status;

	public static HotelBookingDto of(HotelBook hotelBook) {
		return TravelBookEasyApp.ofNullable(hotelBook,
				hb -> HotelBookingDto.builder().id(hb.getId()).hotelRoom(HotelRoomDto.of(hb.getHotelRoom()))
						.booker(UserDto.of(hb.getUser())).payment(PaymentDto.of(hb.getPayment())).status(hb.getStatus())
						.build());
	}

	public static List<HotelBookingDto> of(List<HotelBook> hotelBooks) {
		return hotelBooks.stream().map(hotelBook -> HotelBookingDto.of(hotelBook)).collect(Collectors.toList());

	}

}
