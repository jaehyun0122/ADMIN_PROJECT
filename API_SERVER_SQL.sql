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


-- api_server 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `api_server` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `api_server`;

-- 테이블 api_server.api_auth_req_info 구조 내보내기
CREATE TABLE IF NOT EXISTS `api_auth_req_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `companyCd` varchar(5) NOT NULL,
  `channelTycd` varchar(2) DEFAULT NULL,
  `channelNm` varchar(40) DEFAULT NULL,
  `agencyCd` varchar(2) DEFAULT NULL,
  `serviceTycd` varchar(5) NOT NULL,
  `telcoTycd` varchar(1) NOT NULL,
  `phoneNo` varbinary(40) NOT NULL,
  `userNm` varbinary(300) NOT NULL,
  `birthday` varbinary(40) NOT NULL,
  `gender` varbinary(40) NOT NULL,
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
  `certTxId` varchar(20) DEFAULT NULL,
  `resultDttm` varchar(20) DEFAULT NULL,
  `resultTycd` varchar(1) DEFAULT NULL,
  `ci` varchar(400) DEFAULT NULL,
  `digitalSign` longtext DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=194 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 api_server.api_auth_result 구조 내보내기
CREATE TABLE IF NOT EXISTS `api_auth_result` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `reqTxId` varchar(20) NOT NULL,
  `telcoTxId` varchar(20) DEFAULT NULL,
  `certTxId` varchar(20) NOT NULL,
  `resultTycd` varchar(1) NOT NULL,
  `resultDttm` varchar(20) DEFAULT NULL,
  `digitalSign` longtext DEFAULT NULL,
  `ci` varchar(400) DEFAULT NULL,
  `telcoTycd` varchar(1) DEFAULT NULL,
  `userNm` varbinary(300) DEFAULT NULL,
  `birthday` varbinary(40) DEFAULT NULL,
  `gender` varbinary(40) DEFAULT NULL,
  `phoneNo` varbinary(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=215 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 api_server.auth 구조 내보내기
CREATE TABLE IF NOT EXISTS `auth` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `token` varchar(100) NOT NULL,
  `auth_key` varchar(1000) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 api_server.company 구조 내보내기
CREATE TABLE IF NOT EXISTS `company` (
  `companyCd` varchar(5) NOT NULL,
  `companyNm` varchar(40) NOT NULL,
  PRIMARY KEY (`companyCd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 api_server.rsa 구조 내보내기
CREATE TABLE IF NOT EXISTS `rsa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `decryptKey` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 api_server.user 구조 내보내기
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userNm` varbinary(300) DEFAULT NULL,
  `phoneNo` varbinary(40) DEFAULT NULL,
  `birthday` varbinary(40) DEFAULT NULL,
  `gender` varbinary(40) DEFAULT NULL,
  `telcoTycd` varbinary(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.
INSERT INTO rsa(decryptKey) VALUES 
('-----BEGIN PRIVATE KEY-----
MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCYjuG3lehPD7m1
W/jRMJjse/8Oqi+BG6KKOqsENRTv5DFDE1xywry/IkLOhx5C0ViuIPpbcjunhC3S
6/1L9dlx03ShrSwut56FFwL3TuLGNPxRsVZnahZ+rzmmYQRRIXkvDO2oCvsQ/+tH
6Jrak9zgkbMBKm+W+Wey4B1HLNKiLR7pUJvgF3XDdgIwchoJ2cPCSRqqc7+9uLXN
Pya1LG8s0sGTzj49jwqRFo4ksTNpUyIKoX87yYW9AO/u81xqXWxoMG5i+AWhUkxy
IwcTDmVAQAcNsZGFtCCNgSrRURpMXXwn4RDfElKTs0Vu7SSR/nTcKLeGvAvJX0ja
gzDLbUq3AgMBAAECggEANQqoi+4BAUwUY2wAzKq64K4T9gNR1O/IyYqwXxwdlXKf
dhTLNdNjUIkW9TuhV9X1oUUyBo1nFE0uqYEHZluqKJs+l9/f1rZc1SU1EMtKqWNO
vxCLXBVUkONXhfOBTrv2qFc0YhK/TB/OrAfBn3F9gE7oD+WzOqioV1b4lwCMk3nV
peTECckbpwaXhYrU7AYkWEAXaL27pF/2FbD09O/UBKblK2Ix0aN3fhnseKZl5kGu
qM2NdtfoFS6tK5Kd8nekiBRCXyopZRCep5B/iUS+vcIYxHz+qIWjNrcluPoi+DIo
ZXCUmH50ZEkkNq7zdfTQEUkmYsUovWaKdVK66mw6MQKBgQDlp0ptOpqjPZRU+4I0
dEtqR1ECUFU59wQXVEHOxeluYSKbisH1z8u7LCghmW89vhFLWolZRplEgCfJ4jzk
lcDVFkF0HMYCKAVL0k3Gv/DYcCqBen9VECHSaYtAnA6OcwZze+8rVAjipLDdN531
2qzUzFMePnBfTiymMj7niZW4bQKBgQCqD19MevYqlMbAX0Q4pVzpWdfxyMVVx3lW
kp26BF/pQ6USqcKXKlDyqmo6F69cDp+67ppyzO3vhlbAalMtx6aw+91g7GCFIlHK
rAVv2ejQIIocxMSAqTQ/wXiHF30mAvEPA0CKgVzs8rMgprmCNEVmvWxl8IK7CRTA
f7e2J/GhMwKBgQCQ0h4tI3fA2WRg8vn4+h1UuARt4RrdotgBnjChpEzAPqEEVUxb
Jt7i+cWU6p0r4mAllMJoegzNFcQBm5mqzIT92zsLbdmwImy0k7pYS1ImKxWi8Qy7
J6Esf0JBTCghzIeU6+K3ABdaVWDzH8+M6oly6CbCO73BXBrr82OXvzubQQKBgFaE
p5lDXYXgVxV74jtnddOKT+DEohC4ATVYNkJzA1Crh4ntMAC0GKa8qHqRnjeZgQ4b
YhxRwiOAFxahgVvjHR0hpkkvORPCmBYiWknDdTydsWZdfRNwAhMFnQotmxABox5e
KzHMCrjJ6PQNnodtjHlumX1rVTdOsW4WDCLyQ24dAoGBAOFrfIv6cp1KiTTjvhTM
dbR1CT47fon4W3NTAgy11N3iFfeyg0FBJiVsE7qxUaeqbfpvFGGvG3kSW1+X57tK
bVS3EohfYKDBy1kKBruUDc65vsC0wcDzMeGZVZwivaSZ+6JlW/VsMAO2WowhLISU
xOcr5QXDUENDFQo+Z52Si62M
-----END PRIVATE KEY-----'
)

INSERT INTO user(userNm, phoneNo, birthday, gender, telcoTycd, myKey)
VALUES(
  AES_ENCRYPT('정재현', 'mykey'),
  AES_ENCRYPT('01093689836', 'mykey'),
  AES_ENCRYPT('950122', 'mykey'),
  AES_ENCRYPT('1', 'mykey'),
  'S',
  'YzNmOGQ2OGI1ZDEwNDA5YmJmZmRhMTI5'
);

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
