package net.hue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oreilly.servlet.MultipartRequest;

import net.hue.dao.StockDAO;
import net.hue.vo.StockVO;

@Service
public class StockServiceImpl implements StockService {

	@Autowired
	private StockDAO stockDao;
	
	@Override
	public List<StockVO> getAllStockByPno(int pno) {
		return this.stockDao.getAllStockByPno(pno);
	}

	// 어드민 상품 등록
	@Override
	public int insertStock(StockVO s) {
		return this.stockDao.insertStock(s);
	}
	// 어드민 상품 삭제
	@Override
	public int deleteAllStock(int pno) {
		return this.stockDao.deleteAllStock(pno);
	}

	// 주문 OK
	@Override
	public void updateByOrder(int pno, String opname, int qty) {
		this.stockDao.updateByOrder(pno, opname, qty);
	}


}
