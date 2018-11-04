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
@Table(name="train")
public class Train {

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
	private String arriveDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "timestamp")
	private Date timestamp;
	
	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinTable(
			name="travel_class_train",
			joinColumns = @JoinColumn(name="train_id"),
			inverseJoinColumns = @JoinColumn(name="travel_class_id"))
	private List<TravelClass> travelClasses;
	
}
