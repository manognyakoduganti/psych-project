-- MySQL dump 10.13  Distrib 5.5.47, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: teenViolence
-- ------------------------------------------------------
-- Server version	5.5.47-0ubuntu0.14.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */; 
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

drop schema if exists psych;
CREATE DATABASE IF NOT EXISTS Psych;

USE Psych;

/*fieldLook up table*/

DROP TABLE IF EXISTS `fieldLookup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fieldLookup` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `groupName` varchar(200) NOT NULL,
  `fieldName` varchar(200) NOT NULL,
  `description` TEXT DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
ALTER TABLE `fieldLookup` ADD UNIQUE `unique_index`(`groupName`, `fieldName`);

##########################################################################

/*location table*/

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `locCode` varchar(6) NOT NULL UNIQUE,
  `locName` varchar(150) NOT NULL UNIQUE,
  `description` TEXT DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `addressLine1` varchar(200) NOT NULL,
  `addressLine2` varchar(200) DEFAULT NULL,
  `city` varchar(100) NOT NULL,
  `state` bigint(20) NOT NULL,
  `zipcode` varchar(20) NOT NULL,
  `phoneNumber` varchar(20) NOT NULL,
  `faxNumber` varchar(20) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKI_LOC_STATE` FOREIGN KEY (`state`) REFERENCES `fieldLookup` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

##########################################################################

/*admin table*/

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(50) NOT NULL,
  `lastName` varchar(50) DEFAULT NULL,
  `email` varchar(200) NOT NULL UNIQUE,
  `password` varchar(30) NOT NULL,
  `locationId` bigint(20) NOT NULL,
  `privilegeToReleaseFeedback` BOOL DEFAULT FALSE,
  `privilegeToCustomizeTraining` BOOL DEFAULT FALSE,
  `role` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKI_ADMIN_ROLE` FOREIGN KEY (`role`) REFERENCES `fieldLookup` (`id`),
  CONSTRAINT `FKI_ADMIN_LOCATION` FOREIGN KEY (`locationId`) REFERENCES `location` (`id`)
)
  ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

##########################################################################

/*training table*/

DROP TABLE IF EXISTS `training`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `training` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL UNIQUE,
  `description` TEXT DEFAULT NULL,
  `keywords` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

##########################################################################

/*targetGroup table*/

DROP TABLE IF EXISTS `targetGroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `targetGroup` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL UNIQUE,
  `description` TEXT DEFAULT NULL,
  `keywords` varchar(200) DEFAULT NULL,
  `locationId` bigint(20) NOT NULL,
  `trainingId` bigint(20) NOT NULL,
  `registrationCode` varchar(50) NOT NULL UNIQUE,
  `default` BOOL DEFAULT FALSE,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKI_TRG_GRP_TRAINING` FOREIGN KEY (`trainingId`) REFERENCES `training` (`id`),
  CONSTRAINT `FKI_TRG_GRP_LOCATION` FOREIGN KEY (`locationId`) REFERENCES `location` (`id`)
)
  ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

##########################################################################

/*participant table*/

DROP TABLE IF EXISTS `participant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `participant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(200) NOT NULL UNIQUE,
  `password` varchar(200) NOT NULL,
  `age` int(3) DEFAULT NULL,
  `gender` bigint(20) DEFAULT NULL,
  `ethnicity` bigint(20) DEFAULT NULL,
  `disability` varchar(200) DEFAULT NULL,
  `education` bigint(20) NOT NULL,
  `maritalStatus` bigint(20) NOT NULL,
  `employmentStatus` bigint(20) NOT NULL,
  `householdIncome` bigint(20) NOT NULL,
  `mobileHandlingExperience` varchar(200) DEFAULT NULL,
  `psycothereputicMedications` varchar(200) NOT NULL,
  `colorblind` varchar(200) DEFAULT NULL,
  `registrationCode` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKI_PART_GENDER` FOREIGN KEY (`gender`) REFERENCES `fieldLookup` (`id`),
  CONSTRAINT `FKI_PART_ETHNICITY` FOREIGN KEY (`ethnicity`) REFERENCES `fieldLookup` (`id`),
  CONSTRAINT `FKI_PART_EDUCATION` FOREIGN KEY (`education`) REFERENCES `fieldLookup` (`id`),
  CONSTRAINT `FKI_PART_MARITAL_STATUS` FOREIGN KEY (`maritalStatus`) REFERENCES `fieldLookup` (`id`),
  CONSTRAINT `FKI_PART_EMPLOYMENT_STATUS` FOREIGN KEY (`employmentStatus`) REFERENCES `fieldLookup` (`id`),
  CONSTRAINT `FKI_PART_HOUSEHOLD_INCOME` FOREIGN KEY (`householdIncome`) REFERENCES `fieldLookup` (`id`),
  CONSTRAINT `FKI_PART_REGISTRATION_CODE` FOREIGN KEY (`registrationCode`) REFERENCES `targetGroup` (`registrationCode`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


##########################################################################

/*targetGroup table*/

DROP TABLE IF EXISTS `targetGroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `targetGroup` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL UNIQUE,
  `description` TEXT DEFAULT NULL,
  `keywords` varchar(200) DEFAULT NULL,
  `locationId` bigint(20) NOT NULL,
  `trainingId` bigint(20) NOT NULL,
  `registrationCode` varchar(50) NOT NULL UNIQUE,
  `default` BOOL DEFAULT FALSE,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKI_TRG_GRP_TRAINING` FOREIGN KEY (`trainingId`) REFERENCES `training` (`id`),
  CONSTRAINT `FKI_TRG_GRP_LOCATION` FOREIGN KEY (`locationId`) REFERENCES `location` (`id`)
)
  ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

##########################################################################

/*questionCategory table*/

DROP TABLE IF EXISTS `questionCategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questionCategory` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL UNIQUE,
  `description` TEXT DEFAULT NULL,
  `responseType` bigint(20) NOT NULL,
  `startLabel` varchar(50) NOT NULL,
  `endLabel` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKI_QUES_CAT_RESPONSE_TYPE` FOREIGN KEY (`responseType`) REFERENCES `fieldLookup` (`id`)
)
  ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

