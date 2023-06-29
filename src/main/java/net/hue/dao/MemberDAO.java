package net.hue.dao;

import java.util.List;

import net.hue.vo.MemberVO;

public interface MemberDAO {

	MemberVO getMemberById(String memid); //메인헤더,로그인

	// 어드민 회원관리
	List<MemberVO> getMemList(MemberVO m);
	int getListCount(MemberVO m);
	
	int insertMember(MemberVO m); // 회원가입
	
	boolean checkDuplicateId(String id); // 회원가입시 중복확인

	MemberVO idCheck(String id); // 중복아이디체크

	MemberVO findId(MemberVO m); // 아이디찾기

	MemberVO findPwd(MemberVO m); // 비밀번호 찾기

}
