package com.travel.book.easy.travelbookeasy.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.book.easy.travelbookeasy.db.model.PassengerTicket;

public interface PassengerTicketRepository extends JpaRepository<PassengerTicket,Long>{

	PassengerTicket findByTicketNumber(String ticketNumber);
	
}
