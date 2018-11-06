package com.travel.book.easy.travelbookeasy.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.book.easy.travelbookeasy.api.dto.AuthorityDto;
import com.travel.book.easy.travelbookeasy.db.repository.UserRoleRepository;
import com.travel.book.easy.travelbookeasy.services.interfaces.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public List<AuthorityDto> getUserRoles(long id) {

		return userRoleRepository.findByUser(id).stream().map(ur -> new AuthorityDto(ur.getId().getRole().toString()))
				.collect(Collectors.toList());
	}

}
