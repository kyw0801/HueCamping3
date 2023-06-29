package net.hue.service;

import java.util.List;

import net.hue.vo.MemberVO;

public interface MemberService {

	MemberVO getMemberById(String memid); //메인헤더

	// 어드민 회원관리
	List<MemberVO> getMemList(MemberVO m);
	int getListCount(MemberVO m);
	
	int insertMember(MemberVO m); // 회원가입

	boolean checkDuplicateId(String id); // 회원가입 중복확인

	MemberVO idCheck(String id); // 중복아이디 체크

	MemberVO findId(MemberVO m); // 아이디찾기

	MemberVO findPwd(MemberVO m); // 비밀번호 찾기

}
