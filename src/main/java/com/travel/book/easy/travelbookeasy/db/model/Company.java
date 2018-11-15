package com.travel.book.easy.travelbookeasy.db.model;

import java.math.BigDecimal;
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
@Table(name="company")
public class Company {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="raiting")
	private BigDecimal raiting;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="company_logo")
	Picture companyLogo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "timestamp")
	private Date timestamp;

	@OneToMany(mappedBy = "id.company")
	List<UserCompanyRaiting> userCompanyRaitings;

	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "user_company_rating", joinColumns = @JoinColumn(name = "company_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> users;
}
