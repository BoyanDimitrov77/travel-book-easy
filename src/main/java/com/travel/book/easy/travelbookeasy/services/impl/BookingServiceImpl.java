package com.travel.book.easy.travelbookeasy.services.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.book.easy.travelbookeasy.api.common.ApiException;
import com.travel.book.easy.travelbookeasy.api.dto.BusDto;
import com.travel.book.easy.travelbookeasy.api.dto.FlightDto;
import com.travel.book.easy.travelbookeasy.api.dto.HotelBookingDto;
import com.travel.book.easy.travelbookeasy.api.dto.PassengerTicketDto;
import com.travel.book.easy.travelbookeasy.api.dto.TrainDto;
import com.travel.book.easy.travelbookeasy.api.dto.TransportBookingDto;
import com.travel.book.easy.travelbookeasy.db.model.BookStatus;
import com.travel.book.easy.travelbookeasy.db.model.Bus;
import com.travel.book.easy.travelbookeasy.db.model.BusBook;
import com.travel.book.easy.travelbookeasy.db.model.Flight;
import com.travel.book.easy.travelbookeasy.db.model.FlightBook;
import com.travel.book.easy.travelbookeasy.db.model.HotelBook;
import com.travel.book.easy.travelbookeasy.db.model.HotelRoom;
import com.travel.book.easy.travelbookeasy.db.model.PassengerTicket;
import com.travel.book.easy.travelbookeasy.db.model.Train;
import com.travel.book.easy.travelbookeasy.db.model.TrainBook;
import com.travel.book.easy.travelbookeasy.db.model.TravelClass;
import com.travel.book.easy.travelbookeasy.db.model.User;
import com.travel.book.easy.travelbookeasy.db.repository.BusBookingRepository;
import com.travel.book.easy.travelbookeasy.db.repository.BusRepository;
import com.travel.book.easy.travelbookeasy.db.repository.FlightBookingRepository;
import com.travel.book.easy.travelbookeasy.db.repository.FlightRepository;
import com.travel.book.easy.travelbookeasy.db.repository.HotelBookRepository;
import com.travel.book.easy.travelbookeasy.db.repository.HotelRepository;
import com.travel.book.easy.travelbookeasy.db.repository.PassengerTicketRepository;
import com.travel.book.easy.travelbookeasy.db.repository.TrainBookRepository;
import com.travel.book.easy.travelbookeasy.db.repository.TrainRepository;
import com.travel.book.easy.travelbookeasy.db.repository.TravelClassRepository;
import com.travel.book.easy.travelbookeasy.db.repository.UserRepository;
import com.travel.book.easy.travelbookeasy.services.interfaces.BookingService;
import com.travel.book.easy.travelbookeasy.services.interfaces.PaymentService;

@Service
public class BookingServiceImpl implements BookingService{

	private static final String[] SEATS_LETTER = { "A", "B", "C", "D", "E", "F" };

	@Autowired
	private FlightRepository flightRepository;

	@Autowired
	private BusRepository busRepository;

	@Autowired
	private TrainRepository trainRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FlightBookingRepository flightBookingRepository;

	@Autowired
	private BusBookingRepository busBookingRepository;

	@Autowired
	private TrainBookRepository trainBookingRepository;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private TravelClassRepository travelClassRepository;

	@Autowired
	private PassengerTicketRepository passengerTicketRepository;

	@Autowired
	private HotelBookRepository hotelBookRepository;

	@Autowired
	private HotelRepository hotelRepository;
	
