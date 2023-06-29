package net.hue.vo;

import lombok.Data;

@Data
public class MemberVO {

	int no; // 식별자
	String id; // 아이디
	String password; // 패스워드
	String name; // 이름
	String zip; // 우편번호
	String addr1; // 주소
	String addr2; // 상세주소
	String mobile1; // 핸드폰 앞
	String mobile2; // 핸드폰 중
	String mobile3; // 핸드폰 뒤
	String email; //이메일
	String gender; // 성별
	int state; // 회원여부
	String memdate;
	String deldate;
	
	//페이징(쪽 나누기) 관련 변수
	private int startrow;//시작행 번호
	private int endrow;//끝행 번호

	//검색 기능 관련 변수 
	private String find_field;//검색필드
	private String find_name;//검색어

	public void setMemdate(String memdate) {
		this.memdate = memdate.substring(0,10);
	}
	public String getDeldate() {
		return deldate;
	}
	public void setDeldate(String deldate) {
		if(deldate != null) {
			this.deldate = deldate.substring(0,10);
		}
	}
}
