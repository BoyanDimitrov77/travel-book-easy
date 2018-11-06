package com.travel.book.easy.travelbookeasy.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.book.easy.travelbookeasy.db.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

	User findByEmail(String email);
}
