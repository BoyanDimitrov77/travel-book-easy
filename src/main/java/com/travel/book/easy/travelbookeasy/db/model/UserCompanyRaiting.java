package com.travel.book.easy.travelbookeasy.db.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "user_company_raiting")
public class UserCompanyRaiting {

	
	@EmbeddedId
	private UserCompanyRaitingPk id;
	
	@Column(name = "raiting")
	private BigDecimal raiting;
}
