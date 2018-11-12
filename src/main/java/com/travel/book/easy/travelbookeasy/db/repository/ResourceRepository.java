package com.travel.book.easy.travelbookeasy.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.book.easy.travelbookeasy.db.model.Resource;

public interface ResourceRepository extends JpaRepository<Resource, String>{

}
