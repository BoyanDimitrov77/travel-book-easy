package com.travel.book.easy.travelbookeasy.services.impl;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.travel.book.easy.travelbookeasy.api.dto.UserDto;
import com.travel.book.easy.travelbookeasy.db.model.User;
import com.travel.book.easy.travelbookeasy.db.repository.UserRepository;
import com.travel.book.easy.travelbookeasy.services.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private Mapper mapper;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public PasswordEncoder getPasswordEncoder(){
		return this.passwordEncoder;
	}
	
	@Override
    public UserDto findByEmail(String email) {
		UserDto result = mapper.map(userRepository.findByEmail(email), UserDto.class);
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

}
