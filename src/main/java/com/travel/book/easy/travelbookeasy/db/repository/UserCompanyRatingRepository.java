package com.travel.book.easy.travelbookeasy.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travel.book.easy.travelbookeasy.db.model.UserCompanyRating;

public interface UserCompanyRatingRepository extends JpaRepository<UserCompanyRating, Long>{
	
	@Query("SELECT usr FROM UserCompanyRating usr WHERE usr.id.user.id =:userId AND usr.id.company.id =:companyId")
	UserCompanyRating findUserCompanyRecord(@Param("userId") long userId, @Param("companyId") long companyId);
	
	@Query("SELECT usr FROM UserCompanyRating usr WHERE usr.id.company.id =:companyId")
	List<UserCompanyRating> findByCompany(@Param("companyId") long companyId);

}
