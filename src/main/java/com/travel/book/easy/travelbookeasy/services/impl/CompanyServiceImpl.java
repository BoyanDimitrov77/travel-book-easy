package com.travel.book.easy.travelbookeasy.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.book.easy.travelbookeasy.api.common.ApiException;
import com.travel.book.easy.travelbookeasy.api.dto.CompanyDto;
import com.travel.book.easy.travelbookeasy.db.model.Company;
import com.travel.book.easy.travelbookeasy.db.model.User;
import com.travel.book.easy.travelbookeasy.db.model.UserCompanyRaiting;
import com.travel.book.easy.travelbookeasy.db.model.UserCompanyRaitingPk;
import com.travel.book.easy.travelbookeasy.db.repository.CompanyRepository;
import com.travel.book.easy.travelbookeasy.db.repository.UserCompanyRaitingRepository;
import com.travel.book.easy.travelbookeasy.db.repository.UserRepository;
import com.travel.book.easy.travelbookeasy.services.interfaces.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private UserCompanyRaitingRepository userCompanyRaitingRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public CompanyDto createCompanyRecord(CompanyDto companyDto) {

		Company company = new Company();
		company.setName(companyDto.getName());

		Company saveCompany = companyRepository.saveAndFlush(company);

		return CompanyDto.of(saveCompany);

	}

	@Override
	public CompanyDto raitingCompany(long userId, long companyId, BigDecimal raiting) {
		
		UserCompanyRaiting findUserCompanyRecord = userCompanyRaitingRepository.findUserCompanyRecord(userId, companyId);
		
		if(findUserCompanyRecord != null) {
			throw new ApiException("This user has already voted for this company");
		}
		
		User user = userRepository.findById(userId);
		
		if(user == null) {
			throw new ApiException("User not found");
		}
		
		Optional<Company> company = companyRepository.findById(companyId);
		
		if(!company.isPresent()) {
			throw new ApiException("Company not found");
		}
		
		UserCompanyRaitingPk pk = new UserCompanyRaitingPk(user, company.get());
		UserCompanyRaiting userCompanyRating = new UserCompanyRaiting();
		userCompanyRating.setId(pk);
		userCompanyRating.setRaiting(raiting);
		
		userCompanyRaitingRepository.saveAndFlush(userCompanyRating);
		
		BigDecimal calculateWeightedRating = calculateWeightedRating(company.get().getId());
		
		company.get().setRaiting(calculateWeightedRating);
		
		Company savedCompany = companyRepository.saveAndFlush(company.get());
		
		return CompanyDto.of(savedCompany);
	}

	private BigDecimal calculateWeightedRating(long companyId) {

		// weighted rating (WR) = (v ÷ (v+m)) × R + (m ÷ (v+m)) × C

		// m
		int minimumVotesRequired = 50;
		List<UserCompanyRaiting> ratingRecordsForCompany = userCompanyRaitingRepository.findAll().stream()
				.filter(ucr -> ucr.getId().getCompany().getId() == companyId).collect(Collectors.toList());
		// v
		int votes = ratingRecordsForCompany.size();
		// R
		BigDecimal averageRatingForMovie = ratingRecordsForCompany.stream().map(urc -> urc.getRaiting())
				.reduce(BigDecimal.ZERO, BigDecimal::add).divide(new BigDecimal(votes));
		// C
		BigDecimal averageRatingForAllCompanies = companyRepository.findAll().stream().map(c -> {
			if (c.getRaiting() != null) {
				return c.getRaiting();
			}
			return BigDecimal.ZERO;

		}).reduce(BigDecimal.ZERO, BigDecimal::add).divide(new BigDecimal(companyRepository.findAll().size()));

		BigDecimal sumVotesAndMinimumVotesRequired = new BigDecimal(votes).add(new BigDecimal(minimumVotesRequired));

		BigDecimal firstPart = new BigDecimal(votes).divide(sumVotesAndMinimumVotesRequired, 3, RoundingMode.HALF_UP)
				.multiply(averageRatingForMovie);

		BigDecimal secodPart = new BigDecimal(minimumVotesRequired)
				.divide(sumVotesAndMinimumVotesRequired, 2, RoundingMode.HALF_UP)
				.multiply(averageRatingForAllCompanies);

		BigDecimal weightedRating = firstPart.add(secodPart);

		return weightedRating;

	}

	@Override
	public CompanyDto getCompany(long companyId, long userId) {
		
		Optional<Company> company = companyRepository.findById(companyId);
		
		if(!company.isPresent()) {
			throw new ApiException("Company not found");
		}
		
		Optional<UserCompanyRaiting> isCurrentUserVotted = company.get().getUserCompanyRaitings().stream().filter(ucr->ucr.getId().getUser().getId()==userId).findFirst();
		
		return CompanyDto.of(company.get(),isCurrentUserVotted.isPresent());
	}

	@Override
	public List<CompanyDto> getAllCompany() {
		List<Company> findAllCompany = companyRepository.findAll();

		return findAllCompany.stream().map(c->CompanyDto.of(c)).collect(Collectors.toList());
	}

}
