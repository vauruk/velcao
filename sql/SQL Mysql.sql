-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema vcsoftcl_velcao
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `vcsoftcl_velcao` ;

-- -----------------------------------------------------
-- Schema vcsoftcl_velcao
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `vcsoftcl_velcao` DEFAULT CHARACTER SET utf8 ;
USE `vcsoftcl_velcao` ;

-- -----------------------------------------------------
-- Table `vcsoftcl_velcao`.`Cargo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `vcsoftcl_velcao`.`Cargo` ;

CREATE TABLE IF NOT EXISTS `vcsoftcl_velcao`.`Cargo` (
  `idCargo` INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador do  cargo',
  `Nome` VARCHAR(45) NULL COMMENT 'Nome do cargo',
  `TipoCargo` VARCHAR(45) NULL COMMENT 'Tipo do cargo',
  `isGerente` TINYINT(1) NULL COMMENT 'Nivel de Gerente',
  `isSupervisao` TINYINT(1) NULL COMMENT 'Nivel de Supervisao',
  `isOperacional` TINYINT(1) NULL COMMENT 'Nivel de Operacional',
  PRIMARY KEY (`idCargo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `vcsoftcl_velcao`.`Usuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `vcsoftcl_velcao`.`Usuario` ;

CREATE TABLE IF NOT EXISTS `vcsoftcl_velcao`.`Usuario` (
  `idUsuario` INT NOT NULL AUTO_INCREMENT COMMENT 'Numero do identificador do usuário ',
  `Nome` VARCHAR(255) NOT NULL COMMENT 'Nome do usuário',
  `RG` VARCHAR(45) NOT NULL COMMENT 'RG do usuário',
  `CPF` VARCHAR(45) NOT NULL COMMENT 'CPF do usuário',
  `DataAdmissao` DATE NOT NULL COMMENT 'Data em que o usuário entrou na emprresa',
  `Telefone` VARCHAR(45) NOT NULL COMMENT 'Telefone do usuário',
  `idCargo` INT NOT NULL COMMENT 'Identificador do Cargo ',
  `Senha` VARCHAR(255) NOT NULL COMMENT 'Senha do usuário',
  `Endereco` VARCHAR(255) NULL COMMENT 'Endereço do usuário',
  `Email` VARCHAR(255) NOT NULL COMMENT 'Email valido do usuário',
  `isAdministrador` TINYINT(1) NULL DEFAULT 0,
  `isAtivo` TINYINT(1) NULL DEFAULT 1,
  PRIMARY KEY (`idUsuario`, `Senha`, `DataAdmissao`),
  INDEX `UsuarioId_CargoId_idx` (`idCargo` ASC),
  UNIQUE INDEX `Email_UNIQUE` (`Email` ASC),
  CONSTRAINT `UsuarioId_CargoId`
    FOREIGN KEY (`idCargo`)
    REFERENCES `vcsoftcl_velcao`.`Cargo` (`idCargo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `vcsoftcl_velcao`.`AnimalMorto`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `vcsoftcl_velcao`.`AnimalMorto` ;

CREATE TABLE IF NOT EXISTS `vcsoftcl_velcao`.`AnimalMorto` (
  `idAnimalMorto` INT NOT NULL AUTO_INCREMENT,
  `Nome` VARCHAR(255) NOT NULL,
  `DataRegistro` DATETIME NULL,
  `Localizacao` VARCHAR(255) NULL,
  `idUsuarioRegistro` INT NULL,
  PRIMARY KEY (`idAnimalMorto`),
  INDEX `idUsuarioRegistro_idx` (`idUsuarioRegistro` ASC),
  CONSTRAINT `idUsuarioRegistro`
    FOREIGN KEY (`idUsuarioRegistro`)
    REFERENCES `vcsoftcl_velcao`.`Usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
