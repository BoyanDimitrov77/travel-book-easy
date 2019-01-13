package com.travel.book.easy.travelbookeasy.api.dto;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.travel.book.easy.travelbookeasy.db.model.User;
import com.travel.book.easy.travelbookeasy.util.TravelBookEasyApp;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;

@ToString
@EqualsAndHashCode
@Builder
public class UserDto implements Principal {

	@Getter
	@Setter
	private long id;

	@Getter
	@Setter
	private String email;

	@Getter
	@Setter
	private String userName;

	@Getter
	@Setter
	private String fullName;

	@Getter
	@Setter
	private boolean enabled;

	private String password;

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	@Getter
	@Setter
	private List<String> userRole;

    @Getter
    @Setter
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date timestamp;

    @Getter
    @Setter
    private PictureDto profilePicture;

    @Tolerate
	public UserDto() {
	}

    @Override
    public String getName() {
        return fullName;
    }

    public static UserDto of(User user) {

    LocalDate now = LocalDate.now();

	return TravelBookEasyApp.ofNullable(user, u->UserDto.builder()
				.id(u.getId())
                .email(u.getEmail())
                .userName(u.getUserName())
                .fullName(u.getFullName())
                .enabled(u.isEnabled())
                .profilePicture(PictureDto.of(u.getProfilePicture()))
                .userRole(u.getUserRole() != null ? u.getUserRole().stream().map(ur->ur.getId().getRole().toString()).collect(Collectors.toList()) : new ArrayList<>())
                .timestamp(u.getTimestamp())
                .build());
    }
}
