package net.hue.vo;

import lombok.Data;

@Data
public class CartVO {

	int no;		   	// 시리얼넘버
	String pname;  	// 상품명
	int pno;
	String opname; 	// 옵션명
	int qty;	   	// 수량
	int oneprice;	// 단가
	String mainimgn; // 메인상품 이미지 파일명
}
