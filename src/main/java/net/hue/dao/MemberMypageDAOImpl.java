package net.hue.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.hue.vo.MemberVO;
import net.hue.vo.OrderVO;
import net.hue.vo.WishlistVO;

@Repository
public class MemberMypageDAOImpl implements MemberMypageDAO {
	@Autowired
	private SqlSession sqlSession;
	
	@Override//마이페이지 메인창
	public MemberVO getMemberMypage(String mypage) {
		
		return this.sqlSession.selectOne("mypage", mypage);
	}
	
	//23.05.23
	@Override//회원 탈퇴 
	public void delMember(MemberVO m) {
		 this.sqlSession.selectOne("mem_del",m);
	}
	

	@Override //회원 탈퇴  ok
	public int getMemberByIdEmailAndPassword(MemberVO m) {
		return this.sqlSession.update("mem_del_ok", m);
	}

	@Override//회원정보 수정
	public MemberVO contmember(int mno) {
		return this.sqlSession.selectOne("mem_edit", mno);
	}
	
	@Override//회원정보 수정 ok
	public int editMember(MemberVO m) {
		return this.sqlSession.update("mem_edit_ok", m);
	}

	@Override //주문접수내역
	public List<OrderVO> getProList(String memid) {
		return this.sqlSession.selectList("order_list",memid);
	}

	
	@Override
	public List<WishlistVO> getAllItem(int mno) {
		return this.sqlSession.selectList("wish_list", mno);
	}//관심상품 목록

	@Override
	public int insertItem(int mno, int pno, int qty, String opname) {
		Map<String, Object> parameters = new HashMap<>();
	    parameters.put("mno", mno);
	    parameters.put("pno", pno);
	    parameters.put("qty", qty);
	    parameters.put("opname", opname);		
		return this.sqlSession.insert("wishlist_insert", parameters);
	}//관심상품 추가

	@Override
	public int delWishlist(int itemno) {
		return this.sqlSession.delete("wishlist_del", itemno);
		
	}//관심상품 삭제

	@Override
	public int delOrderlist(int no) {
		return this.sqlSession.delete("orderlist_del", no);
		
	}//주문내역 삭제
}
