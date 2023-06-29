package net.hue.vo;

import lombok.Data;

@Data
public class OrderVO {
	
	private int no;
	private String id;
	private String name;		// 상품명
	private int pno;
	private String mainimgn;		// 상품이미지 파일명
	private String opname;		// 옵션명
	private int qty;			// 주문수량
	private int price;
	private String time;      // 결제시간
	private String receiver;
	private String rv_hp1;
	private String rv_hp2;
	private String rv_hp3;
	private String rv_zip;
	private String rv_addr1;
	private String rv_addr2;
	
	//페이징 관련 변수
	private int startrow;//시작행 번호
	private int endrow;//끝행번호
	
	 //검색 관련 변수
	private String find_field;//검색 필드
	private String find_name;//검색어

}
