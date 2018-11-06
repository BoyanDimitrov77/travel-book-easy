package com.travel.book.easy.travelbookeasy.services.interfaces;

import com.travel.book.easy.travelbookeasy.api.dto.UserDto;
import com.travel.book.easy.travelbookeasy.db.model.User;

public interface UserService {

	UserDto findByEmail(String email);
	
	User activateUser(User user);
	
	void resetPassword(User dbUser, String password);
}
