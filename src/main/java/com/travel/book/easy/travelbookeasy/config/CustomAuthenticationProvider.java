package com.travel.book.easy.travelbookeasy.config;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.travel.book.easy.travelbookeasy.api.common.ApiException;
import com.travel.book.easy.travelbookeasy.api.dto.UserDto;
import com.travel.book.easy.travelbookeasy.services.interfaces.UserRoleService;
import com.travel.book.easy.travelbookeasy.services.interfaces.UserService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Value("${validation.token.expire}")
    private int minutes;

    
    private UserService userService;
    
//    @Autowired
//    private FacebookService facebookService;

    
    private UserRoleService userRoleService;
    
    private PasswordEncoder passwordEncoder;
    
    public CustomAuthenticationProvider(UserService userService,UserRoleService userRoleService,
    		@Lazy PasswordEncoder  passwordEncoder){
    	this.userService = userService;
    	this.userRoleService = userRoleService;
    	this.passwordEncoder = passwordEncoder;
    	
    }
    
     /*public void setUserService(UserService userService) {
		this.userService = userService;
	}
     
     public void setUserRoleService(UserRoleService userRoleService) {
		this.userRoleService = userRoleService;
	}*/
     
   public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        UserDto user = null;

        
        if(StringUtils.isEmpty(username) && StringUtils.isEmpty(password)){
        	throw new ApiException("Authorization Exception: Empty username and password.");
        }
        if (StringUtils.isEmpty(username)) { //facebook token auth
            /*org.springframework.social.facebook.api.User userProfile;
        	System.out.println("FACEBOOK: authenticate: with token: " + password);
            Facebook facebook = new FacebookTemplate(password);
            try {
            	System.out.println("FACEBOOK: fetching fields");
            	String [] FB_FIELDS = facebookService.getUserFieldsMeta();
                userProfile = facebook.fetchObject("me", org.springframework.social.facebook.api.User.class, FB_FIELDS);
            } catch (Exception iae) {
            	System.out.println("FACEBOOK: invalid token");
                throw new ApiException("Invalid facebook connection with token: " + password);
            }

            String facebookId = userProfile.getId();
            user = userService.getUserByFacebookId(facebookId);
            if(user == null || user.isBanned()) {
            	System.out.println("FACEBOOK: username with facebookID not found-exception");
                throw new BadCredentialsException("Username not found.");
            }*/
		} else { // regular authentication
			user = userService.findByEmail(username);
			if (user == null) {
				//System.out.println("FACEBOOK: cant find user by email");
				throw new BadCredentialsException("Username not found.");
			}

            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new BadCredentialsException("Wrong password.");
            }

            if (!user.isEnabled() && ZonedDateTime.now().isAfter(ZonedDateTime.ofInstant(user.getTimestamp().toInstant(), ZoneId.systemDefault()).plusMinutes(minutes))) {
				throw new ApiException("Account demo version has expired. Please verify your account.");
            }
        }
        
        user.setPassword("");

        return new UsernamePasswordAuthenticationToken(user, password, getAuthorities(user));
    }

    public boolean supports(Class<?> arg0) {
        return true;
    }
    
    private List<? extends GrantedAuthority> getAuthorities(UserDto user) {
        List<? extends GrantedAuthority> result = userRoleService.getUserRoles(user.getId());
        return result;
    }
}