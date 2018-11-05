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
@Table(name = "train_book")
public class TrainBook implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "train_id")
	private Train train;
	
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
			name="train_book_passenger_ticket",
			joinColumns = @JoinColumn(name = "train_book_id"),
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
		TrainBook other = (TrainBook) obj;
		if (booker == null) {
			if (other.booker != null)
				return false;
		} else if (!booker.equals(other.booker))
			return false;
		if (train == null) {
			if (other.train != null)
				return false;
		} else if (!train.equals(other.train))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((booker == null) ? 0 : booker.hashCode());
		result = prime * result + ((train == null) ? 0 : train.hashCode());
		return result;
	}
	

}
