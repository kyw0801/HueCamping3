package net.hue.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.hue.vo.CartVO;
import net.hue.vo.MemberVO;
import net.hue.vo.OrderVO;
import net.hue.vo.ProductVO;

@Repository
public class OrderDAOImpl implements OrderDAO {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<OrderVO> getAllOrder(OrderVO o) {
		return sqlSession.selectList("all_order", o);
	}
	
	// 어드민 주문관리
	@Override
	public int getListCount(OrderVO o) {
		return this.sqlSession.selectOne("order_count", o);
	}
	@Override
	public List<OrderVO> getOrderList(OrderVO o) {
		return this.sqlSession.selectList("Aorder_list", o);
	}

	// 주문 삭제
	@Override
	public int deleteorder(int pno) {
		return this.sqlSession.delete("deleteorder", pno);
	}

	// 주문 OK
	@Override
	public void insertOrder(CartVO ctbean, MemberVO mbean) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("ctbean", ctbean);
	    parameters.put("mbean", mbean);
		this.sqlSession.insert("insertOrder", parameters);
	}

}
