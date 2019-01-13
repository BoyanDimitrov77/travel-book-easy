package com.travel.book.easy.travelbookeasy.services.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.book.easy.travelbookeasy.api.common.ApiException;
import com.travel.book.easy.travelbookeasy.api.dto.UserDto;
import com.travel.book.easy.travelbookeasy.db.model.User;
import com.travel.book.easy.travelbookeasy.db.model.VerificationToken;
import com.travel.book.easy.travelbookeasy.db.repository.VerificationTokenRepository;
import com.travel.book.easy.travelbookeasy.services.interfaces.UserService;
import com.travel.book.easy.travelbookeasy.services.interfaces.VerificationTokenService;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

	@Value("${validation.token.expire}")
    private int minutes;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private UserService userService;
    
    @Autowired
    private Mapper mapper;
    
    @Value("${application.base.url}")
    private String baseUrl;

    @Value("${server.servlet.contextPath}")
    private String restUrl;

	@Value("${application.url.reset.password}")
	private String resetUrlForPassword;

    private final static String mailUrl = "http://localhost:8080/api/v1/verification";


    @Override
    public VerificationToken generateTokenForUser(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken =
                VerificationToken.builder().user(user).token(token).expiryDate(calculateExpiryDate(minutes)).build();
        return verificationTokenRepository.save(verificationToken);
    }

    @Override
    @Transactional
    public UserDto verifyToken(String token) {
        VerificationToken dbToken = findByToken(token);
        if (dbToken == null) {
            throw new ApiException("Invalid token");
        }

        if (dbToken.getExpiryDate().before(new Date())) {
            throw new ApiException("The token has expired");
        }

        User result = userService.activateUser(dbToken.getUser());
        verificationTokenRepository.delete(dbToken);
        return mapper.map(result, UserDto.class);
    }

    @Override
    public VerificationToken findByUser(User user) {
        return verificationTokenRepository.findByUser(user);
    }

    @Override
    public VerificationToken findByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    @Override
    public String urlFromToken(VerificationToken token) {
        StringBuilder builder = new StringBuilder();
        builder.append(baseUrl);
        builder.append(restUrl);
        builder.append("/verification/");
        builder.append(token.getToken());
        return builder.toString();
    }


    @Override
    public String urlFromToken(String token, String type){

        StringBuilder builder = new StringBuilder();
        builder.append(mailUrl);
        builder.append(type);
        builder.append("?token=");
        builder.append(token);
        return builder.toString();
    }

    @Override
    public String urlFromToken(String token){

        StringBuilder builder = new StringBuilder();
        builder.append(resetUrlForPassword);
        builder.append(token);
        return builder.toString();
    }


    @Override
    public void resetUserPassword(String token, String password) {
        VerificationToken dbToken = findByToken(token);
        if (dbToken == null) {
            //throw new ApiException("Invalid token");
        }
        if (dbToken.getExpiryDate().before(new Date())) {
            //throw new ApiException("The token has expired");
        }

        User dbUser = dbToken.getUser();
        userService.resetPassword(dbUser, password);
        // Delete the token
        verificationTokenRepository.delete(dbToken);
    }
    
    @Override
    public void deleteToken(VerificationToken dbToken) {
        verificationTokenRepository.delete(dbToken);
        verificationTokenRepository.flush();
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

}
