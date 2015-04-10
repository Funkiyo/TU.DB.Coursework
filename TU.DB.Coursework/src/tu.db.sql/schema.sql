DROP DATABASE IF EXISTS `parkings`;

CREATE DATABASE `parkings`;

USE `parkings`;

CREATE TABLE `car_companies`(
    `id` UNSIGNED TINYINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `car_models`(
    `id` UNSIGNED INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `company_id` UNSIGNED TINYINT NOT NULL,
    PRIMARY KEY (`id`)
    FOREIGN KEY ( `company_id`)
		REFERENCES `car_companies`( `id` )
		ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT car_company_model UNIQUE (`name`,`company_id`)
) ENGINE = InnoDB;

CREATE TABLE `cars`(
    `reg_num` CHAR(10) NOT NULL,
    `model_id` UNSIGNED INT NOT NULL,
    PRIMARY KEY (`reg_num`)
) ENGINE = InnoDB;

CREATE TABLE `register` (
	`id` UNSIGNED INT NOT NULL  AUTO_INCREMENT ,
	`car_num` CHAR(10) NOT NULL ,
	`time_entered` DATETIME NOT NULL ,
	`time_left` DATETIME NULL DEFAULT NULL,
    `fine` FLOAT NOT NULL,
    `description` VARCHAR(1000) NOT NULL,
	PRIMARY KEY ( `id` ),
    FOREIGN KEY (`car_num`)
        REFERENCES `cars`(`reg_num`)
) ENGINE = InnoDB;

