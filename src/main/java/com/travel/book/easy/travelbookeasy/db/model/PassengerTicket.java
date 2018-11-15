package com.travel.book.easy.travelbookeasy.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
@Table(name = "passenger_ticket")
public class PassengerTicket {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "passenger_name")
	private String passengerName;

	@Column(name = "email")
	private String email;
	
	@Column(name  = "phone_number")
	private String phoneNumber;

	@Column(name = "ticket_number")
	private String ticketNumber;

	@Column(name = "seat_no")
	private String boardSeatNumber;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "timestamp")
	private Date timestamp;

}
