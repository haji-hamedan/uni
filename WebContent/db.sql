CREATE TABLE "LOANS" (
"LOANID" NUMBER(12, 0) NOT NULL ENABLE, 
"TITLE" VARCHAR2(255),
 "AMOUNT" NUMBER(21, 0),
 "INTERESTRATE" NUMBER(3, 0),
 "PAYMENTCOUNT" NUMBER(5, 0),
 "PAYMENTFREQUENCY" NUMBER(1, 0),
 "FIRSTPAYMENTDATE" NUMBER(13, 0), 
 "CREATEDATE" NUMBER(13, 0),
 "USERID" NUMBER(12, 0)
 );

CREATE UNIQUE INDEX "LOANS_PK" ON "LOANS" ("LOANID" );

ALTER TABLE "LOANS" ADD  CONSTRAINT "LOANS_PK" PRIMARY KEY ("LOANID") USING INDEX;


CREATE TABLE "PAYMENTS" (
"PAYMENTID" NUMBER(12, 0) NOT NULL ENABLE,
 "LOANID" NUMBER(12, 0) NOT NULL ENABLE,
 "AMOUNT" NUMBER(21, 0), 
 "PAYDATE" NUMBER(13, 0),
 "CREATEDATE" NUMBER(13, 0),
 "USERID" NUMBER(12, 0),
 "ISPAID" NUMBER(1, 0) NOT NULL ENABLE
 );
 
CREATE UNIQUE INDEX "PAYMENTS_PK" ON "PAYMENTS" ("PAYMENTID" );

ALTER TABLE "PAYMENTS" ADD  CONSTRAINT "PAYMENTS_PK" PRIMARY KEY ("PAYMENTID") USING INDEX;


CREATE TABLE "USERS" (
"USERID" NUMBER(12, 0) NOT NULL ENABLE, 
"USERNAME" VARCHAR2(255),
 "EMAIL" VARCHAR2(255), 
 "PASSWORD" VARCHAR2(255), 
 "FIRSTNAME" VARCHAR2(255), 
 "LASTNAME" VARCHAR2(255), 
 "MOBILE" VARCHAR2(255), 
 "ISADMIN" NUMBER(1, 0) NOT NULL ENABLE
 );
 
CREATE UNIQUE INDEX "USERS_PK" ON "USERS" ("USERID");

ALTER TABLE "USERS" ADD  CONSTRAINT "USERS_PK" PRIMARY KEY ("USERID");