	@Override
	public TransportBookingDto<FlightDto> bookFlight(long flightId, long userId) {
		Optional<Flight> flight = flightRepository.findById(flightId);
		
		if(!flight.isPresent()) {
			throw new ApiException("Flight not found");
		}
		
		User user = userRepository.findById(userId);
		
		if(user == null) {
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
		
		if(!flightBook.isPresent()) {
			throw new ApiException("Flight Book not found");
		}
		
		Optional<TravelClass> travelClass = travelClassRepository.findById(travelClassId);
		
		if(!travelClass.isPresent()) {
			throw new ApiException("Travel Class not found");
		}
		
		List<PassengerTicket> passengersTickets = passengerTicketDtos.stream().map(ticket ->{
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
	public TransportBookingDto<FlightDto> payBookedFlight(BigDecimal amount, long flightBookId, long travelClassId, String nonceFromTheClient) {
		
		Optional<FlightBook> flightBook = flightBookingRepository.findById(flightBookId);
		
		if(!flightBook.isPresent()) {
			throw new ApiException("Flight Book not found");
		}
		
		Optional<TravelClass> travelClass = travelClassRepository.findById(travelClassId);
		
		if(!travelClass.isPresent()) {
			throw new ApiException("Travel Class not found");
		}
		
		FlightBook payBookedFlight = paymentService.payBookedFlight(amount, flightBook.get(), travelClass.get(), nonceFromTheClient);
		payBookedFlight.setStatus(BookStatus.CONFIRMED.toString());
		
		FlightBook savedFlightBook = flightBookingRepository.saveAndFlush(payBookedFlight);
		
		return TransportBookingDto.of(savedFlightBook);
	}

	@Override
	public TransportBookingDto<BusDto> bookBus(long busId, long userId) {
		Optional<Bus> bus = busRepository.findById(busId);

		if (!bus.isPresent()) {
			throw new ApiException("Flight not found");
		}

		User user = userRepository.findById(userId);

		if (user == null) {
			throw new ApiException("User not found");
		}

		BusBook busBook = new BusBook();
		busBook.setBooker(user);
		busBook.setBus(bus.get());
		busBook.setPayment(paymentService.createPaymentRecord(user));
		busBook.setStatus(BookStatus.WAITING.toString());

		BusBook savedBusBook = busBookingRepository.saveAndFlush(busBook);

		return TransportBookingDto.of(savedBusBook);
	}

	@Override
	public TransportBookingDto<BusDto> addPassengersToBusBook(long busBookId,
			List<PassengerTicketDto> passengerTicketDtos) {
		Optional<BusBook> busBook = busBookingRepository.findById(busBookId);

		if (!busBook.isPresent()) {
			throw new ApiException("Bus Book not found");
		}

		List<PassengerTicket> passengersTickets = passengerTicketDtos.stream().map(ticket -> {
			PassengerTicket passengerTicket = new PassengerTicket();
			passengerTicket.setPassengerName(ticket.getPassengerName());
			passengerTicket.setPhoneNumber(ticket.getPhoneNumber());
			passengerTicket.setEmail(ticket.getEmail());
			passengerTicket.setBoardSeatNumber(generateSeatNumForBus(busBook.get()));
			passengerTicket.setTicketNumber(generateTicketNumber());

			return passengerTicket;

		}).collect(Collectors.toList());

		List<PassengerTicket> savedPassengersTickets = passengerTicketRepository.saveAll(passengersTickets);

		busBook.get().setPassengerTickets(savedPassengersTickets);

		BusBook savedBusBookWithPassengers = busBookingRepository.saveAndFlush(busBook.get());

		return TransportBookingDto.of(savedBusBookWithPassengers);
	}

	private String generateSeatNumForBus(BusBook busBook) {

		int maxSeats = busBook.getBus().getMaxSeats();

		int counterSeats = busBook.getBus().getCountSeats();

		if (counterSeats <= maxSeats) {
			counterSeats++;
		}

		busBook.getBus().setCountSeats(counterSeats);

		return String.valueOf(counterSeats);
	}

	private String generateSeatNumForTrain(TravelClass travelClass) {

		int maxSeats = travelClass.getMaxSeats();

		int currentCounterSeats = travelClass.getCounterSeats();

		if (currentCounterSeats <= maxSeats) {
			currentCounterSeats++;

		}

		travelClass.setCounterSeats(currentCounterSeats);

		return String.valueOf(currentCounterSeats);
	}

	@Override
	public TransportBookingDto<BusDto> payBookedBus(BigDecimal amount, long busBookId, String nonceFromTheClient) {
		Optional<BusBook> busBook = busBookingRepository.findById(busBookId);

		if (!busBook.isPresent()) {
			throw new ApiException("Flight Book not found");
		}

		BusBook payBookedFlight = paymentService.payBookedBus(amount, busBook.get(), nonceFromTheClient);
		payBookedFlight.setStatus(BookStatus.CONFIRMED.toString());

		BusBook savedBusBook = busBookingRepository.saveAndFlush(payBookedFlight);

		return TransportBookingDto.of(savedBusBook);
	}

	@Override
	public TransportBookingDto<TrainDto> bookTrain(long trainId, long userId) {
		Optional<Train> train = trainRepository.findById(trainId);

		if (!train.isPresent()) {
			throw new ApiException("Flight not found");
		}

		User user = userRepository.findById(userId);

		if (user == null) {
			throw new ApiException("User not found");
		}

		TrainBook trainBook = new TrainBook();
		trainBook.setBooker(user);
		trainBook.setTrain(train.get());
		trainBook.setPayment(paymentService.createPaymentRecord(user));
		trainBook.setStatus(BookStatus.WAITING.toString());

		TrainBook savedTrainBook = trainBookingRepository.saveAndFlush(trainBook);

		return TransportBookingDto.of(savedTrainBook);
	}

	@Override
	public TransportBookingDto<TrainDto> addPassengersToTrainBook(long trainBookId, long travelClassId,
			List<PassengerTicketDto> passengerTicketDtos) {
		Optional<TrainBook> trainBook = trainBookingRepository.findById(trainBookId);

		if (!trainBook.isPresent()) {
			throw new ApiException("Train Book not found");
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
			passengerTicket.setBoardSeatNumber(generateSeatNumForTrain(travelClass.get()));
			passengerTicket.setTicketNumber(generateTicketNumber());

			return passengerTicket;

		}).collect(Collectors.toList());

		List<PassengerTicket> savedPassengersTickets = passengerTicketRepository.saveAll(passengersTickets);

		trainBook.get().setPassengerTickets(savedPassengersTickets);

		TrainBook savedTrainBookWithPassengers = trainBookingRepository.saveAndFlush(trainBook.get());

		return TransportBookingDto.of(savedTrainBookWithPassengers);
	}

	@Override
	public TransportBookingDto<TrainDto> payBookedTrain(BigDecimal amount, long trainBookId, long travelClassId, String nonceFromTheClient) {
		Optional<TrainBook> trainBook = trainBookingRepository.findById(trainBookId);

		if (!trainBook.isPresent()) {
			throw new ApiException("Train Book not found");
		}

		Optional<TravelClass> travelClass = travelClassRepository.findById(travelClassId);

		if (!travelClass.isPresent()) {
			throw new ApiException("Travel Class not found");
		}

		TrainBook payBookedTrain = paymentService.payBookedTrain(amount, trainBook.get(), travelClass.get(), nonceFromTheClient);
		payBookedTrain.setStatus(BookStatus.CONFIRMED.toString());

		TrainBook savedTrainBook = trainBookingRepository.saveAndFlush(payBookedTrain);

		return TransportBookingDto.of(savedTrainBook);
	}

	@Override
	public HotelBookingDto bookHotel(long hotelRoomId, long userId) {
		HotelRoom hotelRoom = hotelRepository.getHotelRoom(hotelRoomId);

		if (hotelRoom == null) {
			throw new ApiException("Hotel room not found");
		}

		User booker = userRepository.findById(userId);

		HotelBook hotelBook = new HotelBook();
		hotelBook.setHotelRoom(hotelRoom);
		hotelBook.setUser(booker);
		hotelBook.setPayment(paymentService.createPaymentRecord(booker));
		hotelBook.setStatus(BookStatus.WAITING.toString());
		hotelBook.setTimestamp(new Date());

		HotelBook saveHotelBook = hotelBookRepository.saveAndFlush(hotelBook);

		return HotelBookingDto.of(saveHotelBook);
	}

	@Override
	public HotelBookingDto payHotel(long hotelBookId, BigDecimal amount, String nonceFromTheClient) {
		HotelBook hotelBook = hotelBookRepository.findById(hotelBookId).get();

		HotelBook hotelB = paymentService.payHotelBook(hotelBook, amount, nonceFromTheClient);

		HotelBook saveHotelBook = hotelBookRepository.saveAndFlush(hotelB);

		return HotelBookingDto.of(saveHotelBook);
	}

}
