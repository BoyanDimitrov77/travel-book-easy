package com.travel.book.easy.travelbookeasy.api.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.travel.book.easy.travelbookeasy.api.common.ApiException;
import com.travel.book.easy.travelbookeasy.api.dto.BasicDto;
import com.travel.book.easy.travelbookeasy.api.dto.ChangeUserPasswordDto;
import com.travel.book.easy.travelbookeasy.api.dto.UpdateUserInformationDto;
import com.travel.book.easy.travelbookeasy.api.dto.UserDto;
import com.travel.book.easy.travelbookeasy.services.interfaces.UserService;
import com.travel.book.easy.travelbookeasy.util.UserUtil;

import it.ozimov.springboot.mail.service.exception.CannotSendEmailException;

@RestController
@RequestMapping(value = "users", produces = "application/json")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.GET,value="/hello")
	public String helloTest(SecurityContextHolder contex){
		
		return "Hello";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/resetPassword")
	public ResponseEntity<BasicDto<String>> RequestMethod(@RequestBody UserDto user) {

		try {
			userService.resetPasswrodRequest(user.getEmail());
		} catch (CannotSendEmailException e) {

			throw new ApiException(e);
		}
		return new ResponseEntity<>(new BasicDto<String>("Send verification token"),HttpStatus.OK);

	}

	/*@RequestMapping(method = RequestMethod.POST, value = "/uploadProfilePhoto")
	public ResponseEntity<PictureDto> uploadProfilePhoto(@RequestParam("file") MultipartFile file,
			SecurityContextHolder context) {

		PictureDto profilePicture = null;
		try {
			profilePicture = userService.uploadProfilePhoto(file, UserUtil.gerUserFromContext()).getProfilePicture();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ResponseEntity<>(profilePicture, HttpStatus.OK);
	}*/

	@RequestMapping(method = RequestMethod.POST, value = "/updatePersonalInformation")
	public ResponseEntity<UserDto> updatePersonalInformation(@RequestBody UpdateUserInformationDto dto,
			SecurityContextHolder contex) {

		UserDto updateUserInformationDto = userService.updateUserInformation(dto,
				UserUtil.gerUserFromContext().getId());

		return new ResponseEntity<>(updateUserInformationDto, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<UserDto> getUser(@PathVariable("id") long userId, SecurityContextHolder contex) {

		return new ResponseEntity<>(userService.findUser(userId), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getUser")
	public ResponseEntity<UserDto> getUser(SecurityContextHolder contex) {

		return new ResponseEntity<>(userService.findUser(UserUtil.gerUserFromContext().getId()), HttpStatus.OK);
	}

	/*@RequestMapping(method = RequestMethod.GET, value = "/accessTokenGD")
	public ResponseEntity<BasicDto<String>> getAccessTokenGD() {

		return new ResponseEntity<>(new BasicDto<>(userService.getAccessTokenGD()), HttpStatus.OK);
	}*/

	@RequestMapping(method = RequestMethod.PUT, value = "/changePassword")
	public ResponseEntity<BasicDto<String>> changePassword(@RequestBody ChangeUserPasswordDto changeUserPasswordDto,
			SecurityContextHolder contex) {

		String response = userService.chnageUserPassword(changeUserPasswordDto, UserUtil.gerUserFromContext().getId());

		return new ResponseEntity<>(new BasicDto<>(response), HttpStatus.OK);
	}
	
	
	
	
}
