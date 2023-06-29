package net.hue.dao;

import java.util.List;

import net.hue.vo.MemberVO;
import net.hue.vo.OrderVO;
import net.hue.vo.WishlistVO;

public interface MemberMypageDAO {
	MemberVO getMemberMypage (String mypage);//메인 mypage
	void delMember(MemberVO m);//회원탈퇴
	int getMemberByIdEmailAndPassword(MemberVO m); // 회원탈퇴 oK
	MemberVO contmember(int mno);//회원정보 수정
	int editMember(MemberVO m);//회원정보 수정 ok
	List<OrderVO> getProList(String memid);//주문접수내역
	List<WishlistVO> getAllItem(int mno);
	int insertItem(int mno, int pno, int qty, String opname);
	int delWishlist(int itemno);
	int delOrderlist(int no);
}