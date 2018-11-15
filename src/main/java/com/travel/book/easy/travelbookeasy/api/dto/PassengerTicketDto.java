package com.travel.book.easy.travelbookeasy.api.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.travel.book.easy.travelbookeasy.db.model.PassengerTicket;
import com.travel.book.easy.travelbookeasy.util.TravelBookEasyApp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PassengerTicketDto {

	private long id;
	
	private String passengerName;
	
	private String email;
	
	private String phoneNumber;
	
	private String ticketNumber;
	
	private String boardSeatNumber;

	public PassengerTicketDto() {
		super();
	}

	public PassengerTicketDto(long id, String passengerName, String email, String phoneNumber, String ticketNumber,
			String boardSeatNumber) {
		super();
		this.id = id;
		this.passengerName = passengerName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.ticketNumber = ticketNumber;
		this.boardSeatNumber = boardSeatNumber;
	}
	
	public static List<PassengerTicketDto> of(List<PassengerTicket> passengerTickets) {
		return passengerTickets.stream()
				.map(pt -> TravelBookEasyApp.ofNullable(pt,
						t -> PassengerTicketDto.builder().id(t.getId()).passengerName(t.getPassengerName())
								.ticketNumber(t.getTicketNumber()).boardSeatNumber(t.getBoardSeatNumber())
								.email(t.getEmail()).boardSeatNumber(t.getBoardSeatNumber()).build()))
				.collect(Collectors.toList());
	}
	
}
