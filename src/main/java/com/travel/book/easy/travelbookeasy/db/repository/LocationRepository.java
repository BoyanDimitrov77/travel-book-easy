package com.travel.book.easy.travelbookeasy.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.book.easy.travelbookeasy.db.model.Location;

public interface LocationRepository extends JpaRepository<Location,Long>{

	Location findByName(String name);
}
