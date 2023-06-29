package net.hue.service;

import java.util.List;

import net.hue.vo.CartVO;

public interface CartService {

	int countItemInCart(int memno); // 헤더 장바구니 갯수 확인

	List<CartVO> getAllItem(int mno); // 마이페이지 장바구니 목록

	int deleteItem(int itemno); //장바구니 삭제

	int insertItem(int mno, int pno, int qty, String opname); // 장바구니 추가

	int updateItem(int no, int qty); // 장바구니 수정
	
	CartVO getItem(int ctno);

	List<CartVO> selectItem(int ctno);

	int getMaxCtno(int mno);

}
