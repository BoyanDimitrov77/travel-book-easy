package db.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="travel_class")
public class TravelClass {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "max_seats")
	private int maxSeats;

	@Column(name = "count_seats")
	private int counterSeats;

	@Column(name = "travel_class")
	private String travelClass;

	@Column(name = "price")
	private BigDecimal price;
	
}
