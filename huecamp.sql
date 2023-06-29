--드랍테이블 모음
------------------------------------------------------------------------------------------------------------------
--회원
drop sequence memseq ;
DROP TABLE member CASCADE CONSTRAINTS;
------------------------------------------------------------------------------------------------------------------
--카트
drop sequence cartseq ;
drop table cart cascade constraints;
------------------------------------------------------------------------------------------------------------------
--카테고리
drop sequence bigcateseq ;
drop table bigcategory cascade constraints;
------------------------------------------------------------------------------------------------------------------
drop sequence smallcateseq ;
drop table smallcategory cascade constraints;
------------------------------------------------------------------------------------------------------------------
--qna
drop table productQnA cascade constraints;
drop sequence productQnA_no_seq;
------------------------------------------------------------------------------------------------------------------
--qna댓글
drop table ReplyproductQnA cascade constraints;
drop sequence ReplyproductQnA_no_seq;
------------------------------------------------------------------------------------------------------------------
--공지
drop sequence notice_no_seq ;
drop table notice cascade constraints;
------------------------------------------------------------------------------------------------------------------
--공지댓글
drop sequence Replynotice_no_seq ;
drop table Replynotice cascade constraints;
------------------------------------------------------------------------------------------------------------------
--주문
drop sequence orderseq ;
drop table orderlist;
------------------------------------------------------------------------------------------------------------------
--상품
drop sequence prodseq ;
drop table product cascade constraints;
------------------------------------------------------------------------------------------------------------------
--리뷰
drop sequence review_no_seq;
drop table review cascade constraints;
------------------------------------------------------------------------------------------------------------------
--리뷰댓글
 drop sequence reviewReply_no_seq;
 drop table reviewReply cascade constraints;
 ------------------------------------------------------------------------------------------------------------------
--재고
drop sequence stockseq ;
drop table stock cascade constraints;
------------------------------------------------------------------------------------------------------------------
--찜
drop table wishlist cascade constraints;
drop sequence wishlist_no_seq;
------------------------------------------------------------------------------------------------------------------





--테이블생성모음
------------------------------------------------------------------------------------------------------------------

--회원
create sequence memseq   
increment by 1
start with 1
minvalue 1
maxvalue 10000
nocache;
------------------------------------------------------------------------------------------------------------------
create table member(
	no number not null primary key, -- 사용자 식별번호
	id varchar2(40) UNIQUE not null, -- 아이디
	password varchar2(30) not null, -- 패스워드
	name varchar2(30) not null, -- 이름
	zip varchar2(20), -- 우편번호
	addr1 varchar2(50), -- 주소
	addr2 varchar2(50), -- 상세주소
    mobile1 varchar2(30), -- 핸드폰 앞
	mobile2 varchar2(30), -- 핸드폰 중
	mobile3 varchar2(30), -- 핸드폰 뒷	
    email varchar2(50),
    gender varchar2(10),
    state int default 1 not null,
    memdate date,
    deldate date,
    delcont varchar2(4000)
);
------------------------------------------------------------------------------------------------------------------

--카트
create sequence cartseq   
increment by 1
start with 1
minvalue 1
maxvalue 10000
nocache;
------------------------------------------------------------------------------------------------------------------
create table cart(
	no number primary key,
	mno number references member(no) on delete cascade, -- 유저번호
	pno number references product(no) on delete cascade, -- 상품 번호 
	opname varchar2(40), -- 사이즈
	qty number not null
);
------------------------------------------------------------------------------------------------------------------

--카테고리
create table bigcategory( 
	no number not null primary key,	-- 식별값
	name varchar2(50) not null,     -- 대분류명
	step number not null	 		-- 대분류간 순서조정을 위한 값
);
------------------------------------------------------------------------------------------------------------------
create sequence bigcateseq
increment by 1
start with 1
minvalue 1
maxvalue 10000
nocache;
------------------------------------------------------------------------------------------------------------------

