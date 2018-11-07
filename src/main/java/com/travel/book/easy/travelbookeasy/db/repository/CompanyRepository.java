package com.travel.book.easy.travelbookeasy.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.book.easy.travelbookeasy.db.model.Company;

public interface CompanyRepository extends JpaRepository<Company,Long>{

	Company findByName(String name);
	
}
