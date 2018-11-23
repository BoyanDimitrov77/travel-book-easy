package com.travel.book.easy.travelbookeasy.db.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_comment_vote")
public class UserCompanyVote implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserCompanyVotePk id;

	public UserCompanyVotePk getId() {
		return this.id;
	}

	public void setId(UserCompanyVotePk id) {
		this.id = id;
	}

}
