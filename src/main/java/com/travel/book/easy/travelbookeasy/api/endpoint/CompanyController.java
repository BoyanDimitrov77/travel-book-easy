package com.travel.book.easy.travelbookeasy.api.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.travel.book.easy.travelbookeasy.api.dto.CompanyDto;
import com.travel.book.easy.travelbookeasy.services.interfaces.CompanyService;

@RestController
@RequestMapping(value = "company", produces = "application/json")
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@RequestMapping(method = RequestMethod.POST, value = "/createCompanyRecord")
	@Transactional
	public ResponseEntity<CompanyDto> createAirlineRecord(@RequestBody CompanyDto airlineDto) {

		CompanyDto dto = companyService.createCompanyRecord(airlineDto);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
}
