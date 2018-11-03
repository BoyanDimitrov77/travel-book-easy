package services.impl;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import api.dto.UserDto;
import db.model.User;
import db.reposiory.UserRepository;
import services.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private Mapper mapper;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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