create table smallcategory( 
	no number not null primary key,	-- 식별값
	name varchar2(50) not null,     -- 소분류명
	step number not null,			-- 소분류간 순서조정을 위한 값
	lcno number				-- 순서조정을 위한 값
);
------------------------------------------------------------------------------------------------------------------
create sequence smallcateseq
increment by 1
start with 1
minvalue 1
maxvalue 10000
nocache;
------------------------------------------------------------------------------------------------------------------

--qna
create table productQnA(
 board_no number(38) primary key --게시물 번호
 ,board_name varchar2(30) not null --글쓴이
 ,board_title varchar2(200) not null --글제목
 ,board_pwd varchar2(20) not null --비번
 ,board_cont varchar2(4000) not null --글내용
 ,board_hit number(38) default 0 --조회수, default 0 제약조건을 주면 해당 제약조건이 설정된 컬럼에 굳이 레
 --코드를 저장하지 않아도 기본값 0이 저장됨.
 ,board_ref number(38)  --원본글과 답변글을 묶어주는 그룹번호 역할
 ,board_step number(38) --원본글이면 0,첫번째 답변글이면 1,두번째 답변글이면 2 => 원본글과 답변글을 구분하는 번
 --호값이면서 몇번째 답변글인가를 알려준다.
 ,board_level number(38) --답변글 정렬순서
 ,board_date date 
);
------------------------------------------------------------------------------------------------------------------
create sequence productQnA_no_seq
start with 1
increment by 1
nocache;
------------------------------------------------------------------------------------------------------------------

--qna댓글
create table ReplyproductQnA(
 reply_no number(38) primary key --댓글 번호
 ,board_no number(38) references productQnA(board_no) on delete cascade --게시글 번호
 ,reply_name varchar2(30) not null --댓글 작성자
 ,reply_title varchar2(200) not null --댓글 제목
 ,reply_pwd varchar2(20) not null --비번
 ,reply_cont varchar2(4000) not null --댓글 내용
 ,reply_hit number(38) default 0 --조회수
 ,reply_ref number(38)  --
 ,reply_step number(38) --
 ,reply_level number(38) --
 ,reply_date date -- 댓글 작성 날짜
);
------------------------------------------------------------------------------------------------------------------
create sequence ReplyproductQnA_no_seq
start with 1
increment by 1
nocache;
------------------------------------------------------------------------------------------------------------------

--공지
create table notice(
 board_no number(38) primary key --게시물 번호
 ,board_name varchar2(30) not null --글쓴이
 ,board_title varchar2(200) not null --글제목
 ,board_pwd varchar2(20) not null --비번
 ,board_cont varchar2(4000) not null --글내용
 ,board_hit number(38) default 0 --조회수, default 0 제약조건을 주면 해당 제약조건이 설정된 컬럼에 굳이 레
 --코드를 저장하지 않아도 기본값 0이 저장됨.
 ,board_ref number(38)  --원본글과 답변글을 묶어주는 그룹번호 역할
 ,board_step number(38) --원본글이면 0,첫번째 답변글이면 1,두번째 답변글이면 2 => 원본글과 답변글을 구분하는 번
 --호값이면서 몇번째 답변글인가를 알려준다.
 ,board_level number(38) --답변글 정렬순서
 ,board_date date 
);
------------------------------------------------------------------------------------------------------------------
create sequence notice_no_seq
start with 1
increment by 1
nocache;
------------------------------------------------------------------------------------------------------------------

--공지댓글
create table Replynotice(
 reply_no number(38) primary key --댓글 번호
 ,board_no number(38) references notice(board_no) on delete cascade --게시글 번호
 ,reply_name varchar2(30) not null --댓글 작성자
 ,reply_title varchar2(200) not null --댓글 제목
 ,reply_pwd varchar2(20) not null --비번
 ,reply_cont varchar2(4000) not null --댓글 내용
 ,reply_hit number(38) default 0 --조회수
 ,reply_ref number(38)  --
 ,reply_step number(38) --
 ,reply_level number(38) --
 ,reply_date date -- 댓글 작성 날짜
);
------------------------------------------------------------------------------------------------------------------
create sequence Replynotice_no_seq
start with 1
increment by 1
nocache;
------------------------------------------------------------------------------------------------------------------

