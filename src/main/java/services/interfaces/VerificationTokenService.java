package services.interfaces;

import api.dto.UserDto;
import db.model.User;
import db.model.VerificationToken;

public interface VerificationTokenService {

	VerificationToken generateTokenForUser(User user);

	UserDto verifyToken(String token);

	VerificationToken findByUser(User user);

	VerificationToken findByToken(String token);

	String urlFromToken(VerificationToken token);

	void resetUserPassword(String token, String password);

	void deleteToken(VerificationToken dbToken);

	String urlFromToken(String token, String type);
}
