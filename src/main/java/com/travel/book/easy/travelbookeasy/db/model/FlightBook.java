package com.travel.book.easy.travelbookeasy.db.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
@Table(name = "flight_book")
public class FlightBook  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	
	@ManyToOne
	@JoinColumn(name = "flight_id")
	private Flight flight;
	
	@ManyToOne
	@JoinColumn(name = "booker")
	private User booker;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "payment_id")
	private Payment payment;
	
	@Column(name = "status")
	private String status;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "timestamp")
	private Date timestamp;
	
	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinTable(
			name="flight_book_passenger_ticket",
			joinColumns = @JoinColumn(name = "flight_book_id"),
			inverseJoinColumns = @JoinColumn(name = "passenger_ticket_id"))
	private List<PassengerTicket> passengerTickets;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlightBook other = (FlightBook) obj;
		if (booker == null) {
			if (other.booker != null)
				return false;
		} else if (!booker.equals(other.booker))
			return false;
		if (flight == null) {
			if (other.flight != null)
				return false;
		} else if (!flight.equals(other.flight))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((booker == null) ? 0 : booker.hashCode());
		result = prime * result + ((flight == null) ? 0 : flight.hashCode());
		return result;
	}
	
}
