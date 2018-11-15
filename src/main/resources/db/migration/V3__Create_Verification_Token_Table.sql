CREATE TABLE IF NOT EXISTS `verification_token`(
   `id` INT(11) NOT NULL AUTO_INCREMENT,
  `token` VARCHAR(64) NOT NULL,
  `user_id` INT(11) NOT NULL,
  `expiry_date` DATETIME NOT NULL,
  `verified` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ix_verification_token` (`user_id`),
  CONSTRAINT `FK_Verification_Token_User` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
	)ENGINE=InnoDB DEFAULT CHARSET=utf8;