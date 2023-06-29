package net.hue.vo;

import lombok.Data;

@Data
public class WishlistVO {

	int no;		   	// 시리얼넘버
	String pname;  	// 상품명
	int pno;
	String opname; 	// 옵션명
	int qty;	   	// 수량
	int oneprice;	// 단가
	String mainimgn;
	
	//페이징(쪽 나누기) 관련 변수
	private int startrow;//시작행 번호
	private int endrow;//끝행 번호
	
	//검색 기능 관련 변수 
	private String find_field;//검색필드
	private String find_name;//검색어
}
