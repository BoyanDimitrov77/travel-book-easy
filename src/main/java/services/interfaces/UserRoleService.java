package services.interfaces;

import java.util.List;

import api.dto.AuthorityDto;

public interface UserRoleService {

	List<AuthorityDto> getUserRoles(long id);
}
