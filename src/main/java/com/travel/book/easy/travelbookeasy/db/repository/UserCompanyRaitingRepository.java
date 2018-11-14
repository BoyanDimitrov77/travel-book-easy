package com.travel.book.easy.travelbookeasy.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travel.book.easy.travelbookeasy.db.model.UserCompanyRaiting;

public interface UserCompanyRaitingRepository extends JpaRepository<UserCompanyRaiting, Long>{
	
	@Query("SELECT usr FROM UserCompanyRaiting usr WHERE usr.id.user.id =:userId AND usr.id.company.id =:companyId")
	UserCompanyRaiting findUserCompanyRecord(@Param("userId") long userId, @Param("companyId") long companyId);
	
	@Query("SELECT usr FROM UserCompanyRaiting usr WHERE usr.id.company.id =:companyId")
	List<UserCompanyRaiting> findByCompany(@Param("companyId") long companyId);

}
