package net.hue.dao;

import java.util.List;

import com.oreilly.servlet.MultipartRequest;

import net.hue.vo.StockVO;

public interface StockDAO {

	List<StockVO> getAllStockByPno(int pno); // 제고

	// 어드민 상품 등록
	int insertStock(StockVO s);
	// 어드민 상품 삭제
	int deleteAllStock(int pno);

	// 주문 OK
	void updateByOrder(int pno, String opname, int qty);
}
