package com.travel.book.easy.travelbookeasy.db.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
@Table(name = "resources")
public class Resource {

	@Id
	@Column(name = "id", nullable = false)
	private String id;

	@Column(name = "value", nullable = false)
	private String value;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "timestamp")
	private Date timeCreated;
	
		public Resource() {}
	
	public Resource(String id, String value, String description, Date timeCreated) {
		this.id = id;
		this.value = value;
		this.timeCreated = timeCreated;
	}	

	@PrePersist
	private void setUUID() {
		if (id == null) {
			id = UUID.randomUUID().toString();
		}
	}

}

