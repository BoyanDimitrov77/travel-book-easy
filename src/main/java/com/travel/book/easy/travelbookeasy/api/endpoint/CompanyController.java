package com.travel.book.easy.travelbookeasy.api.endpoint;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

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
import org.springframework.web.multipart.MultipartFile;

import com.travel.book.easy.travelbookeasy.api.common.ApiException;
import com.travel.book.easy.travelbookeasy.api.dto.CompanyDto;
import com.travel.book.easy.travelbookeasy.services.interfaces.CompanyService;
import com.travel.book.easy.travelbookeasy.util.UserUtil;

@RestController
@RequestMapping(value = "company", produces = "application/json")
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@RequestMapping(method = RequestMethod.POST, value = "/create/createCompanyRecord")
	@Transactional
	public ResponseEntity<CompanyDto> createAirlineRecord(@RequestBody CompanyDto airlineDto) {

		CompanyDto dto = companyService.createCompanyRecord(airlineDto);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/ratingCompany/{companyId}")
	@Transactional
	public ResponseEntity<CompanyDto> ratingCompany(@PathVariable(name = "companyId") long companyId,
			@RequestParam(value = "rating") String rating, SecurityContextHolder contex) {

		CompanyDto dto = companyService.ratingCompany(UserUtil.gerUserFromContext().getId(), companyId,
				new BigDecimal(rating));

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{companyId}")
	public ResponseEntity<CompanyDto> getCompany(@PathVariable(name = "companyId") long companyId,
			SecurityContextHolder contex) {

		CompanyDto dto = companyService.getCompany(companyId, UserUtil.gerUserFromContext().getId());

		return new ResponseEntity<>(dto, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/all")
	public ResponseEntity<List<CompanyDto>> getAllCompanies(SecurityContextHolder contex) {

		List<CompanyDto> dto = companyService.getAllCompany();

		return new ResponseEntity<>(dto, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.POST, value = "/createComment/{companyId}")
	@Transactional
	public ResponseEntity<CompanyDto> createCompanyComment(@PathVariable(name = "companyId") long companyId,
			@RequestParam("comment") String comment, SecurityContextHolder contex) {

		CompanyDto commnetsCompany = companyService.commnetsCompany(UserUtil.gerUserFromContext().getId(), companyId,
				comment);

		return new ResponseEntity<>(commnetsCompany, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.POST, value = "/voteComment/{commentId}")
	@Transactional
	public ResponseEntity<CompanyDto> voteComment(@PathVariable(name = "commentId") long commentId,
			@RequestParam("isLike") String isLike, SecurityContextHolder contex) {

		companyService.voteComment(UserUtil.gerUserFromContext().getId(), commentId, Boolean.parseBoolean(isLike));

		return new ResponseEntity<>(HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.POST, value = "/uploadCompanyLogo/{companyId}")
	@Transactional
	public ResponseEntity<CompanyDto> uploadAirlineLog(@RequestParam("file") MultipartFile file,
			@PathVariable("companyId") long airlineId) {

		CompanyDto companyDto = null;
		try {
			companyDto = companyService.uploadCompanyLogo(file, airlineId);
		} catch (IOException e) {
			throw new ApiException(e);
		}

		return new ResponseEntity<>(companyDto, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/allCompanyOrderByRating")
	@Transactional
	public ResponseEntity<List<CompanyDto>> getAllCompanyOrderByRating() {

		List<CompanyDto> dto = companyService.getAllCompanyOrderByRating();

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/updateCompany/{companyId}")
	@Transactional
	public ResponseEntity<CompanyDto> updateCompany(@PathVariable(name = "companyId") long companyId,
			@RequestParam("name") String name) {

		CompanyDto dto = companyService.updateComany(companyId, name);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

}
