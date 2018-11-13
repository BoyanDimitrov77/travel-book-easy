package com.travel.book.easy.travelbookeasy.api.dto;

import java.util.ArrayList;
import java.util.List;

import com.travel.book.easy.travelbookeasy.db.model.BusBook;
import com.travel.book.easy.travelbookeasy.db.model.FlightBook;
import com.travel.book.easy.travelbookeasy.db.model.TrainBook;
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
	
	public static TransportBookingDto<BusDto> of(BusBook busBook) {
		return TravelBookEasyApp.ofNullable(busBook,
				b -> TransportBookingDto.<BusDto>builder().id(b.getId())
				.transport(BusDto.of(b.getBus()))
						.booker(UserDto.of(b.getBooker())).payment(PaymentDto.of(b.getPayment()))
						.passengerTickets(b.getPassengerTickets()!=null ? PassengerTicketDto.of(b.getPassengerTickets()):new ArrayList<>()).status(b.getStatus())
						.build());

	}
	
	public static TransportBookingDto<TrainDto> of(TrainBook trainBook) {
		return TravelBookEasyApp.ofNullable(trainBook,
				t -> TransportBookingDto.<TrainDto>builder().id(t.getId())
				.transport(TrainDto.of(t.getTrain()))
						.booker(UserDto.of(t.getBooker())).payment(PaymentDto.of(t.getPayment()))
						.passengerTickets(t.getPassengerTickets()!=null ? PassengerTicketDto.of(t.getPassengerTickets()):new ArrayList<>()).status(t.getStatus())
						.build());

	}

}
