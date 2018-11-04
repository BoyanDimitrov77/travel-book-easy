package db.model;

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
@Table(name="company")
public class Company {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="rating")
	private BigDecimal rating;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="company_logo")
	Picture companyLogo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "timestamp")
	private Date timestamp;
}
