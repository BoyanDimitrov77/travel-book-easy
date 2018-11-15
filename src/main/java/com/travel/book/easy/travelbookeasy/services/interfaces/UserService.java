package com.travel.book.easy.travelbookeasy.services.interfaces;

import java.text.ParseException;

import com.travel.book.easy.travelbookeasy.api.dto.ChangeUserPasswordDto;
import com.travel.book.easy.travelbookeasy.api.dto.UpdateUserInformationDto;
import com.travel.book.easy.travelbookeasy.api.dto.UserDto;
import com.travel.book.easy.travelbookeasy.db.model.User;
import com.travel.book.easy.travelbookeasy.db.model.UserRoleEnum;

import it.ozimov.springboot.mail.service.exception.CannotSendEmailException;

public interface UserService {

	UserDto findByEmail(String email);

	UserDto register(UserDto userDto) throws ParseException, CannotSendEmailException;
	
	User addRole(User user, UserRoleEnum role);
	
	User activateUser(User user);
	
	void resetPassword(User dbUser, String password);

	void resetPasswrodRequest(String userEmail) throws CannotSendEmailException;

	//UserDto uploadProfilePhoto(MultipartFile file, UserDto userDto )throws IOException;

	UserDto updateUserInformation(UpdateUserInformationDto dto , long userId);

	UserDto findUser(long userId);

	//String getAccessTokenGD();

	String chnageUserPassword(ChangeUserPasswordDto dto, long userId);

	boolean checkIfEmailExist(String email);

	boolean checkIfUsernameExist(String username);
}
