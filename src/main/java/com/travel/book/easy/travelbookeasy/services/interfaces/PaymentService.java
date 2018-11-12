package com.travel.book.easy.travelbookeasy.services.interfaces;

import java.math.BigDecimal;

import com.travel.book.easy.travelbookeasy.db.model.FlightBook;
import com.travel.book.easy.travelbookeasy.db.model.Payment;
import com.travel.book.easy.travelbookeasy.db.model.TravelClass;
import com.travel.book.easy.travelbookeasy.db.model.User;

public interface PaymentService {

	Payment createPaymentRecord(User user);
	
	FlightBook payBookedFlight(BigDecimal amount, FlightBook flightBook, TravelClass travelClass);
}