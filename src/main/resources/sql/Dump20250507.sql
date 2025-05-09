-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: bookmarket
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `isbn` varchar(20) NOT NULL,
  `title` varchar(100) NOT NULL,
  `date` int DEFAULT NULL,
  `author` varchar(100) DEFAULT NULL,
  `description` text,
  `category` varchar(50) DEFAULT NULL,
  `publishDate` varchar(20) DEFAULT NULL,
  `imgPath` varchar(255) DEFAULT NULL,
  `amount` int DEFAULT '0',
  `publisher` varchar(45) DEFAULT NULL,
  `price` int DEFAULT NULL,
  PRIMARY KEY (`isbn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES ('1111','자바 코드의 품질을 높이는 100가지 방법 ',NULL,'타기르 발레예프','자바 개발에서 반복적으로 발생하는 100가지 실수를 모아 더 나은 코드를 작성할 수 있도록 돕는 실전 가이드다. ','프로그래밍 언어','2025년 3월','/img/books/book1.jpg',13,'한빛미디어',25000),('1112','혼자 공부하는 자바',NULL,'신용권','더욱 풍성한 내용을 담아 돌아왔다. 개정판은 기존의 자바 8 & 11 버전은 물론, 최신 버전인 자바 21까지 다룬다. ','프로그래밍 언어 ','2024년 2월','/img/books/book2.jpg',21,'한빛미디어',23000),('1113','Do it! 알고리즘 코딩 테스트 : 자바 편',NULL,'김종관','“코딩 테스트는 어떻게 준비해야 할까?” 곧 코딩 테스트를 앞두고 있거나 올해 안에 IT 기업으로 취업 또는 이직.','프로그래밍 언어 ','2022년 4월','/img/books/book3.jpg',4,'이지스퍼블리싱',17000),('1114','자바의 신 VOL.1 : 기초 문법편',NULL,'이상민','현장 전문가가 쓴 자바 기초 입문서다. NAVER, NHN, SKPlanet 등에서 성능 전문가의 풍부한 경험을 바탕으로 자바를 실무에 맞게 제대로 쓸 줄 알게 해주는 입문서를 목표를 만들었다. 현장 중심형 자바 기초 문법서다. ','프로그래밍 언어 ','2023년 10월','/img/books/book4.jpg',1,'로드북 ',27000),('1115','대규모 리액트 웹 앱 개발',NULL,'하산 지르데','확장 가능한 대규모 자바스크립트 웹 애플리케이션을 구축하는 방법','웹디자인/홈페이지','2025년 2월','/img/books/book5.jpg',10,'제이펍',35000),('1116','패턴으로 익히고 설계로 완성하는 리액트',NULL,'준타오 추','리액트를 활용한 대규모 애플리케이션 개발은 비동기 처리, 상태 관리, 성능 최적화 등 다양한 도전을 동반한다.','웹디자인/홈페이지','2025년 2월','/img/books/book6.jpg',23,'한빛미디어',25000),('1117','주니어 백엔드 개발자가 반드시 알아야 할 실무 지식',NULL,'최범균','백엔드 개발자가 실무에서 겪는 다양한 상황과 문제 해결 방법을 다룬 실무 지침서입니다.','프로그래밍 언어','2025년 4월','/img/books/book7.jpg',5,'한빛미디어',25200),('1118','처음부터 제대로 배우는 스프링 부트',NULL,'마크 헤클러','자바와 코틀린으로 클라우드 네이티브 애플리케이션을 구축하는 방법을 설명합니다.','프로그래밍 언어','2023년 5월','/img/books/book8.jpg',3,'한빛미디어',25200),('1119','코딩 자율학습 자바 입문',NULL,'최원효','자바 문법과 개념을 쉽게 설명하여 자율학습이 가능하도록 구성된 입문서입니다.','프로그래밍 언어','2024년 12월','/img/books/book9.jpg',4,'교보문고',20000),('1120','점프 투 자바',NULL,'박응용','자바의 문법을 쉽고 재미있게 이해할 수 있도록 구성된 자바 입문서입니다.','프로그래밍 언어','2025년 4월','/img/books/book10.jpg',6,'위키독스',8000),('1121','명품 JAVA Programming',NULL,'남궁성','자바 프로그래밍 개념과 방법을 충실히 정리하고 적절한 예제를 수록한 교재입니다.','프로그래밍 언어','2023년 3월','/img/books/book11.jpg',2,'한빛아카데미',30000),('1122','Java: A Beginner\'s Guide',NULL,'Herbert Schildt','자바의 기초부터 고급 기능까지 폭넓게 다루는 자바 입문서입니다.','프로그래밍 언어','2018년 10월','/img/books/book12.jpg',7,'McGraw-Hill Education',35000),('1123','Core Java Volume I – Fundamentals',NULL,'Cay S. Horstmann','자바의 핵심 개념과 기능을 심도 있게 다루는 자바 프로그래밍 기본서입니다.','프로그래밍 언어','2022년 8월','/img/books/book13.jpg',3,'Pearson Education',40000),('1124','Think Java: How to Think Like a Computer Scientist',NULL,'Allen B. Downey, Chris Mayfield','컴퓨터 과학자처럼 생각하는 방법을 자바를 통해 배우는 입문서입니다.','프로그래밍 언어','2016년 5월','/img/books/book14.jpg',4,'O\'Reilly Media',28000),('1125','Java: The Complete Reference',NULL,'Herbert Schildt','자바의 모든 것을 포괄적으로 다루는 참고서로, 초보자부터 전문가까지 유용합니다.','프로그래밍 언어','2018년 4월','/img/books/book15.jpg',5,'McGraw-Hill Education',45000),('1126','모던 자바 인 액션',NULL,'Raoul-Gabriel Urma, Mario Fusco, Alan Mycroft','자바 8의 함수형 프로그래밍 기능을 중심으로 모던 자바 개발 방법을 소개합니다.','프로그래밍 언어','2019년 1월','/img/books/book16.jpg',6,'한빛미디어',32000),('1127','Java 8 in Action',NULL,'Raoul-Gabriel Urma, Mario Fusco, Alan Mycroft','자바 8의 새로운 기능과 스트림 API를 활용한 실용적인 예제를 제공합니다.','프로그래밍 언어','2014년 8월','/img/books/book17.jpg',4,'Manning Publications',38000),('1128','실전 자바 소프트웨어 개발',NULL,'이재홍','실무에서 자바를 활용한 소프트웨어 개발 사례와 노하우를 담은 책입니다.','프로그래밍 언어','2023년 7월','/img/books/book18.jpg',3,'프리렉',30000),('1129','자바 ORM 표준 JPA 프로그래밍',NULL,'김영한','자바 ORM 기술인 JPA의 표준을 이해하고 실무에 적용하는 방법을 설명합니다.','프로그래밍 언어','2022년 11월','/img/books/book19.jpg',5,'에이콘출판',35000),('1130','스프링 부트와 AWS로 혼자 구현하는 웹 서비스',NULL,'이동욱','스프링 부트와 AWS를 활용하여 웹 서비스를 구현하는 방법을 단계별로 안내합니다.','프로그래밍 언어','2021년 3월','/img/books/book20.jpg',4,'프리렉',32000),('2000','진짜 기본 요리책',NULL,'월간 수퍼레시피','진짜 쉽고 맛있고 정확한 기본 레시피 306개를 담은 요리책입니다.','요리/레시피','2021-01-25','/img/books/cook1.jpg',10,'슈퍼레시피',16500),('2001','나의 첫 번째 요리책',NULL,'데이비드 에저턴','아이들이 처음 요리를 배울 수 있도록 구성된 요리책입니다.','요리/입문','2015-03-10','/img/books/cook2.jpg',12,'북극곰',13000),('2002','맛있게 살 빼는 다이어트 레시피',NULL,'이은경','운동 없이 8kg 감량! 초간단 다이어트 레시피를 소개합니다.','요리/다이어트','2022-06-20','/img/books/cook3.jpg',8,'비타북스',15000),('2003','엘더스크롤 공식 요리책',NULL,'첼시 먼로 카셀','게임 엘더스크롤에 등장하는 요리를 실제로 만들어볼 수 있는 공식 요리책입니다.','요리/게임','2021-11-10','/img/books/cook4.jpg',15,'제우미디어',22000),('2004','내가 만든 똥',NULL,'박하잎','아이들의 호기심을 자극하는 주제로, 재미있게 읽을 수 있는 그림책입니다.','아동/그림책','2019-07-26','/img/books/kid1.jpg',7,'미래엔 아이세움',9500),('2005','채소는 참 맛있어',NULL,'오사다 히로시','채소에 대한 긍정적인 인식을 심어주는 아동용 그림책입니다.','아동/그림책','2010-06-15','/img/books/kid2.jpg',9,'한림출판사',12000),('2006','아우야 안녕',NULL,'이수지','형제간의 사랑과 이별을 다룬 감동적인 아동용 그림책입니다.','아동/그림책','2015-09-10','/img/books/kid3.jpg',5,'한림출판사',11000),('2007','내 몸이 이상해',NULL,'강순예','아이들이 자신의 몸에 대해 이해할 수 있도록 도와주는 아동용 만화책입니다.','아동/만화','2019-08-15','/img/books/kid4.jpg',6,'미래엔 아이세움',10000),('2008','우주소년 아톰',NULL,'오사무 테즈카','고전 명작 만화로, 세대를 아우르는 인기를 자랑하는 작품입니다.','만화/SF','2019-05-20','/img/books/comic1.jpg',10,'학산문화사',12500),('2009','만화로 쉽게 배우는 유체역학',NULL,'타케이 마사히로','과학 개념을 만화로 쉽게 풀어낸 책으로, 학습과 재미를 동시에 제공합니다.','만화/과학','2018-12-10','/img/books/comic2.jpg',11,'성안당',13500),('2010','만화 쉽게 그리기',NULL,'마츠이타 와타루','만화 캐릭터의 표정과 포즈를 쉽게 그릴 수 있도록 도와주는 가이드북입니다.','만화/그리기','2017-03-15','/img/books/comic3.jpg',6,'성안당',14000),('2011','흔한남매 12',NULL,'흔한남매','유튜브 인기 채널을 기반으로 한 유쾌한 가족 만화입니다.','만화/코미디','2022-12-05','/img/books/comic4.jpg',9,'아이세움',13000),('2012','일상적이지만 절대적인 생활 속 수학지식 100',NULL,'존 D. 배로','일상에서 접할 수 있는 수학 개념을 쉽게 설명한 책입니다.','과학/수학','2016-07-01','/img/books/life1.jpg',7,'동아엠앤비',17000),('2013','지적 대화를 위한 넓고 얕은 지식 2',NULL,'채사장','다양한 분야의 지식을 간결하게 소개하여, 교양을 넓히는 데 도움이 됩니다.','인문/교양','2019-12-24','/img/books/life2.jpg',13,'웨일북',16000),('2014','알고 보니 내 생활이 다 과학',NULL,'김해보','생활 속 과학 원리를 재미있게 설명한 책으로, 과학에 대한 흥미를 높여줍니다.','과학/생활','2020-09-10','/img/books/life3.jpg',5,'교보문고',15000),('2015','1일 1페이지 짧고 깊은 지식수업 365: 마음편',NULL,'김옥림','매일 한 페이지씩 읽으며 다양한 지식을 쌓을 수 있는 책입니다.','자기계발/교양','2021-10-05','/img/books/life4.jpg',10,'위즈덤하우스',18000);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrows`
--

DROP TABLE IF EXISTS `borrows`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `borrows` (
  `user_id` varchar(50) NOT NULL,
  `isbn` varchar(20) NOT NULL,
  `borrow_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `due_date` date DEFAULT NULL,
  `remaining_days` int DEFAULT NULL,
  PRIMARY KEY (`user_id`,`isbn`),
  KEY `isbn` (`isbn`),
  CONSTRAINT `borrows_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `borrows_ibfk_2` FOREIGN KEY (`isbn`) REFERENCES `book` (`isbn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrows`
--

LOCK TABLES `borrows` WRITE;
/*!40000 ALTER TABLE `borrows` DISABLE KEYS */;
INSERT INTO `borrows` VALUES ('aaa123','1111','2025-05-07 00:00:00','2025-05-14',7),('aaa123','1112','2025-05-07 00:00:00','2025-05-14',7),('aaa123','1113','2025-05-07 00:00:00','2025-05-14',7);
/*!40000 ALTER TABLE `borrows` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupons`
--

DROP TABLE IF EXISTS `coupons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupons` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `imgPath` varchar(255) DEFAULT NULL,
  `value` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupons`
--

LOCK TABLES `coupons` WRITE;
/*!40000 ALTER TABLE `coupons` DISABLE KEYS */;
INSERT INTO `coupons` VALUES (1,'30% 할인 쿠폰','/img/coupons/30.png',30),(2,'50% 할인 쿠폰','/img/coupons/50.png',50),(3,'북싹 읽었수다 앱 전용쿠폰','/img/coupons/app50.png',50),(4,'미니게임 승리 보상쿠폰','/img/coupons/game30.png',30);
/*!40000 ALTER TABLE `coupons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchases`
--

DROP TABLE IF EXISTS `purchases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchases` (
  `user_id` varchar(50) NOT NULL,
  `isbn` varchar(20) NOT NULL,
  `amount` int DEFAULT NULL,
  PRIMARY KEY (`user_id`,`isbn`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `purchases_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchases`
--

LOCK TABLES `purchases` WRITE;
/*!40000 ALTER TABLE `purchases` DISABLE KEYS */;
INSERT INTO `purchases` VALUES ('aaa123','1111',2),('aaa123','1112',3),('aaa123','1113',1),('bbb123','1111',1),('bbb123','1116',12),('ccc123','1111',8),('ccc123','1112',1);
/*!40000 ALTER TABLE `purchases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_coupons`
--

DROP TABLE IF EXISTS `user_coupons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_coupons` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) DEFAULT NULL,
  `coupon_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `coupon_id` (`coupon_id`),
  CONSTRAINT `user_coupons_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `user_coupons_ibfk_2` FOREIGN KEY (`coupon_id`) REFERENCES `coupons` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_coupons`
--

LOCK TABLES `user_coupons` WRITE;
/*!40000 ALTER TABLE `user_coupons` DISABLE KEYS */;
INSERT INTO `user_coupons` VALUES (2,'aaa123',2),(3,'aaa123',3),(4,'bbb123',1),(5,'bbb123',2),(8,'ccc123',4);
/*!40000 ALTER TABLE `user_coupons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` varchar(50) NOT NULL,
  `pw` varchar(50) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `imgPath` varchar(255) DEFAULT NULL,
  `point` int DEFAULT '0',
  `grade` varchar(20) DEFAULT NULL,
  `totalPayed` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('aaa123','1234','김주언','01099605629','수원시 영통구 매탄동 140번길 ','/img/user/kim.png',0,'silver',5000),('admin','1234','김줜관리자','15881588','미국시카고','/img/user/kim.png',999999,'vvvip',999999),('asd123','1234','지창현','01023363061','분당동 129-6','/img/user/ji.png',22222,'silver',5000),('bbb123','1234','박민규','01029352843','경기도 하남시 하남구 88번길','/img/user/park.png',36500,'vip',355000),('ccc123','1234','조정태','01038849324','서울 성동구 송정동 66-146 3층 옥상X','/img/user/joe.png',33012,'vip',684000),('hidden','12345','선아최관리자','15771577','캐나다 어딘가','/img/user/canada.png',999999,'vvvip',999999),('qwe123','1234','현뼘관리자','01052528282','동탄 어딘가','/img/user/h.png',999999,'vvvip',987654);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-07 22:19:02
