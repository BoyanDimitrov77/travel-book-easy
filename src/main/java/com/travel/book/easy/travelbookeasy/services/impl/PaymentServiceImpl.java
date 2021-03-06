package com.travel.book.easy.travelbookeasy.services.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.book.easy.travelbookeasy.api.common.ApiException;
import com.travel.book.easy.travelbookeasy.db.model.BusBook;
import com.travel.book.easy.travelbookeasy.db.model.FlightBook;
import com.travel.book.easy.travelbookeasy.db.model.Payment;
import com.travel.book.easy.travelbookeasy.db.model.PaymentStatus;
import com.travel.book.easy.travelbookeasy.db.model.TrainBook;
import com.travel.book.easy.travelbookeasy.db.model.TravelClass;
import com.travel.book.easy.travelbookeasy.db.model.User;
import com.travel.book.easy.travelbookeasy.db.repository.PaymentRepository;
import com.travel.book.easy.travelbookeasy.services.interfaces.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService{

	@Autowired
	private PaymentRepository paymentRepository;
	
	@Override
	public Payment createPaymentRecord(User user) {
		
		Payment payment = new Payment();
		payment.setUser(user);
		payment.setAmount(BigDecimal.ZERO);
		payment.setStatus(PaymentStatus.PENDING.toString());
		
		Payment savedPayment = paymentRepository.saveAndFlush(payment);
		
		return savedPayment;
	}

	@Override
	public FlightBook payBookedFlight(BigDecimal amount, FlightBook flightBook, TravelClass travelClass) {
		
		Payment payment = flightBook.getPayment();
		
		BigDecimal totalAmountOfBooking = travelClass.getPrice().multiply(new BigDecimal(flightBook.getPassengerTickets().size()));
		
		if(amount.compareTo(totalAmountOfBooking) == 0) {
			payment.setAmount(amount);
			
		}else {
			throw new ApiException("Amount is not correct");
		}
		
		payment.setStatus(PaymentStatus.CONFIRMED.toString());
		paymentRepository.saveAndFlush(payment);
		
		return flightBook;
	}

	@Override
	public BusBook payBookedBus(BigDecimal amount, BusBook busBook) {
		Payment payment = busBook.getPayment();

		BigDecimal totalAmountOfBooking = busBook.getBus().getPrice()
				.multiply(new BigDecimal(busBook.getPassengerTickets().size()));

		if (amount.compareTo(totalAmountOfBooking) == 0) {
			payment.setAmount(amount);

		} else {
			throw new ApiException("Amount is not correct");
		}

		payment.setStatus(PaymentStatus.CONFIRMED.toString());
		paymentRepository.saveAndFlush(payment);

		return busBook;
	}

	@Override
	public TrainBook payBookedTrain(BigDecimal amount, TrainBook trainBook, TravelClass travelClass) {
		Payment payment = trainBook.getPayment();

		BigDecimal totalAmountOfBooking = travelClass.getPrice()
				.multiply(new BigDecimal(trainBook.getPassengerTickets().size()));

		if (amount.compareTo(totalAmountOfBooking) == 0) {
			payment.setAmount(amount);

		} else {
			throw new ApiException("Amount is not correct");
		}

		payment.setStatus(PaymentStatus.CONFIRMED.toString());
		paymentRepository.saveAndFlush(payment);

		return trainBook;
	}

}
