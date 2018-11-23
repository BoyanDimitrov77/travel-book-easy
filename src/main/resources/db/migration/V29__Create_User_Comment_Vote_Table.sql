CREATE TABLE IF NOT EXISTS `user_comment_vote`(
	`user_id` INT(11) NOT NULL,
	`user_company_comments_id` INT(11) NOT NULL,
	UNIQUE KEY `user_vote` (`user_id`, `user_company_comments_id`),
	CONSTRAINT `FK_user_comment_vote_users` FOREIGN KEY(`user_id`) REFERENCES `users`(`id`),
	CONSTRAINT `FK_user_comment_vote_user_company_commnets` FOREIGN KEY(`user_company_comments_id`) REFERENCES `user_company_comments`(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;