--주문
create sequence orderseq   
increment by 1
start with 1
minvalue 1
maxvalue 10000
nocache;
------------------------------------------------------------------------------------------------------------------
create table orderlist(
	no number  primary key,
	mno number references member(no) on delete cascade, -- 유저번호
	pno number references product(no) on delete cascade, -- 상품 번호 
	opname varchar2(40),
	qty number,
	price number,
	time date,    					-- 결제가 이뤄진 시간
	receiver varchar(40), 	-- 받는 사람
	rv_hp1 varchar2(30),
	rv_hp2 varchar2(30),
	rv_hp3 varchar2(30),
	rv_zip varchar2(20),
	rv_addr1 varchar2(50),
	rv_addr2 varchar2(50)
);
------------------------------------------------------------------------------------------------------------------

--상품
create table product( 
	no number not null primary key,	-- 식별값 
	lcno number not null references bigcategory(no) on delete cascade, --대분류
	scno number references smallcategory(no) on delete cascade, -- 소분류
	name varchar2(100) not null, -- 상품이름
	oriprice number, --정가
	discprice number, --할인가
	info varchar2(400), -- 상품설명
	mainImgN varchar2(100), -- 상품메인이미지
	detailImgN1 varchar2(100), -- 설명이미지1
	detailImgN2 varchar2(100), -- 설명이미지2
	detailImgN3 varchar2(100), -- 설명이미지3
	detailImgN4 varchar2(100) -- 설명이미지4
);
------------------------------------------------------------------------------------------------------------------
create sequence prodseq
increment by 1
start with 1
minvalue 1
maxvalue 10000
nocache;
------------------------------------------------------------------------------------------------------------------

--리뷰
create table review(
 board_no number(38) primary key --게시물 번호
 ,board_name varchar2(30) not null  --글쓴이
 ,board_title varchar2(200) not null--글제목
 ,board_pwd varchar2(20) not null --비번
 ,board_cont varchar2(4000) not null --글내용
 ,board_hit number(38) default 0 --조회수, default 0 제약조건을 주면 해당 제약조건이 설정된 컬럼에 굳이 레
 --코드를 저장하지 않아도 기본값 0이 저장됨.
 ,board_ref number(38)  --원본글과 답변글을 묶어주는 그룹번호 역할
 ,board_step number(38) --원본글이면 0,첫번째 답변글이면 1,두번째 답변글이면 2 => 원본글과 답변글을 구분하는 번
 --호값이면서 몇번째 답변글인가를 알려준다.
 ,board_level number(38) --답변글 정렬순서
 ,board_date date 
 ,board_file1 varchar2(150)
);
------------------------------------------------------------------------------------------------------------------
create sequence review_no_seq
start with 1
increment by 1
nocache;
------------------------------------------------------------------------------------------------------------------

--리뷰댓글
 create table reviewReply(
 reply_no number(38) primary key --댓글 번호
 ,board_no number(38) references review(board_no) on delete cascade --게시글 번호
 ,reply_name varchar2(30) not null --댓글 작성자
 ,reply_title varchar2(200) not null --댓글 제목
 ,reply_pwd varchar2(20) not null --비번
 ,reply_cont varchar2(4000) not null --댓글 내용
 ,reply_hit number(38) default 0 --조회수
 ,reply_ref number(38)  
 ,reply_step number(38) 
 ,reply_level number(38) 
 ,reply_date date -- 댓글 작성 날짜
 );
------------------------------------------------------------------------------------------------------------------
 create sequence reviewReply_no_seq
start with 1
increment by 1
nocache;
------------------------------------------------------------------------------------------------------------------

--재고
create sequence stockseq
increment by 1
start with 1
minvalue 1
maxvalue 10000
nocache;
------------------------------------------------------------------------------------------------------------------
create table stock( 
	no number not null primary key,	-- 식별값
	pno number references product(no) on delete cascade,
	opname varchar2(20) not null,
	count number
);
------------------------------------------------------------------------------------------------------------------

