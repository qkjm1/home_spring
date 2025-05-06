DROP DATABASE IF EXISTS AM_JSP_25_04;
 CREATE DATABASE AM_JSP_25_04;
 USE AM_JSP_25_04;
 
 # 게시글 테이블 생성
 CREATE TABLE article (
                          id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
                          regDate DATETIME NOT NULL,
                          updateDate DATETIME NOT NULL,
                          title CHAR(100) NOT NULL,
                          `body` TEXT NOT NULL
 );
 # 회원 테이블 생성
 CREATE TABLE `member` (
                           id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
                           regDate DATETIME NOT NULL,
                           updateDate DATETIME NOT NULL,
                           loginId CHAR(30) NOT NULL,
                           loginPw CHAR(200) NOT NULL,
                           `name` CHAR(100) NOT NULL
 );
 
 # 게시글 테스트 데이터 생성
 INSERT INTO article
 SET regDate = NOW(),
 updateDate = NOW(),
 title = '제목1',
 `body` = '내용1';
 
 INSERT INTO article
 SET regDate = NOW(),
 updateDate = NOW(),
 title = '제목2',
 `body` = '내용2';
 
 INSERT INTO article
 SET regDate = NOW(),
 updateDate = NOW(),
 title = '제목3',
 `body` = '내용3';
 
 # 회원 테스트 데이터 생성
 INSERT INTO `member`
 SET regDate = NOW(),
 updateDate = NOW(),
 loginId = 'test1',
 loginPw = 'test1',
 `name` = '김철수';
 
 INSERT INTO `member`
 SET regDate = NOW(),
 updateDate = NOW(),
 loginId = 'test2',
 loginPw = 'test2',
 `name` = '홍길동';
 
 
 ##########
 
SELECT *
FROM  article
 
 