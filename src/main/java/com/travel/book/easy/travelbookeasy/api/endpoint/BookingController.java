package com.travel.book.easy.travelbookeasy.api.endpoint;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travel.book.easy.travelbookeasy.api.dto.BusDto;
import com.travel.book.easy.travelbookeasy.api.dto.FlightDto;
import com.travel.book.easy.travelbookeasy.api.dto.PassengerTicketDto;
import com.travel.book.easy.travelbookeasy.api.dto.TrainDto;
import com.travel.book.easy.travelbookeasy.api.dto.TransportBookingDto;
import com.travel.book.easy.travelbookeasy.services.interfaces.BookingService;
import com.travel.book.easy.travelbookeasy.util.UserUtil;

@RestController
@RequestMapping(value = "booking", produces = "application/json")
public class BookingController {

	@Autowired
	private BookingService bookingService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/bookFlight/{flightId}")
	@Transactional
	public ResponseEntity<TransportBookingDto<FlightDto>> bookFlight(@PathVariable("flightId") long flightId,
			SecurityContextHolder contex) {

	    TransportBookingDto<FlightDto> bookFlight = bookingService.bookFlight(flightId, UserUtil.gerUserFromContext().getId());

		return new ResponseEntity<>(bookFlight, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/bookFlight/addPassengers/{flightBookingId}/{travelClassId}")
	@Transactional
	public ResponseEntity<TransportBookingDto<FlightDto>> addPassengersToFlightBook(@PathVariable("flightBookingId") long flightBookingId,
			@PathVariable("travelClassId") long travelClassId,@RequestBody List<PassengerTicketDto> passengerTicketDtos) {

	    TransportBookingDto<FlightDto> flightBookWithPassengers = bookingService.addPassengersToFlightBook(flightBookingId, travelClassId,passengerTicketDtos);

		return new ResponseEntity<>(flightBookWithPassengers, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/bookFlight/payment")
	@Transactional
	public ResponseEntity<TransportBookingDto<FlightDto>> payBookedFlight(@RequestParam(value = "amount") String amount,
			@RequestParam(value = "flightBookId") String flightBookId,
			@RequestParam(value = "travelClassId")String travelClassId,
			@RequestParam("nonceFromTheClient") String nonceFromTheClient) {

		TransportBookingDto<FlightDto> payBookedFlight = bookingService.payBookedFlight(new BigDecimal(amount), Long.parseLong(flightBookId), Long.parseLong(travelClassId), nonceFromTheClient);
		
		return new ResponseEntity<>(payBookedFlight, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/bookBus/{busId}")
	@Transactional
	public ResponseEntity<TransportBookingDto<BusDto>> bookBus(@PathVariable("busId") long busId,
			SecurityContextHolder contex) {

		TransportBookingDto<BusDto> bookFlight = bookingService.bookBus(busId, UserUtil.gerUserFromContext().getId());

		return new ResponseEntity<>(bookFlight, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/bookBus/addPassengers/{busBookingId}")
	@Transactional
	public ResponseEntity<TransportBookingDto<BusDto>> addPassengersToBusBook(
			@PathVariable("busBookingId") long busBookingId,
			@RequestBody List<PassengerTicketDto> passengerTicketDtos) {

		TransportBookingDto<BusDto> busBookWithPassengers = bookingService.addPassengersToBusBook(busBookingId,
				passengerTicketDtos);

		return new ResponseEntity<>(busBookWithPassengers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/bookBus/payment")
	@Transactional
	public ResponseEntity<TransportBookingDto<BusDto>> payBookedBus(@RequestParam(value = "amount") String amount,
			@RequestParam(value = "busBookingId") String busBookingId, @RequestParam("nonceFromTheClient") String nonceFromTheClient) {

		TransportBookingDto<BusDto> payBookedBus = bookingService.payBookedBus(new BigDecimal(amount),
				Long.parseLong(busBookingId), nonceFromTheClient);

		return new ResponseEntity<>(payBookedBus, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/bookTrain/{trainId}")
	@Transactional
	public ResponseEntity<TransportBookingDto<TrainDto>> bookTrain(@PathVariable("trainId") long trainId,
			SecurityContextHolder contex) {

		TransportBookingDto<TrainDto> bookTrain = bookingService.bookTrain(trainId,
				UserUtil.gerUserFromContext().getId());

		return new ResponseEntity<>(bookTrain, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/bookTrain/addPassengers/{trainBookingId}/{travelClassId}")
	@Transactional
	public ResponseEntity<TransportBookingDto<TrainDto>> addPassengersToTrainBook(
			@PathVariable("trainBookingId") long trainBookingId, @PathVariable("travelClassId") long travelClassId,
			@RequestBody List<PassengerTicketDto> passengerTicketDtos) {

		TransportBookingDto<TrainDto> trainBookWithPassengers = bookingService.addPassengersToTrainBook(trainBookingId,
				travelClassId, passengerTicketDtos);

		return new ResponseEntity<>(trainBookWithPassengers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/bookTrain/payment")
	@Transactional
	public ResponseEntity<TransportBookingDto<TrainDto>> payBookedTrain(@RequestParam(value = "amount") String amount,
			@RequestParam(value = "trainBookingId") String trainBookingId,
			@RequestParam(value = "travelClassId") String travelClassId,
			@RequestParam("nonceFromTheClient") String nonceFromTheClient) {

		TransportBookingDto<TrainDto> payBookedTrain = bookingService.payBookedTrain(new BigDecimal(amount),
				Long.parseLong(trainBookingId), Long.parseLong(travelClassId), nonceFromTheClient);

		return new ResponseEntity<>(payBookedTrain, HttpStatus.OK);
	}

}
