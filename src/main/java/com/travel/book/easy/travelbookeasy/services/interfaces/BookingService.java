package com.travel.book.easy.travelbookeasy.services.interfaces;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.travel.book.easy.travelbookeasy.api.dto.BusDto;
import com.travel.book.easy.travelbookeasy.api.dto.FlightDto;
import com.travel.book.easy.travelbookeasy.api.dto.PassengerTicketDto;
import com.travel.book.easy.travelbookeasy.api.dto.TrainDto;
import com.travel.book.easy.travelbookeasy.api.dto.TransportBookingDto;

public interface BookingService {

	TransportBookingDto<FlightDto> bookFlight(long flightId, long userId);
	
	TransportBookingDto<FlightDto> addPassengersToFlightBook(long flightBookId, long travelClassId, List<PassengerTicketDto> passengerTicketDtos);
	
	TransportBookingDto<FlightDto> payBookedFlight(BigDecimal amount, long flightBookId, long travelClassId, String nonceFromTheClient);

	TransportBookingDto<BusDto> bookBus(long busId, long userId);

	TransportBookingDto<BusDto> addPassengersToBusBook(long busBookId, List<PassengerTicketDto> passengerTicketDtos);

	TransportBookingDto<BusDto> payBookedBus(BigDecimal amount, long busBookId, String nonceFromTheClient);

	TransportBookingDto<TrainDto> bookTrain(long trainId, long userId);

	TransportBookingDto<TrainDto> addPassengersToTrainBook(long trainBookId, long travelClassId,
			List<PassengerTicketDto> passengerTicketDtos);

	TransportBookingDto<TrainDto> payBookedTrain(BigDecimal amount, long trainBookId, long travelClassId, String nonceFromTheClient);
}
