package com.travel.book.easy.travelbookeasy.services.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.function.BiConsumer;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.travel.book.easy.travelbookeasy.api.common.ApiException;
import com.travel.book.easy.travelbookeasy.api.dto.ChangeUserPasswordDto;
import com.travel.book.easy.travelbookeasy.api.dto.PictureDto;
import com.travel.book.easy.travelbookeasy.api.dto.UpdateUserInformationDto;
import com.travel.book.easy.travelbookeasy.api.dto.UserDto;
import com.travel.book.easy.travelbookeasy.db.model.Picture;
import com.travel.book.easy.travelbookeasy.db.model.User;
import com.travel.book.easy.travelbookeasy.db.model.UserRole;
import com.travel.book.easy.travelbookeasy.db.model.UserRoleEnum;
import com.travel.book.easy.travelbookeasy.db.model.UserRolePk;
import com.travel.book.easy.travelbookeasy.db.model.VerificationToken;
import com.travel.book.easy.travelbookeasy.db.repository.UserRepository;
import com.travel.book.easy.travelbookeasy.db.repository.UserRoleRepository;
import com.travel.book.easy.travelbookeasy.services.interfaces.LocationService;
import com.travel.book.easy.travelbookeasy.services.interfaces.MailService;
import com.travel.book.easy.travelbookeasy.services.interfaces.PictureService;
import com.travel.book.easy.travelbookeasy.services.interfaces.UserService;
import com.travel.book.easy.travelbookeasy.services.interfaces.VerificationTokenService;
import com.travel.book.easy.travelbookeasy.util.PictureUtil;

import it.ozimov.springboot.mail.service.exception.CannotSendEmailException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private Mapper mapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private VerificationTokenService verificationTokenService;
	
	@Autowired
	private MailService mailService;

	@Autowired
	private PictureService pictureService;

	@Autowired
	private LocationService locationService;

	public PasswordEncoder getPasswordEncoder(){
		return this.passwordEncoder;
	}
	
	@Override
    public UserDto findByEmail(String email) {
		UserDto result = mapper.map(userRepository.findByEmail(email), UserDto.class);
		return result;
	}

	@Override
	@Transactional
	public UserDto register(UserDto userDto) throws ParseException, CannotSendEmailException {

		if (checkIfEmailExist(userDto.getEmail())) {
			throw new ApiException("User with email already exists");
		}
		if (checkIfUsernameExist(userDto.getUserName())) {
			throw new ApiException("User with userName already exists");
		}

		return processRegularRegistration(userDto);
	}
	
	private UserDto processRegularRegistration(UserDto userDto) throws CannotSendEmailException {
		User savedUser;
		
		User userModel = mapper.map(userDto, User.class);
		userModel.setEnabled(false);
		userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
		userModel.setUserName(userDto.getUserName());
		userModel.setTimestamp(new Date());

		savedUser = userRepository.save(userModel);
		savedUser = addRole(savedUser, UserRoleEnum.ROLE_USER);
		
		VerificationToken token = verificationTokenService.generateTokenForUser(savedUser);
		String url = verificationTokenService.urlFromToken(token);

		mailService.sendEmailConfirmation(savedUser.getEmail(), url);

		return UserDto.of(savedUser);
	}
	
	@Override
    public User addRole(User user, UserRoleEnum role) {
		UserRole userRole = new UserRole();
		userRole.setId(UserRolePk.builder().user(user).role(role).build());
		userRoleRepository.save(userRole);
		User result = userRepository.findByEmail(user.getEmail());
		return result;
	}
	
	@Override
    public User activateUser(User user) {

	    user.setEnabled(true);
		userRepository.save(user);

		return user;
	}
	
	@Override
    public void resetPassword(User dbUser, String password) {
		dbUser.setPassword(passwordEncoder.encode(password));
		userRepository.save(dbUser);
	}

	@Override
	public void resetPasswrodRequest(String userEmail) throws CannotSendEmailException {

		User user = userRepository.findByEmail(userEmail);
		if (user == null) {
			throw new ApiException("User not found with such and email");

		}
		generateToken(user);
	}

	private void generateToken(User user) throws CannotSendEmailException {

		VerificationToken oldToken = verificationTokenService.findByUser(user);
		if (oldToken != null) {
			verificationTokenService.deleteToken(oldToken);
		}

		VerificationToken newToken = verificationTokenService.generateTokenForUser(user);
		String url = verificationTokenService.urlFromToken(newToken);

		mailService.sendEmailResetPassord(user.getEmail(), url);

	}

	@Override
	public UserDto uploadProfilePhoto(MultipartFile file, UserDto userDto) throws IOException {
		User user = setUserProfilePicture(file, userDto, User::setProfilePicture);

		return UserDto.of(user);
	}

	private User setUserProfilePicture(MultipartFile file, UserDto userDto, BiConsumer<User, Picture> pictureSetter)
			throws IOException {
		PictureDto savePicure = pictureService.savePicure(PictureUtil.getImageFromMultipartFile(file),
				userDto.getUserName());
		User user = userRepository.findById(userDto.getId());
		pictureSetter.accept(user, pictureService.getPictureById(savePicure.getId()));
		User saveUser = userRepository.saveAndFlush(user);

		return saveUser;

	}

	@Override
	public UserDto updateUserInformation(UpdateUserInformationDto dto, long userId) {

		User user = userRepository.findById(userId);

		if (dto.getEmail() != null) {
			user.setEmail(dto.getEmail());
		}

		if (dto.getFullName() != null) {
			user.setFullName(dto.getFullName());
		}

		User saveUser = userRepository.saveAndFlush(user);

		return UserDto.of(saveUser);
	}

	@Override
	public UserDto findUser(long userId) {
		return UserDto.of(userRepository.findById(userId));
	}

	@Override
	public String chnageUserPassword(ChangeUserPasswordDto dto, long userId) {

		User user = userRepository.findById(userId);

		if (user == null) {
			throw new ApiException("User not found");
		}

		if (dto.getOldPassword() != null && !dto.getOldPassword().isEmpty()) {
			if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
				throw new ApiException("Wrong password");
			}

			if (dto.getNewPassword() != null && !dto.getNewPassword().isEmpty()) {
				user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
				userRepository.saveAndFlush(user);
				return "Password change successful";
			}
		}
		return "Something went wrong please try again!";

	}

	@Override
	public boolean checkIfEmailExist(String email) {

		User user = userRepository.findByEmail(email);

		if (user != null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean checkIfUsernameExist(String userName) {

		User user = userRepository.findByUserName(userName);

		if (user != null) {
			return true;
		}
		return false;
	}

}
