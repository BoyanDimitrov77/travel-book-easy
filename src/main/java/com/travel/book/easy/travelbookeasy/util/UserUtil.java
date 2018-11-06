package com.travel.book.easy.travelbookeasy.util;

import org.springframework.security.core.context.SecurityContextHolder;

import com.travel.book.easy.travelbookeasy.api.dto.UserDto;

public class UserUtil {

	public static UserDto gerUserFromContext(){
		return (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
