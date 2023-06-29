package net.hue.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.oreilly.servlet.MultipartRequest;

import net.hue.vo.ProductVO;

@Repository
public class ProductDAOImpl implements ProductDAO {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<ProductVO> getAllProduct(ProductVO p) {
		return sqlSession.selectList("all_product", p);
	}
	@Override
	public List<ProductVO> getAllProduct2(ProductVO p) {
		return sqlSession.selectList("all_product2", p);
	}
	@Override
	public List<ProductVO> getAllProduct3(ProductVO p) {
		return sqlSession.selectList("all_product3", p);
	}

	// 어드민 상품관리
	@Override
	public int getListCount(ProductVO p) {
		return sqlSession.selectOne("product_count", p);
	}
	@Override
	public List<ProductVO> getProdList(ProductVO p) {
		return sqlSession.selectList("product_list", p);
	}
	
	@Override
	public int getLargeListCount(int lcno) {
		return this.sqlSession.selectOne("LargeCount", lcno);
	}
	
	@Override
	public List<ProductVO> getLcList(int lcno, ProductVO p) {
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("lcno", lcno);
	    parameters.put("p", p);
	    return sqlSession.selectList("getLcList", parameters);
	}
	@Override
	public List<ProductVO> getLcList2(int lcno, ProductVO p) {
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("lcno", lcno);
	    parameters.put("p", p);
	    return sqlSession.selectList("getLcList2", parameters);
	}
	@Override
	public List<ProductVO> getLcList3(int lcno, ProductVO p) {
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("lcno", lcno);
	    parameters.put("p", p);
	    return sqlSession.selectList("getLcList3", parameters);
	}

	@Override
	public int getSmallListCount(int lcno, int scno) {
	    Map<String, Integer> parameters = new HashMap<>();
	    parameters.put("lcno", lcno);
	    parameters.put("scno", scno);
	    return sqlSession.selectOne("SmallCount", parameters);
	}

	@Override
	public List<ProductVO> getScList(int lcno, int scno, ProductVO p) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("lcno", lcno);
		parameters.put("p", p);
		parameters.put("scno", scno);
		return sqlSession.selectList("getScList", parameters);
	}
	@Override
	public List<ProductVO> getScList2(int lcno, int scno, ProductVO p) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("lcno", lcno);
		parameters.put("p", p);
		parameters.put("scno", scno);
		return sqlSession.selectList("getScList2", parameters);
	}
	@Override
	public List<ProductVO> getScList3(int lcno, int scno, ProductVO p) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("lcno", lcno);
		parameters.put("p", p);
		parameters.put("scno", scno);
		return sqlSession.selectList("getScList3", parameters);
	}

	@Override
	public ProductVO getProduct(int no) {
		return this.sqlSession.selectOne("getProduct", no);
	}
	
	@Override
	public List<ProductVO> getSearchProduct(ProductVO p) {
		return this.sqlSession.selectList("search_product", p);
	}

	@Override
	public int getSearchListCount(ProductVO p) {
		return this.sqlSession.selectOne("search_procnt",p);
	}

	// 어드민 상품 등록
	@Override
	public int insertProduct(ProductVO p) {
		return this.sqlSession.insert("insertProduct", p);
	}
	// 어드민 상품 삭제
	@Override
	public int prodDel(int pno) {
		return this.sqlSession.delete("prodDel", pno);
	}

}
