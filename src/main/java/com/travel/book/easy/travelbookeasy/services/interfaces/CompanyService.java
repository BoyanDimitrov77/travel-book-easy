package com.travel.book.easy.travelbookeasy.services.interfaces;

import java.math.BigDecimal;
import java.util.List;

import com.travel.book.easy.travelbookeasy.api.dto.CompanyDto;

public interface CompanyService {

	CompanyDto createCompanyRecord(CompanyDto companyDto);
	
	CompanyDto ratingCompany(long userId, long companyId, BigDecimal rating);
	
	CompanyDto getCompany(long companyId, long userId);

	List<CompanyDto> getAllCompany();

	CompanyDto commnetsCompany(long userId, long companyId, String comment);

	void voteComment(long userId,long commentId ,boolean isLike);

}
