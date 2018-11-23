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
public class UserCompanyVotePk implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	public UserCompanyVotePk() {}
	
	public UserCompanyVotePk(User user, UserCompanyComment userCompanyComment) {

		this.user = user;
		this.userCompanyComment = userCompanyComment;
	}

	@ManyToOne
	@JoinColumn(name = "user_id", updatable = false, insertable = false)
	User user;

	@ManyToOne
	@JoinColumn(name = "user_company_comments_id", updatable = false, insertable = false)
	UserCompanyComment userCompanyComment;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((userCompanyComment == null) ? 0 : userCompanyComment.hashCode());
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
		UserCompanyVotePk other = (UserCompanyVotePk) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (userCompanyComment == null) {
			if (other.userCompanyComment != null)
				return false;
		} else if (!userCompanyComment.equals(other.userCompanyComment))
			return false;
		return true;
	}

}
