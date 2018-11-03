package api.dto;

import org.springframework.security.core.GrantedAuthority;

import lombok.Setter;
import lombok.ToString;

@ToString
public class AuthorityDto implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @Setter
    String authority;

    @Override
    public String getAuthority() {
        return authority;
    }

    public AuthorityDto(String authority) {
        this.authority = authority;
    }
}
