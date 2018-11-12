package com.travel.book.easy.travelbookeasy.services.interfaces;

import java.math.BigDecimal;
import java.util.List;

import com.travel.book.easy.travelbookeasy.api.dto.FlightDto;
import com.travel.book.easy.travelbookeasy.api.dto.PassengerTicketDto;
import com.travel.book.easy.travelbookeasy.api.dto.TransportBookingDto;

public interface BookingService {

	TransportBookingDto<FlightDto> bookFlight(long flightId, long userId);
	
	TransportBookingDto<FlightDto> addPassengersToFlightBook(long flightBookId, long travelClassId, List<PassengerTicketDto> passengerTicketDtos);
	
	TransportBookingDto<FlightDto> payBookedFlight(BigDecimal amount, long flightBookId, long travelClassId);
}