--찜
create sequence wishlist_no_seq
start with 1
increment by 1
nocache;
------------------------------------------------------------------------------------------------------------------
create table wishlist(
	no number  primary key,
	mno number references member(no) on delete cascade, -- 유저번호
	pno number references product(no) on delete cascade, -- 상품 번호 
	opname varchar2(40),
    qty number not null
);



commit;
------------------------------------------------------------------------------------------------------------------

select * from notice order by board_no asc;



commit;


---------------------
--공지사항 댓글


select * from Replynotice;
-------------------------------------


commit;






select * from bigcategory;
--아래의 insert 첫줄은 무조건 넣어줘야 함.
insert into bigcategory values(bigcateseq.nextval, '텐트/타프', 1);
insert into bigcategory values(bigcateseq.nextval, '테이블/선반', 2);
insert into bigcategory values(bigcateseq.nextval, '의자/침대', 3);
insert into bigcategory values(bigcateseq.nextval, '침낭/매트', 4);
insert into bigcategory values(bigcateseq.nextval, '취사용품', 5);
insert into bigcategory values(bigcateseq.nextval, '조명용품', 6);
insert into bigcategory values(bigcateseq.nextval, '캠핑툴', 7);
insert into bigcategory values(bigcateseq.nextval, '일반용품', 8);
insert into bigcategory values(bigcateseq.nextval, '등산/아웃도어', 9);

select * from product;
commit;
--아래의 insert 첫줄은 무조건 넣어줘야 함.
insert into smallcategory values(smallcateseq.nextval, '텐트', 1, 1);
insert into smallcategory values(smallcateseq.nextval, '타프', 2, 1);
insert into smallcategory values(smallcateseq.nextval, '그라운드시트/방수포', 3, 1);
insert into smallcategory values(smallcateseq.nextval, '폴대/팩.스토퍼', 4, 1);
insert into smallcategory values(smallcateseq.nextval, '테이블', 1, 2);
insert into smallcategory values(smallcateseq.nextval, '선반', 2, 2);
insert into smallcategory values(smallcateseq.nextval, '침낭', 1, 3);
insert into smallcategory values(smallcateseq.nextval, '매트', 2, 3);
insert into smallcategory values(smallcateseq.nextval, '돗자리/피크닉매트', 3, 3);


select * from bigcategory order by step asc;
select * from smallcategory order by lcno asc, step asc;
select scate.no as sno, scate.name as sname, scate.step as sstep from
		(select * from smallcategory order by lcno asc, step asc) scate where
		lcno=1 order by lcno asc, step asc;
        
commit;

--getAllCategory()
select lcate.no as lno, lcate.name as lname, lcate.step as lstep, scate.no as sno, scate.name as sname, scate.step as sstep
from (select * from bigcategory order by step asc) lcate left outer join (select * from smallcategory order by lcno asc, step asc) scate
on lcate.no = scate.lcno
order by lstep asc, sstep asc;

commit;


------------------------------------------------------------------------
-- 회원 테이블 생성



select * from member;

-----------------------------------------------------

--상품문의 게시판 

select * from productQnA order by board_no desc;

--product_QnA 시퀀스 생성



commit;

--------------------------------------------------------------
--상품문의 게시판 댓글




--ReplyproductQnA 시퀀스 생성


commit;
select * from ReplyproductQnA;

--------------------------------------------------------------

-- 상품 테이블







select * from product;

--getAllProduct()
select pro.NO, pro.LCNAME, scate.name as scname, pro.NAME, pro.ORIPRICE, pro.DISCPRICE, pro.INFO, pro.MAINIMGN, pro.DETAILIMGN1, pro.DETAILIMGN2, pro.DETAILIMGN3, pro.DETAILIMGN4 
from smallcategory scate inner join (select pro.NO, lcate.NAME as lcname, pro.SCNO, pro.NAME, pro.ORIPRICE, pro.DISCPRICE, pro.INFO, pro.MAINIMGN, pro.DETAILIMGN1, pro.DETAILIMGN2, pro.DETAILIMGN3, pro.DETAILIMGN4
from bigcategory lcate inner join product pro
on lcate.no = pro.lcno) pro
on scate.no = pro.scno;


-----------------------------------------------------------------------
-- 사이즈별 재고 테이블




