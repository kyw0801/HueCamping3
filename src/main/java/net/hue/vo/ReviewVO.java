package net.hue.vo;

import lombok.Data;

@Data
public class ReviewVO {

	private int board_no;
	private String board_name;
	private String board_title;
	private String board_pwd;
	private String board_cont;
	private int board_hit;
	private int board_ref;
	private int board_step;
	private int board_level;
	private String board_date;
	/*   추가  */
	private String board_file1;
	
	//페이징(쪽 나누기) 관련 변수
	private int startrow;//시작행 번호
	private int endrow;//끝행 번호
		
	//검색 기능 관련 변수 
	private String find_field;//검색필드
	private String find_name;//검색어
}
