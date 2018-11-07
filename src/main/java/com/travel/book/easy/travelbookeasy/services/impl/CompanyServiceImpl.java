package com.travel.book.easy.travelbookeasy.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.book.easy.travelbookeasy.api.dto.CompanyDto;
import com.travel.book.easy.travelbookeasy.db.model.Company;
import com.travel.book.easy.travelbookeasy.db.repository.CompanyRepository;
import com.travel.book.easy.travelbookeasy.services.interfaces.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Override
	public CompanyDto createCompanyRecord(CompanyDto companyDto) {

		Company company = new Company();
		company.setName(companyDto.getName());

		Company saveCompany = companyRepository.saveAndFlush(company);

		return CompanyDto.of(saveCompany);

	}

}
