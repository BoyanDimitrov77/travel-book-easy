package com.travel.book.easy.travelbookeasy.services.interfaces;

import java.math.BigDecimal;

import com.travel.book.easy.travelbookeasy.db.model.BusBook;
import com.travel.book.easy.travelbookeasy.db.model.FlightBook;
import com.travel.book.easy.travelbookeasy.db.model.HotelBook;
import com.travel.book.easy.travelbookeasy.db.model.Payment;
import com.travel.book.easy.travelbookeasy.db.model.TrainBook;
import com.travel.book.easy.travelbookeasy.db.model.TravelClass;
import com.travel.book.easy.travelbookeasy.db.model.User;

public interface PaymentService {

	Payment createPaymentRecord(User user);
	
	FlightBook payBookedFlight(BigDecimal amount, FlightBook flightBook, TravelClass travelClass, String nonceFromTheClient);

	BusBook payBookedBus(BigDecimal amount, BusBook busBook, String nonceFromTheClient);

	TrainBook payBookedTrain(BigDecimal amount, TrainBook trainBook, TravelClass travelClass, String nonceFromTheClient);

	HotelBook payHotelBook(HotelBook hotelBook, BigDecimal amount, String nonceFromTheClient);
}