select * from stock;
commit;

-----------------------------------------------------------------------
-- 장바구니 테이블 생성




select * from cart;
commit;

------------------------------------------------------------------------
------------------------------------------------------------------------
-- 주문내역 테이블 생성

create sequence orderseq   
increment by 1
start with 1
minvalue 1
maxvalue 10000
nocache;

create table orderlist(
	no number  primary key,
	mno number references member(no) on delete cascade, -- 유저번호
	pno number references product(no) on delete cascade, -- 상품 번호 
	opname varchar2(40),
	qty number,
	price number,
	time date,    					-- 결제가 이뤄진 시간
	receiver varchar(40), 	-- 받는 사람
	rv_hp1 varchar2(30),
	rv_hp2 varchar2(30),
	rv_hp3 varchar2(30),
	rv_zip varchar2(20),
	rv_addr1 varchar2(50),
	rv_addr2 varchar2(50)
);

commit;

SELECT * FROM orderlist;
--------------------포토후기-----------------------------


select * from review order by board_no desc;



--review 시퀀스 생성


select review_no_seq.nextval from dual;
commit;

insert into review (board_no,board_name,board_title,board_pwd,board_cont) values('1','홍길동','1111','1111','1122222');
 select * from review;


 
 
-----------------포토 후기 댓글------------------------
 


 
 select * from reviewReply order by board_no desc;   
 
 --reviewReply 시퀀스 생성


select reviewReply_no_seq.nextval from dual;

commit;

select table_name, constraint_type, constraint_name, r_constraint_name from user_constraints
where table_name in('REVIEW', 'REVIEWREPLY');

SELECT rowNum rNum, pro.NO, pro.LCNAME, scate.name AS SCNAME, pro.NAME, pro.ORIPRICE, pro.DISCPRICE, pro.INFO, pro.MAINIMGN, pro.DETAILIMGN1, pro.DETAILIMGN2, pro.DETAILIMGN3, pro.DETAILIMGN4
   FROM smallcategory scate INNER JOIN (
    SELECT rowNum rNum, pro.NO, lcate.NAME AS lcname, pro.SCNO, pro.NAME, pro.ORIPRICE, pro.DISCPRICE, pro.INFO, pro.MAINIMGN, pro.DETAILIMGN1, pro.DETAILIMGN2, pro.DETAILIMGN3, pro.DETAILIMGN4
    FROM bigcategory lcate
    INNER JOIN product pro ON lcate.no = pro.lcno
    WHERE lcate.no = 1
   ) pro ON scate.no = pro.scno 
   AND rNum >= 1 AND rNum <= 8 order by pro.discPRICE asc;
   
   select * from bigcategory  order by step asc;
   
   SELECT rowNum rNum, pro.NO, pro.LCNAME, scate.name AS SCNAME, pro.NAME, pro.ORIPRICE, pro.DISCPRICE, pro.INFO, pro.MAINIMGN, pro.DETAILIMGN1, pro.DETAILIMGN2, pro.DETAILIMGN3, pro.DETAILIMGN4
   FROM smallcategory scate INNER JOIN (
    SELECT rowNum rNum, pro.NO, lcate.NAME AS lcname, pro.SCNO, pro.NAME, pro.ORIPRICE, pro.DISCPRICE, pro.INFO, pro.MAINIMGN, pro.DETAILIMGN1, pro.DETAILIMGN2, pro.DETAILIMGN3, pro.DETAILIMGN4
    FROM bigcategory lcate
    INNER JOIN product pro ON lcate.no = pro.lcno
    WHERE lcate.no = 1 order by pro.NAME asc
   ) pro ON scate.no = pro.scno 
   AND rNum >= 9 AND rNum <= 16;
   


---------------------------------------------------------
--공지사항

select * from notice order by board_no asc;

--공지사항 시퀀스


commit;


---------------------
--공지사항 댓글

select * from Replynotice;
-------------------------------------
 --Replynotice 시퀀스 생성


commit;


-----------------------------------------------

--마이페이지 관심상품 시퀀스

--마이페이지 관심상품

select * from wishlist;


