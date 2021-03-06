package com.travel.book.easy.travelbookeasy.api.endpoint;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travel.book.easy.travelbookeasy.api.dto.CompanyDto;
import com.travel.book.easy.travelbookeasy.services.interfaces.CompanyService;
import com.travel.book.easy.travelbookeasy.util.UserUtil;

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

	@RequestMapping(method = RequestMethod.POST, value = "/ratingCompany/{companyId}")
	public ResponseEntity<CompanyDto> raitingCompany(@PathVariable(name = "companyId") long companyId,
			@RequestParam(value = "raiting") String raiting, SecurityContextHolder contex) {

		CompanyDto dto = companyService.raitingCompany(UserUtil.gerUserFromContext().getId(), companyId,
				new BigDecimal(raiting));

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{companyId}")
	public ResponseEntity<CompanyDto> getCompany(@PathVariable(name = "companyId") long companyId,
			SecurityContextHolder contex) {

		CompanyDto dto = companyService.getCompany(companyId, UserUtil.gerUserFromContext().getId());

		return new ResponseEntity<>(dto, HttpStatus.OK);

	}
	
}
