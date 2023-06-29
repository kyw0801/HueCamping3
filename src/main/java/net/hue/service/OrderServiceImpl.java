package net.hue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.hue.dao.OrderDAO;
import net.hue.vo.CartVO;
import net.hue.vo.MemberVO;
import net.hue.vo.OrderVO;
import net.hue.vo.ProductVO;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderDAO orderDAO;
	
	@Override
	public List<OrderVO> getAllOrder(OrderVO o) {
		return orderDAO.getAllOrder(o);
	}
	
	// 어드민 주문관리
	@Override
	public int getListCount(OrderVO o) {
		return orderDAO.getListCount(o);
	}
	@Override
	public List<OrderVO> getOrderList(OrderVO o) {
		return orderDAO.getOrderList(o);
	}

	// 주문 삭제
	@Override
	public int deleteorder(int pno) {
		return orderDAO.deleteorder(pno);
	}

	// 주문 OK
	@Override
	public void insertOrder(CartVO ctbean, MemberVO mbean) {
		this.orderDAO.insertOrder(ctbean, mbean);
	}
	
	
}
