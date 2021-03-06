-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema bosetterne
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `bosetterne` ;

-- -----------------------------------------------------
-- Schema bosetterne
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bosetterne` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `bosetterne` ;

-- -----------------------------------------------------
-- Table `bosetterne`.`SPILLER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bosetterne`.`SPILLER` (
  `brukernavn` VARCHAR(15) NOT NULL,
  `kallenavn` VARCHAR(25) NOT NULL,
  `farge` CHAR(7) NOT NULL,
  `epost` VARCHAR(45) NOT NULL,
  `passord` VARCHAR(45) NOT NULL,
  `dato_registrert` DATE NOT NULL,
  `innlogget` BIT(1) NOT NULL DEFAULT b'0',
  `i_spill` BIT(1) NOT NULL DEFAULT b'0',
  `dato_sist_innlogget` TIMESTAMP NULL,
  PRIMARY KEY (`brukernavn`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE UNIQUE INDEX `navn_UNIQUE` ON `bosetterne`.`SPILLER` (`brukernavn` ASC);

CREATE UNIQUE INDEX `farge_UNIQUE` ON `bosetterne`.`SPILLER` (`farge` ASC);

CREATE UNIQUE INDEX `epost_UNIQUE` ON `bosetterne`.`SPILLER` (`epost` ASC);


-- -----------------------------------------------------
-- Table `bosetterne`.`SPILL`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bosetterne`.`SPILL` (
  `spill_id` INT NOT NULL AUTO_INCREMENT,
  `navn` VARCHAR(45) NULL,
  `leder` VARCHAR(15) NOT NULL,
  `type_spill` ENUM('BOSETTERNE','BYERRIDDER','SJOFARER','SJOOGLAND','HANDELBARB','EXPLPIRAT') NOT NULL,
  `dato_fom` TIMESTAMP NULL,
  `dato_tom` TIMESTAMP NULL,
  `max_poeng` TINYINT(1) UNSIGNED NOT NULL DEFAULT 13,
  PRIMARY KEY (`spill_id`),
  CONSTRAINT `fk_SPILL_SPILLER1`
    FOREIGN KEY (`leder`)
    REFERENCES `bosetterne`.`SPILLER` (`brukernavn`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE INDEX `fk_SPILL_SPILLER1_idx` ON `bosetterne`.`SPILL` (`leder` ASC);

CREATE INDEX `idx_dato` USING BTREE ON `bosetterne`.`SPILL` (`dato_fom` ASC, `dato_tom` ASC);


-- -----------------------------------------------------
-- Table `bosetterne`.`SPILLER_I_SPILL`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bosetterne`.`SPILLER_I_SPILL` (
  `spiller_i_spill_id` INT NOT NULL AUTO_INCREMENT,
  `brukernavn` VARCHAR(15) NOT NULL,
  `spill_id` INT NOT NULL,
  `plassering` TINYINT NULL,
  PRIMARY KEY (`spiller_i_spill_id`),
  CONSTRAINT `fk_SPILLER_i_SPILL`
    FOREIGN KEY (`brukernavn`)
    REFERENCES `bosetterne`.`SPILLER` (`brukernavn`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SPILL_med_SPILLER`
    FOREIGN KEY (`spill_id`)
    REFERENCES `bosetterne`.`SPILL` (`spill_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE INDEX `fk_SPILL_med_SPILLER_idx` USING BTREE ON `bosetterne`.`SPILLER_I_SPILL` (`spill_id` ASC);

CREATE INDEX `fk_SPILLER_i_SPILL_idx` ON `bosetterne`.`SPILLER_I_SPILL` (`brukernavn` ASC);

CREATE UNIQUE INDEX `game_id_UNIQUE` USING BTREE ON `bosetterne`.`SPILLER_I_SPILL` (`spill_id` ASC, `brukernavn` ASC);


-- -----------------------------------------------------
-- Table `bosetterne`.`TUR`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bosetterne`.`TUR` (
  `tur_id` INT NOT NULL,
  `spill_id` INT NOT NULL,
  PRIMARY KEY (`tur_id`, `spill_id`),
  CONSTRAINT `fk_table1_SPILL1`
    FOREIGN KEY (`spill_id`)
    REFERENCES `bosetterne`.`SPILL` (`spill_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `bosetterne`.`K_HANDLING`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bosetterne`.`K_HANDLING` (
  `k_handling` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`k_handling`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `bosetterne`.`HANDLING`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bosetterne`.`HANDLING` (
  `handling_id` INT NOT NULL,
  `k_handling` VARCHAR(20) NOT NULL,
  `tur_id` INT NOT NULL,
  `spill_id` INT NOT NULL,
  PRIMARY KEY (`handling_id`),
  CONSTRAINT `fk_HANDLING_TUR1`
    FOREIGN KEY (`tur_id` , `spill_id`)
    REFERENCES `bosetterne`.`TUR` (`tur_id` , `spill_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_HANDLING_K_HANDLING1`
    FOREIGN KEY (`k_handling`)
    REFERENCES `bosetterne`.`K_HANDLING` (`k_handling`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE INDEX `fk_HANDLING_TUR1_idx` ON `bosetterne`.`HANDLING` (`tur_id` ASC, `spill_id` ASC);

CREATE INDEX `fk_HANDLING_K_HANDLING1_idx` ON `bosetterne`.`HANDLING` (`k_handling` ASC);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
