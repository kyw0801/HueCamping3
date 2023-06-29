package net.hue.service;

import java.util.List;

import net.hue.vo.CartVO;
import net.hue.vo.MemberVO;
import net.hue.vo.OrderVO;
import net.hue.vo.ProductVO;

public interface OrderService {
	
	List<OrderVO> getAllOrder(OrderVO p);

	// 어드민 주문관리
	int getListCount(OrderVO o);
	List<OrderVO> getOrderList(OrderVO o);

	// 주문 삭제
	int deleteorder(int pno);

	// 주문 OK
	void insertOrder(CartVO ctbean, MemberVO mbean);

}
