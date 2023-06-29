package net.hue.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.hue.vo.CartVO;

@Repository
public class CartDAOImpl implements CartDAO {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public int countItemInCart(int memno) {
		return this.sqlSession.selectOne("count_cart", memno);
	}

	@Override
	public List<CartVO> getAllItem(int mno) {
		return this.sqlSession.selectList("All_Cart", mno);
	}

	@Override
	public int deleteItem(int itemno) {
		return this.sqlSession.delete("delete_cart", itemno);
	}

	@Override
	public int insertItem(int mno, int pno, int qty, String opname) {
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("mno", mno);
	    parameters.put("pno", pno);
	    parameters.put("qty", qty);
	    parameters.put("opname", opname);
	    return sqlSession.insert("insert_cart", parameters);
	}

	@Override
	public int updateItem(int no, int qty) {
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("no", no);
	    parameters.put("qty", qty);
	    return sqlSession.update("update_cart", parameters);
	}
	
	@Override
	public void getSelectItem(String[] rowcheck) {
		this.sqlSession.selectOne("select_cart", rowcheck);
		
	}

	@Override
	public CartVO getItem(int ctno) {
		return this.sqlSession.selectOne("getItem", ctno);
	}

	@Override
	public List<CartVO> selectItem(int ctno) {
		return this.sqlSession.selectList("selectItem",ctno);
	}

	@Override
	public int getMaxCtno(int mno) {
		return this.sqlSession.selectOne("getMaxCtno", mno);
	}
}