##########################################################################


/*question table*/

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL UNIQUE,
  `description` TEXT DEFAULT NULL,
  `categoryId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKI_QUES_CAT` FOREIGN KEY (`categoryId`) REFERENCES `questionCategory` (`id`)
)
  ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

##########################################################################

/*imageCategory table*/

DROP TABLE IF EXISTS `imageCategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `imageCategory` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL UNIQUE,
  `description` TEXT DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

##########################################################################

/*image table*/

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL UNIQUE,
  `description` TEXT DEFAULT NULL,
  `categoryId` bigint(20) NOT NULL,
  `intensity` bigint(20) NOT NULL,
  `imageType` bigint(20) NOT NULL,
  `imageLoc` varchar(500) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKI_IMG_CAT` FOREIGN KEY (`categoryId`) REFERENCES `imageCategory` (`id`),
  CONSTRAINT `FKI_IMG_IMG_TYPE` FOREIGN KEY (`imageType`) REFERENCES `fieldLookup` (`id`)
)
  ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

##########################################################################

/*trainingQuestionMap table*/

DROP TABLE IF EXISTS `trainingQuestionMap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trainingQuestionMap` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `trainingId` bigint(20) NOT NULL,
  `questionId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKI_TRAIN_QUE_MAP_QUE` FOREIGN KEY (`questionId`) REFERENCES `question` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKI_TRAIN_QUE_MAP_TRAIN` FOREIGN KEY (`trainingId`) REFERENCES `training` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
)
  ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
ALTER TABLE `trainingQuestionMap` ADD UNIQUE `unique_index_train_ques`(`trainingId`, `questionId`);
##########################################################################

/*trainingImageMap table*/

DROP TABLE IF EXISTS `trainingImageMap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trainingImageMap` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `trainingId` bigint(20) NOT NULL,
  `imageCategoryId` bigint(20) NOT NULL,
  `duration` int NOT NULL,
  `noOfImages` int NOT NULL,
  `imageType` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKI_TRAIN_IMG_MAP_IMG_CAT` FOREIGN KEY (`imageCategoryId`) REFERENCES `imageCategory` (`id`)  ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKI_TRAIN_IMG_MAP_TRAIN` FOREIGN KEY (`trainingId`) REFERENCES `training` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKI_TRAIN_IMG_MAP_IMG_TYPE` FOREIGN KEY (`imageType`) REFERENCES `fieldLookup` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
)
  ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
ALTER TABLE `trainingImageMap` ADD UNIQUE `unique_index_train_img`(`trainingId`, `imageCategoryId`, `imageType`);
##########################################################################

/*userSession table*/

DROP TABLE IF EXISTS `userSession`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userSession` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `participantId` bigint(20) NOT NULL,
  `sessionDate` DATETIME NOT NULL DEFAULT NOW(),
  `sessionNumber` varchar(200) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKI_USER_SESSION_PARTICIPANT_ID` FOREIGN KEY (`participantId`) REFERENCES `participant` (`id`)
)
  ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
##########################################################################

/*questionResponse table*/

DROP TABLE IF EXISTS `questionResponse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questionResponse` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sessionId` bigint(20) NOT NULL,
  `participantId` bigint(20) NOT NULL,
  `questionId` bigint(20) NOT NULL,
  `response` varchar(200) NOT NULL,
  `questionStage` varchar(100) NOT NULL,
  `responseDate` DATETIME NOT NULL DEFAULT NOW(),
  PRIMARY KEY (`id`),
  CONSTRAINT `FKI_QUES_RESP_SID` FOREIGN KEY (`sessionId`) REFERENCES `userSession` (`id`),
  CONSTRAINT `FKI_QUES_RESP_PARTICIPANT_ID` FOREIGN KEY (`participantId`) REFERENCES `participant` (`id`),
  CONSTRAINT `FKI_QUES_RESP_QUESTION_ID` FOREIGN KEY (`questionId`) REFERENCES `question` (`id`)
)
  ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
##########################################################################

/*imageResponse table*/

DROP TABLE IF EXISTS `imageResponse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `imageResponse` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sessionId` bigint(20) NOT NULL,
  `participantId` bigint(20) NOT NULL,
  `correctness` varchar(200) NOT NULL,
  `timeTaken` varchar(200) NOT NULL,
  `expectedResult` varchar(200) NOT NULL,
  `bgColor` varchar(200) NOT NULL,
  `responseDate` DATETIME NOT NULL DEFAULT NOW(),
  PRIMARY KEY (`id`),
  CONSTRAINT `FKI_IMG_RESP_SID` FOREIGN KEY (`sessionId`) REFERENCES `userSession` (`id`),
  CONSTRAINT `FKI_IMG_RESP_PARTICIPANT_ID` FOREIGN KEY (`participantId`) REFERENCES `participant` (`id`)
)
  ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
##########################################################################
