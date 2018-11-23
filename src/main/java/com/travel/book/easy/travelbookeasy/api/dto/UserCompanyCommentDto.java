package com.travel.book.easy.travelbookeasy.api.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.travel.book.easy.travelbookeasy.db.model.User;
import com.travel.book.easy.travelbookeasy.db.model.UserCompanyComment;
import com.travel.book.easy.travelbookeasy.util.TravelBookEasyApp;
import com.travel.book.easy.travelbookeasy.util.UserUtil;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCompanyCommentDto {

	private long id;

	private UserDto creator;

	private String comment;

	private long likes;

	private long dislikes;

	private Boolean isCurrentUserAlreadyVote;
	
	private Boolean isCurrentUserCreatorOfComment;

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
	private Date timestamp;

	public UserCompanyCommentDto() {
		super();
	}

	public UserCompanyCommentDto(long id, UserDto creator, String comment, long likes, long dislikes, Boolean isCurrentUserAlreadyVote,
			Boolean isCurrentUserCreatorOfComment, Date timestamp) {
		super();
		this.id = id;
		this.creator = creator;
		this.comment = comment;
		this.likes = likes;
		this.dislikes = dislikes;
		this.isCurrentUserAlreadyVote = isCurrentUserAlreadyVote;
		this.isCurrentUserCreatorOfComment = isCurrentUserCreatorOfComment;
		this.timestamp = timestamp;
	}

	public static UserCompanyCommentDto of(UserCompanyComment userCompanyComment) {
		return TravelBookEasyApp.ofNullable(userCompanyComment,
				ucc -> UserCompanyCommentDto.builder().id(ucc.getId()).creator(UserDto.of(ucc.getCreator()))
						.comment(ucc.getComment()).likes(ucc.getLikes())
						.dislikes(ucc.getDislikes())
						.isCurrentUserAlreadyVote(ucc.getUserCompanyVotes() != null ? ucc.getUserCompanyVotes().stream().filter(u->u.getId().getUser().getId() == UserUtil.gerUserFromContext().getId()).findFirst().isPresent() : false)
						.isCurrentUserCreatorOfComment(ucc.getCreator().getId() == UserUtil.gerUserFromContext().getId())
						.timestamp(ucc.getTimestamp()).build());
	}

}
