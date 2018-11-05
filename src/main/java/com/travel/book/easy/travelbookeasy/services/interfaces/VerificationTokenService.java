package com.travel.book.easy.travelbookeasy.services.interfaces;

import com.travel.book.easy.travelbookeasy.api.dto.UserDto;
import com.travel.book.easy.travelbookeasy.db.model.User;
import com.travel.book.easy.travelbookeasy.db.model.VerificationToken;

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
