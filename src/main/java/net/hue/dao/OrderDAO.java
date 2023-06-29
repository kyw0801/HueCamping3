package net.hue.dao;

import java.util.List;

import net.hue.vo.CartVO;
import net.hue.vo.MemberVO;
import net.hue.vo.OrderVO;
import net.hue.vo.ProductVO;

public interface OrderDAO {
	
	List<OrderVO> getAllOrder(OrderVO o);
	
	// 어드민 주문관리
	int getListCount(OrderVO o);
	List<OrderVO> getOrderList(OrderVO o);

	// 주문 삭제
	int deleteorder(int pno);

	// 주문 OK
	void insertOrder(CartVO ctbean, MemberVO mbean);

}
