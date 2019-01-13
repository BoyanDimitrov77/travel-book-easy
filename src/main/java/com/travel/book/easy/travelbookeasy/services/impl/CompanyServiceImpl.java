package com.travel.book.easy.travelbookeasy.services.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.travel.book.easy.travelbookeasy.api.common.ApiException;
import com.travel.book.easy.travelbookeasy.api.dto.CompanyDto;
import com.travel.book.easy.travelbookeasy.api.dto.PictureDto;
import com.travel.book.easy.travelbookeasy.db.model.Company;
import com.travel.book.easy.travelbookeasy.db.model.Picture;
import com.travel.book.easy.travelbookeasy.db.model.User;
import com.travel.book.easy.travelbookeasy.db.model.UserCompanyComment;
import com.travel.book.easy.travelbookeasy.db.model.UserCompanyRating;
import com.travel.book.easy.travelbookeasy.db.model.UserCompanyRatingPk;
import com.travel.book.easy.travelbookeasy.db.model.UserCompanyVote;
import com.travel.book.easy.travelbookeasy.db.model.UserCompanyVotePk;
import com.travel.book.easy.travelbookeasy.db.repository.CompanyRepository;
import com.travel.book.easy.travelbookeasy.db.repository.UserCompanyCommentsRepository;
import com.travel.book.easy.travelbookeasy.db.repository.UserCompanyRatingRepository;
import com.travel.book.easy.travelbookeasy.db.repository.UserRepository;
import com.travel.book.easy.travelbookeasy.services.interfaces.CompanyService;
import com.travel.book.easy.travelbookeasy.services.interfaces.PictureService;
import com.travel.book.easy.travelbookeasy.util.PictureUtil;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private UserCompanyRatingRepository userCompanyRatingRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserCompanyCommentsRepository userCompanyCommentsRepository;

	@Autowired
	private PictureService pictureService;

	@Override
	public CompanyDto createCompanyRecord(CompanyDto companyDto) {

		Company company = new Company();
		company.setName(companyDto.getName());

		Company saveCompany = companyRepository.saveAndFlush(company);

		return CompanyDto.of(saveCompany);

	}

	@Override
	public CompanyDto ratingCompany(long userId, long companyId, BigDecimal rating) {
		
		UserCompanyRating findUserCompanyRecord = userCompanyRatingRepository.findUserCompanyRecord(userId, companyId);
		
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
		
		UserCompanyRatingPk pk = new UserCompanyRatingPk(user, company.get());
		UserCompanyRating userCompanyRating = new UserCompanyRating();
		userCompanyRating.setId(pk);
		userCompanyRating.setRating(rating);
		
		userCompanyRatingRepository.saveAndFlush(userCompanyRating);
		
		BigDecimal calculateWeightedRating = calculateWeightedRating(company.get().getId());
		
		company.get().setRating(calculateWeightedRating);
		
		Company savedCompany = companyRepository.saveAndFlush(company.get());
		
		Optional<UserCompanyRating> isCurrentUserVotted = savedCompany.getUserCompanyRatings().stream().filter(ucr->ucr.getId().getUser().getId()==userId).findFirst();
		
		return CompanyDto.of(savedCompany,isCurrentUserVotted.isPresent());
	}

	private BigDecimal calculateWeightedRating(long companyId) {

		// weighted rating (WR) = (v ÷ (v+m)) × R + (m ÷ (v+m)) × C

		// m
		int minimumVotesRequired = 10;
		List<UserCompanyRating> ratingRecordsForCompany = userCompanyRatingRepository.findAll().stream()
				.filter(ucr -> ucr.getId().getCompany().getId() == companyId).collect(Collectors.toList());
		// v
		int votes = ratingRecordsForCompany.size();
		// R
		BigDecimal averageRatingForMovie = ratingRecordsForCompany.stream().map(urc -> urc.getRating())
				.reduce(BigDecimal.ZERO, BigDecimal::add).divide(new BigDecimal(votes));
		// C
		BigDecimal averageRatingForAllCompanies = companyRepository.findAll().stream().map(c -> {
			if (c.getRating() != null) {
				return c.getRating();
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

		Optional<UserCompanyRating> isCurrentUserVotted = company.get().getUserCompanyRatings().stream().filter(ucr->ucr.getId().getUser().getId()==userId).findFirst();

		return CompanyDto.of(company.get(),isCurrentUserVotted.isPresent());
	}

	@Override
	public List<CompanyDto> getAllCompany() {
		List<Company> findAllCompany = companyRepository.findAll();

		return findAllCompany.stream().map(c->CompanyDto.of(c)).collect(Collectors.toList());
	}

	@Override
	public CompanyDto commnetsCompany(long userId, long companyId, String comment) {

		User user = userRepository.findById(userId);

		if (user == null) {
			throw new ApiException("User not found");
		}

		Optional<Company> company = companyRepository.findById(companyId);

		if (!company.isPresent()) {
			throw new ApiException("Company not found");
		}

		UserCompanyComment userCompanyComment = new UserCompanyComment();
		userCompanyComment.setCreator(user);
		userCompanyComment.setCompany(company.get());
		userCompanyComment.setComment(comment);

		userCompanyCommentsRepository.saveAndFlush(userCompanyComment);

		return CompanyDto.of(company.get());
	}

	@Override
	public void voteComment(long userId, long commentId, boolean isLike) {

		User user = userRepository.findById(userId);

		if (user == null) {
			throw new ApiException("User not found");
		}

		Optional<UserCompanyComment> comment = userCompanyCommentsRepository.findById(commentId);
		if (!comment.isPresent()) {
			throw new ApiException("Comment not found");
		}

		boolean isUserVoteForThisComment = comment.get().getUserCompanyVotes().stream()
				.filter(u -> u.getId().getUser().getId() == userId).findFirst().isPresent();
		if (isUserVoteForThisComment) {
			throw new ApiException("User has already voted for this comment");
		}

		if (comment.get().getCreator().getId() == userId) {
			throw new ApiException("Comment's creator can't vote.");
		}

		if (isLike) {
			long likes = comment.get().getLikes();
			comment.get().setLikes(likes + 1);
		} else {
			long dislikes = comment.get().getDislikes();
			comment.get().setDislikes(dislikes + 1);
		}

		UserCompanyVotePk id = new UserCompanyVotePk(user, comment.get());
		UserCompanyVote userCompanyVote = new UserCompanyVote();
		userCompanyVote.setId(id);

		comment.get().getUserCompanyVotes().add(userCompanyVote);

		userCompanyCommentsRepository.saveAndFlush(comment.get());

	}

	@Override
	public CompanyDto uploadCompanyLogo(MultipartFile logo, long companyId) throws IOException {
		Optional<Company> company = companyRepository.findById(companyId);

		if (!company.isPresent()) {
			throw new ApiException("Company not found");
		}

		Company savedCompany = setCompanyLogo(logo, company.get(), Company::setCompanyLogo);

		return CompanyDto.of(savedCompany);
	}

	private Company setCompanyLogo(MultipartFile logo, Company company, BiConsumer<Company, Picture> logoSetter)
			throws IOException {

		PictureDto saveLogo = pictureService.savePicure(PictureUtil.getImageFromMultipartFile(logo), company.getName());
		logoSetter.accept(company, pictureService.getPictureById(saveLogo.getId()));

		Company savedCompany = companyRepository.saveAndFlush(company);

		return savedCompany;

	}

	@Override
	public List<CompanyDto> getAllCompanyOrderByRating() {

		return companyRepository.findAllCompanyOrderByRating().stream().map(c -> CompanyDto.of(c))
				.collect(Collectors.toList());
	}

	@Override
	public CompanyDto updateComany(long companyId, String name) {

		Optional<Company> company = companyRepository.findById(companyId);

		if (!company.isPresent()) {
			throw new ApiException("Company not found");
		}

		company.get().setName(name);

		Company savedCompany = companyRepository.saveAndFlush(company.get());

		return CompanyDto.of(savedCompany);
	}

}
