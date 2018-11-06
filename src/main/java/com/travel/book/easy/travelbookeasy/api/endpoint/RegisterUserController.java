package com.travel.book.easy.travelbookeasy.api.endpoint;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.travel.book.easy.travelbookeasy.api.dto.UserDto;
import com.travel.book.easy.travelbookeasy.services.interfaces.UserService;

import it.ozimov.springboot.mail.service.exception.CannotSendEmailException;

@Transactional
@RestController
@RequestMapping(value = "register", produces="application/json", consumes="application/json")
public class RegisterUserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserDto> create(@RequestBody UserDto user, WebRequest request) throws ParseException ,CannotSendEmailException {
        UserDto result = userService.register(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
