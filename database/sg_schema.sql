/* Copyright (C) 2017 SocialGuru S.de R.L. - All Rights Reserved */ 

-- SOCIALGURU DATABASE SCHEMA

CREATE TABLE ACCOUNT_TYPES(
	)

CREATE TABLE ACCOUNT_STATUS(
	)


CREATE TABLE CUSTOMERS ( 
	CUST_ID 		INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	FIRST_NAME 		VARCHAR(30) NOT NULL,
	LAST_NAME 		VARCHAR(30),
	ADDR1			VARCHAR(50),
	ADDR2			VARCHAR(50),
	ZIPCODE			INT(5),
	COUNTRY			VARCHAR(15) NOT NULL,
	MEMBER_SINCE 	DATE NOT NULL)
	AUTO_INCREMENT = 1;

CREATE TABLE ACCOUNTS (
	ACCOUNT_ID		INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	CUST_ID			INT NOT NULL,
	ACCOUNT_TYPE 	INT NOT NULL,
	ACCOUNT_STATUS	INT NOT NULL,
	PHONE_NUMBER	VARCHAR(15),
	COMPANY_NAME	VARCHAR(30),
	USERNAME		VARCHAR(12),
	EMAIL			VARCHAR(30),
	PASSWORD		VARCHAR(15),
	CURRENT_BALANCE	FLOAT(10,2),
	PREV_BALANCE	FLOAT(10,2),
	DATE_CREATED	DATE NOT NULL,
	LAST_CHECKOUT	TIMESTAMP,
	LAST_SIGN_IN	TIMESTAMP,
	FOREIGN KEY(CUST_ID) REFERENCES CUSTOMERS(CUST_ID),
	FOREIGN KEY(ACCOUNT_TYPE) REFERENCES ACCOUNT_TYPES(TYPE),
	FOREIGN KEY(ACCOUNT_STATUS) REFERENCES ACCOUNT_STATUS(STATUS))
	AUTO_INCREMENT = 1;

DROP TABLE ACCOUNT_STATUS;
DROP TABLE ACCOUNT_TYPES;
DROP TABLE ACCOUNTS;
DROP TABLE CUSTOMERS;