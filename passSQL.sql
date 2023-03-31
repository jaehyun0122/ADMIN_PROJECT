-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        10.11.2-MariaDB - mariadb.org binary distribution
-- 서버 OS:                        Win64
-- HeidiSQL 버전:                  11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- pass 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `pass` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `pass`;

-- 테이블 pass.auth_result 구조 내보내기
CREATE TABLE IF NOT EXISTS `auth_result` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `reqTxId` varchar(20) NOT NULL,
  `telcoTxId` varchar(20) DEFAULT NULL,
  `certTxId` varchar(20) NOT NULL,
  `resultTycd` varchar(1) NOT NULL,
  `resultDttm` varchar(20) DEFAULT NULL,
  `digitalSign` longtext DEFAULT NULL,
  `ci` varchar(400) DEFAULT NULL,
  `telcoTycd` varchar(1) DEFAULT NULL,
  `userNm` varchar(300) DEFAULT NULL,
  `birthday` varchar(40) DEFAULT NULL,
  `gender` varchar(40) DEFAULT NULL,
  `phoneNo` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- 테이블 pass.req_info 구조 내보내기
CREATE TABLE IF NOT EXISTS `req_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `companyCd` varchar(5) NOT NULL,
  `channelTycd` varchar(2) DEFAULT NULL,
  `channelNm` varchar(40) DEFAULT NULL,
  `agencyCd` varchar(2) DEFAULT NULL,
  `serviceTycd` varchar(5) NOT NULL,
  `telcoTycd` varchar(1) NOT NULL,
  `phoneNo` varchar(40) NOT NULL,
  `userNm` varchar(300) NOT NULL,
  `birthday` varchar(40) NOT NULL,
  `gender` varchar(40) NOT NULL,
  `reqTitle` varchar(50) NOT NULL,
  `reqContent` varchar(500) DEFAULT NULL,
  `reqCSPhoneNo` varchar(12) NOT NULL,
  `reqEndDttm` varchar(20) NOT NULL,
  `isNotification` varchar(1) DEFAULT 'Y',
  `isPASSVerify` varchar(1) NOT NULL DEFAULT 'Y',
  `verifyURL` varchar(100) DEFAULT NULL,
  `signTargetTycd` varchar(1) NOT NULL,
  `signTarget` longtext NOT NULL,
  `isUserAgreement` varchar(1) DEFAULT 'N',
  `originalInfo` longtext DEFAULT NULL,
  `reqTxId` varchar(20) NOT NULL,
  `isDigitalSign` varchar(1) DEFAULT 'Y',
  `isCombineAuth` varchar(1) DEFAULT 'Y',
  `certTxId` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
