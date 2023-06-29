package net.hue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.hue.dao.MemberMypageDAO;
import net.hue.vo.MemberVO;
import net.hue.vo.OrderVO;
import net.hue.vo.WishlistVO;

@Service
public class MemberMypageServiceImpl implements MemberMypageService {

	@Autowired
	private MemberMypageDAO memberMypageDao;
	
	@Override//메인 mypage
	public MemberVO getMemberMypage(String mypage) {
		return this.memberMypageDao.getMemberMypage(mypage);
	}
	
	//23.05.23
	@Override//회원 탈퇴
	public void delMember(MemberVO m) {
		this.memberMypageDao.delMember(m);
	}

	@Override // 회원탈퇴 OK
	public int getMemberByIdEmailAndPassword(MemberVO m) {
		return this.memberMypageDao.getMemberByIdEmailAndPassword(m);
	}
	/*
	@Override//회원정보 수정
	public MemberVO contmember(String id) {
		return this.memberMypageDao.contmember(id);
	}*/
	/*
	@Override//회원정보 수정
	public void contmember(MemberVO m) {
		this.memberMypageDao.contmember(m);
	}*/
	@Override//회원정보 수정
	public MemberVO contmember(int mno) {
		return this.memberMypageDao.contmember(mno);
	}
	
	@Override//회원정보 수정 ok
	public int editMember(MemberVO m) {
		return this.memberMypageDao.editMember(m);
	}/*
	@Override//회원정보 수정 ok
	public int editMember(MemberVO m,int mno) {
		return this.memberMypageDao.editMember(m,mno);
	}*/
	
	@Override //주문접수내역
	public List<OrderVO> getProList(String memid) {
		 return this.memberMypageDao.getProList(memid);
	}

	
	@Override
	public List<WishlistVO> getAllItem(int mno) {
		return this.memberMypageDao.getAllItem(mno);
	}

	@Override
	public int insertItem(int mno, int pno, int qty, String opname) {
		return this.memberMypageDao.insertItem(mno,pno,qty,opname);
	}

	@Override
	public int delWishlist(int itemno) {
		return this.memberMypageDao.delWishlist(itemno);
		
	}

	@Override
	public int delOrderlist(int no) {
		return this.memberMypageDao.delOrderlist(no);
		
	}



}
