package com.travel.book.easy.travelbookeasy.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.book.easy.travelbookeasy.db.model.User;
import com.travel.book.easy.travelbookeasy.db.model.VerificationToken;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>{

	 public VerificationToken findByUser(User user);

	  public VerificationToken findByToken(String token);
}
