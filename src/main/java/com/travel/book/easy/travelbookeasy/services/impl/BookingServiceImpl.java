package com.travel.book.easy.travelbookeasy.services.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.book.easy.travelbookeasy.api.common.ApiException;
import com.travel.book.easy.travelbookeasy.api.dto.FlightDto;
import com.travel.book.easy.travelbookeasy.api.dto.PassengerTicketDto;
import com.travel.book.easy.travelbookeasy.api.dto.TransportBookingDto;
import com.travel.book.easy.travelbookeasy.db.model.BookStatus;
import com.travel.book.easy.travelbookeasy.db.model.Flight;
import com.travel.book.easy.travelbookeasy.db.model.FlightBook;
import com.travel.book.easy.travelbookeasy.db.model.PassengerTicket;
import com.travel.book.easy.travelbookeasy.db.model.TravelClass;
import com.travel.book.easy.travelbookeasy.db.model.User;
import com.travel.book.easy.travelbookeasy.db.repository.FlightBookingRepository;
import com.travel.book.easy.travelbookeasy.db.repository.FlightRepository;
import com.travel.book.easy.travelbookeasy.db.repository.PassengerTicketRepository;
import com.travel.book.easy.travelbookeasy.db.repository.TravelClassRepository;
import com.travel.book.easy.travelbookeasy.db.repository.UserRepository;
import com.travel.book.easy.travelbookeasy.services.interfaces.BookingService;
import com.travel.book.easy.travelbookeasy.services.interfaces.PaymentService;

@Service
public class BookingServiceImpl implements BookingService {

	private static final String[] SEATS_LETTER = { "A", "B", "C", "D", "E", "F" };

	@Autowired
	private FlightRepository flightRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FlightBookingRepository flightBookingRepository;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private TravelClassRepository travelClassRepository;

	@Autowired
	private PassengerTicketRepository passengerTicketRepository;

	@Override
	public TransportBookingDto<FlightDto> bookFlight(long flightId, long userId) {
		Optional<Flight> flight = flightRepository.findById(flightId);

		if (!flight.isPresent()) {
			throw new ApiException("Flight not found");
		}

		User user = userRepository.findById(userId);

		if (user == null) {
			throw new ApiException("User not found");
		}

		FlightBook flightBook = new FlightBook();
		flightBook.setBooker(user);
		flightBook.setFlight(flight.get());
		flightBook.setPayment(paymentService.createPaymentRecord(user));
		flightBook.setStatus(BookStatus.WAITING.toString());

		FlightBook savedFlightBook = flightBookingRepository.saveAndFlush(flightBook);

		return TransportBookingDto.of(savedFlightBook);
	}

	@Override
	public TransportBookingDto<FlightDto> addPassengersToFlightBook(long flightBookId, long travelClassId,
			List<PassengerTicketDto> passengerTicketDtos) {

		Optional<FlightBook> flightBook = flightBookingRepository.findById(flightBookId);

		if (!flightBook.isPresent()) {
			throw new ApiException("Flight Book not found");
		}

		Optional<TravelClass> travelClass = travelClassRepository.findById(travelClassId);

		if (!travelClass.isPresent()) {
			throw new ApiException("Travel Class not found");
		}

		List<PassengerTicket> passengersTickets = passengerTicketDtos.stream().map(ticket -> {
			PassengerTicket passengerTicket = new PassengerTicket();
			passengerTicket.setPassengerName(ticket.getPassengerName());
			passengerTicket.setPhoneNumber(ticket.getPhoneNumber());
			passengerTicket.setEmail(ticket.getEmail());
			passengerTicket.setBoardSeatNumber(generateSeatNumForFlight(travelClass.get()));
			passengerTicket.setTicketNumber(generateTicketNumber());

			return passengerTicket;

		}).collect(Collectors.toList());

		List<PassengerTicket> savedPassengersTickets = passengerTicketRepository.saveAll(passengersTickets);

		flightBook.get().setPassengerTickets(savedPassengersTickets);

		FlightBook savedFlightBookWithPassengers = flightBookingRepository.saveAndFlush(flightBook.get());

		return TransportBookingDto.of(savedFlightBookWithPassengers);
	}

	private String generateSeatNumForFlight(TravelClass travelClass) {

		int maxSeats = travelClass.getMaxSeats();

		int currentCounterSeats = travelClass.getCounterSeats();

		StringBuilder seatNumber = new StringBuilder();

		if (currentCounterSeats <= maxSeats) {
			currentCounterSeats++;
			int result = currentCounterSeats % SEATS_LETTER.length;

			if (result == 0) {
				seatNumber.append(currentCounterSeats);
				seatNumber.append(SEATS_LETTER[5]);
			} else {
				seatNumber.append(currentCounterSeats);
				seatNumber.append(SEATS_LETTER[result - 1]);
			}
		}

		travelClass.setCounterSeats(currentCounterSeats);

		return seatNumber.toString();
	}

	private String generateTicketNumber() {

		long ticketNumber;

		do {
			ticketNumber = (long) Math.floor(Math.random() * 9000000000000L) + 1000000000000L;

		} while (passengerTicketRepository.findByTicketNumber(Long.toString(ticketNumber)) != null);

		return Long.toString(ticketNumber);

	}

	@Override
	public TransportBookingDto<FlightDto> payBookedFlight(BigDecimal amount, long flightBookId, long travelClassId,
			String nonceFromTheClient) {

		Optional<FlightBook> flightBook = flightBookingRepository.findById(flightBookId);

		if (!flightBook.isPresent()) {
			throw new ApiException("Flight Book not found");
		}

		Optional<TravelClass> travelClass = travelClassRepository.findById(travelClassId);

		if (!travelClass.isPresent()) {
			throw new ApiException("Travel Class not found");
		}

		FlightBook payBookedFlight = paymentService.payBookedFlight(amount, flightBook.get(), travelClass.get(),
				nonceFromTheClient);
		payBookedFlight.setStatus(BookStatus.CONFIRMED.toString());

		FlightBook savedFlightBook = flightBookingRepository.saveAndFlush(payBookedFlight);

		return TransportBookingDto.of(savedFlightBook);
	}

	@Override
	public List<TransportBookingDto<FlightDto>> findAllUserBooking(long userId) {

		User booker = userRepository.findById(userId);

		if (booker == null) {
			throw new ApiException("User not found");
		}

		List<FlightBook> findByBooker = flightBookingRepository.findByBooker(booker);

		return findByBooker.stream().map(fb -> TransportBookingDto.of(fb)).collect(Collectors.toList());
	}

}
