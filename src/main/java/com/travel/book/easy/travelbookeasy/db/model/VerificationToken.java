package com.travel.book.easy.travelbookeasy.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "verification_token")
@Data
@Builder
public class VerificationToken {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

    @Column(name = "token")
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(name = "expiry_date",nullable = false,columnDefinition=" timestamp with time zone")
    private Date expiryDate;
    
    @Column(name = "verified")
    private boolean verified;

    public VerificationToken() {
        super();
    }

    public VerificationToken(Long id, String token, User user, Date expiryDate, boolean verified) {
        super();
        this.id = id;
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
        this.verified = verified;
    }
}

