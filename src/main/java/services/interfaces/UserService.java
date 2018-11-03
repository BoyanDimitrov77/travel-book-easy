package services.interfaces;

import api.dto.UserDto;
import db.model.User;

public interface UserService {

	UserDto findByEmail(String email);
	
	User activateUser(User user);
	
	void resetPassword(User dbUser, String password);
}
