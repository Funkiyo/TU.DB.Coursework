DROP DATABASE IF EXISTS `parkings`;

CREATE DATABASE `parkings`;

USE `parkings`;

CREATE TABLE `car_companies`(
    `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `car_models`(
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `company_id` TINYINT UNSIGNED NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY ( `company_id`)
		REFERENCES `car_companies`( `id` )
		ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT car_company_model UNIQUE (`name`,`company_id`)
) ENGINE = InnoDB;

CREATE TABLE `cars`(
    `reg_num` CHAR(10) NOT NULL,
    `model_id` INT UNSIGNED NOT NULL,
    `records_count` TINYINT UNSIGNED NOT NULL DEFAULT 0,
    PRIMARY KEY (`reg_num`)
) ENGINE = InnoDB;

CREATE TABLE `register` (
	`id` INT UNSIGNED NOT NULL  AUTO_INCREMENT ,
	`car_num` CHAR(10) NOT NULL ,
	`time_entered` DATETIME NOT NULL ,
	`time_left` DATETIME NULL DEFAULT NULL,
    `fine` FLOAT NOT NULL,
    `description` VARCHAR(1000) NOT NULL,
	PRIMARY KEY ( `id` ),
    FOREIGN KEY (`car_num`)
        REFERENCES `cars`(`reg_num`)
) ENGINE = InnoDB;

INSERT INTO `parkings`.`car_companies` (`id`, `name`) VALUES (NULL, 'BMW'), (NULL, 'Ford');
INSERT INTO `parkings`.`car_companies` (`id`, `name`) VALUES (NULL, 'Opel'), (NULL, 'VW');

INSERT INTO `parkings`.`car_models` (`id`, `name`, `company_id`) VALUES (NULL, 'E36', '1'), (NULL, 'E39', '1');
INSERT INTO `parkings`.`car_models` (`id`, `name`, `company_id`) VALUES (NULL, 'Focus', '2'), (NULL, 'Mustang', '2');
INSERT INTO `parkings`.`car_models` (`id`, `name`, `company_id`) VALUES (NULL, 'Astra', '3'), (NULL, 'Corsa', '3');
INSERT INTO `parkings`.`car_models` (`id`, `name`, `company_id`) VALUES (NULL, 'Golf', '4'), (NULL, 'Polo', '4');
