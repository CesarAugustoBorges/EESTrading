-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema TradingPlatform
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema TradingPlatform
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `TradingPlatform` DEFAULT CHARACTER SET utf8 ;
USE `TradingPlatform` ;

-- -----------------------------------------------------
-- Table `TradingPlatform`.`Utilizador`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `TradingPlatform`.`Utilizador` ;

CREATE TABLE IF NOT EXISTS `TradingPlatform`.`Utilizador` (
  `Nome` VARCHAR(200) NOT NULL,
  `Password` VARCHAR(200) NOT NULL,
  `Saldo` DECIMAL(8,2) NOT NULL,
  PRIMARY KEY (`Nome`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `TradingPlatform`.`AtivoFinanceiro`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `TradingPlatform`.`AtivoFinanceiro` ;

CREATE TABLE IF NOT EXISTS `TradingPlatform`.`AtivoFinanceiro` (
  `Nome` VARCHAR(100) NOT NULL,
  `ValorUnit` DECIMAL(5,2) NOT NULL,
  `Type` VARCHAR(100) NULL,
  PRIMARY KEY (`Nome`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `TradingPlatform`.`CFD`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `TradingPlatform`.`CFD` ;

CREATE TABLE IF NOT EXISTS `TradingPlatform`.`CFD` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `ValorCompra` DECIMAL(8,2) NOT NULL,
  `Unidades` INT NOT NULL,
  `TopProfit` DECIMAL(8,2) NOT NULL,
  `StopLoss` DECIMAL(8,2) NOT NULL,
  `Utilizador_Nome` VARCHAR(200) NOT NULL,
  `AtivoFinanceiro_Nome` VARCHAR(200) NOT NULL,
  `Portfolio` TINYINT NOT NULL,
  PRIMARY KEY (`Id`),
  INDEX `fk_CFD_Utilizador_idx` (`Utilizador_Nome` ASC) VISIBLE,
  INDEX `fk_CFD_AtivoFinanceiro1_idx` (`AtivoFinanceiro_Nome` ASC) VISIBLE,
  CONSTRAINT `fk_CFD_Utilizador`
    FOREIGN KEY (`Utilizador_Nome`)
    REFERENCES `TradingPlatform`.`Utilizador` (`Nome`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_CFD_AtivoFinanceiro1`
    FOREIGN KEY (`AtivoFinanceiro_Nome`)
    REFERENCES `TradingPlatform`.`AtivoFinanceiro` (`Nome`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
