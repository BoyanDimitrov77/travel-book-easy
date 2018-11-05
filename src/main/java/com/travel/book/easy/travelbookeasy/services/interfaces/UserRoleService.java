package com.travel.book.easy.travelbookeasy.services.interfaces;

import java.util.List;

import com.travel.book.easy.travelbookeasy.api.dto.AuthorityDto;

public interface UserRoleService {

	List<AuthorityDto> getUserRoles(long id);
}
