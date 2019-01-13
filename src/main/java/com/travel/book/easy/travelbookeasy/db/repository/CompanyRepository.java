package com.travel.book.easy.travelbookeasy.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.travel.book.easy.travelbookeasy.db.model.Company;

public interface CompanyRepository extends JpaRepository<Company,Long>{

	Company findByName(String name);

	@Query("SELECT c FROM Company c ORDER BY c.rating DESC")
	List<Company> findAllCompanyOrderByRating();

}
