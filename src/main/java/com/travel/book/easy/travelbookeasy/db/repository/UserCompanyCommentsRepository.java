package com.travel.book.easy.travelbookeasy.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travel.book.easy.travelbookeasy.db.model.UserCompanyComment;
import com.travel.book.easy.travelbookeasy.db.model.UserCompanyVote;

public interface UserCompanyCommentsRepository extends JpaRepository<UserCompanyComment, Long>{
	
	@Query("Select ucv FROM UserCompanyVote ucv WHERE ucv.id.userCompanyComment.company.id =:companyId")
	List<UserCompanyVote> findAllUserCompanyVoteByCompany(@Param("companyId") long companyId);

}
