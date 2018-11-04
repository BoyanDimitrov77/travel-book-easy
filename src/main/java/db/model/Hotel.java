package db.model;

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
@Table(name = "hotel")

public class Hotel {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "name", nullable = false)
	private String hotelName;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "location_id")
	private Location location;

	@Column(name = "description")
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "timestamp")
	private Date timestamp;
	
	@OneToMany
	@JoinTable(
			name = "hotel_picture",
			joinColumns = @JoinColumn(name = "hotel_id"),
			inverseJoinColumns =@JoinColumn(name = "picture_id"))
	private List<Picture> hotelPictures;

	@OneToMany(fetch=FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "hotel")
	private List<HotelRoom> hotelRooms;
	
}
