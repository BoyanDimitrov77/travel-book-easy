package com.travel.book.easy.travelbookeasy.db.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Builder
@Getter
@Setter
public class UserCompanyRatingPk implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UserCompanyRatingPk() {}
	
	public UserCompanyRatingPk (User user, Company company) {
		this.user = user;
		this.company = company;
	}
	
	
	@ManyToOne
	@JoinColumn(name = "user_id", updatable = false, insertable = false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "company_id", updatable = false, insertable = false)
	private Company company;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserCompanyRatingPk other = (UserCompanyRatingPk) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	
}
