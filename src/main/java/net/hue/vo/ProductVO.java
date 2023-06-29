package net.hue.vo;

import lombok.Data;

@Data
public class ProductVO {
	
	private int no;
	private String lcname;
	private String scname;
	private String name;
	private int oriprice;
	private int discprice;
	private String info; 
	private String mainImgN;
	private String detailImgN1;
	private String detailImgN2;
	private String detailImgN3;
	private String detailImgN4;
	
	//페이징(쪽 나누기) 관련 변수
	private int startrow;//시작행 번호
	private int endrow;//끝행 번호
	
	//검색 기능 관련 변수 
	private String find_field;//검색필드
	private String find_name;//검색어
			
}
