ALTER TABLE `users`
ADD COLUMN `profile_picture` VARCHAR(36) DEFAULT NULL,
ADD CONSTRAINT `profile_picutre_picture` FOREIGN KEY (`profile_picture`) REFERENCES picture(`id`);