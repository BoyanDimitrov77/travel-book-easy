package com.travel.book.easy.travelbookeasy.services.interfaces;

import java.math.BigDecimal;

import com.travel.book.easy.travelbookeasy.api.dto.CompanyDto;

public interface CompanyService {

	CompanyDto createCompanyRecord(CompanyDto companyDto);
	
	CompanyDto raitingCompany(long userId, long companyId, BigDecimal raiting);
	
	CompanyDto getCompany(long companyId, long userId);
}
