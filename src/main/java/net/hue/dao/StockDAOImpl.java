package net.hue.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.oreilly.servlet.MultipartRequest;

import net.hue.vo.StockVO;

@Repository
public class StockDAOImpl implements StockDAO {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<StockVO> getAllStockByPno(int pno) {
		return this.sqlSession.selectList("Stock_Pno", pno);
	}

	// 어드민 상품 등록
	@Override
	public int insertStock(StockVO s) {
		return this.sqlSession.insert("insertStock", s);
	}
	// 어드민 상품 삭제
	@Override
	public int deleteAllStock(int pno) {
		return this.sqlSession.delete("deleteAllStock", pno);
	}

	// 주문 OK
	@Override
	public void updateByOrder(int pno, String opname, int qty) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("pno", pno);
	    parameters.put("opname", opname);
	    parameters.put("qty", qty);
		this.sqlSession.update("updateByOrder", parameters);
		
	}

}
