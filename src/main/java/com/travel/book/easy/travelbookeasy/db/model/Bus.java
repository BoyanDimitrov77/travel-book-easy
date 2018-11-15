package com.travel.book.easy.travelbookeasy.db.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
@Table(name="bus")
public class Bus {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne(fetch= FetchType.EAGER)
	@JoinColumn(name="company_id")
	private Company company;
	
	@Column(name ="name")
	private String name;
	
	@ManyToOne(fetch= FetchType.EAGER)
	@JoinColumn(name="location_from_id")
	private Location locationFrom;
	
	@ManyToOne(fetch= FetchType.EAGER)
	@JoinColumn(name="location_to_id")
	private Location locationTo;
	
	@Column(name="depart_date")
	private Date departDate;
	
	@Column(name="arrive_date")
	private Date arriveDate;
	
	@Column(name= "price")
	private BigDecimal price;

	@Column(name = "max_seats")
	private int maxSeats;

	@Column(name = "count_seats")
	private int countSeats;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "timestamp")
	private Date timestamp;
	
}
