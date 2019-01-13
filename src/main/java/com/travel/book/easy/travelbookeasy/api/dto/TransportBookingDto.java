package com.travel.book.easy.travelbookeasy.api.dto;

import java.util.ArrayList;
import java.util.List;

import com.travel.book.easy.travelbookeasy.db.model.FlightBook;
import com.travel.book.easy.travelbookeasy.util.TravelBookEasyApp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransportBookingDto <T> {
	
	private long id;

	private T transport;

	private UserDto booker;

	private PaymentDto payment;

	private String status;

	private List<PassengerTicketDto> passengerTickets;

	public static TransportBookingDto<FlightDto> of(FlightBook flightBook) {
		return TravelBookEasyApp.ofNullable(flightBook,
				fb -> TransportBookingDto.<FlightDto>builder().id(fb.getId())
				.transport(FlightDto.of(fb.getFlight()))
						.booker(UserDto.of(fb.getBooker())).payment(PaymentDto.of(fb.getPayment()))
						.passengerTickets(fb.getPassengerTickets()!=null ? PassengerTicketDto.of(fb.getPassengerTickets()):new ArrayList<>()).status(fb.getStatus())
						.build());

	}

}
