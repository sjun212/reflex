CREATE TABLE member (
	member_id	varchar2(20)		NOT NULL,
	member_name	varchar2(50)		NOT NULL,
	member_password 	varchar2(50)		NOT NULL,
	member_phone	varchar2(20)		NOT NULL,
	member_email	varchar2(255)		NOT NULL,
	member_postcode	number		NOT NULL,
	member_address	varchar2(255)		NOT NULL,
	member_detail_address	varchar2(255)		NOT NULL,
	member_point	number	DEFAULT 0	NOT NULL,
	member_hobby1	varchar2(100)		NULL,
	member_hobby2	varchar2(100)		NULL,
	member_quit_yn 	char(1)	DEFAULT 'N'	NOT NULL,
	member_enroll_date	date	DEFAULT sysdate	NOT NULL
);


select * from member;

--사용자 계정 추가
insert into member values('abcde', '알파벳', '1234', '01012341234', 'abcde@naver.com', 12345, '서울시 강남구 역삼동 11-1', '101호', 11111, '육아', '캠핑', default ,default);
insert into member values('honggd', '홍길동', '1234', '01012341234', 'honggd@naver.com', 23456, '서울시 중구 동대문동 22-2', '202호', 22222, '운동', null ,default ,default);
insert into member values('sinsa', '신사임당', '1234', '01012341234', 'sinsa@naver.com', 34567, '서울시 관악구 인헌동 33-3', '303호', 33333, null, null, default ,default);

--관리자 계정 추가
insert into member values('admin', '관리자', '1234', '01012341234', 'admin@member.com', 98765, '서울시 서초구 서초동 44-4', '404호', 44444, null, null, default ,default);
insert into member values(?, ?, ?, ?, ?, ?, ?, ?, default, ?, ?, default ,default);
commit;
