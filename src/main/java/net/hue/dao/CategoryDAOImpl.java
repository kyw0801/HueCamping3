package net.hue.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.hue.vo.CategoryVO;

@Repository
public class CategoryDAOImpl implements CategoryDAO {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<CategoryVO> getAllCategory(CategoryVO c) {
		return this.sqlSession.selectList("category_all", c);
	}

	// 대분류 추가
	@Override
	public int insertLCategory(CategoryVO c) {
		return this.sqlSession.insert("insertLCategory", c);
	}
	// 소분류 추가
	@Override
	public int insertSCategory(CategoryVO c) {
		return this.sqlSession.insert("insertSCategory", c);
	}


	// 상품등록-대분류리스트
	@Override
	public List<CategoryVO> getLCategory(CategoryVO c) {
		return this.sqlSession.selectList("getLCategory", c);
	}
	// 상품등록-소분류리스트
	@Override
	public List<CategoryVO> getOnlySmallCategory(int lno) {
		return this.sqlSession.selectList("getSCategory", lno);
	}

	// 대분류 카운트
	@Override
	public int countLcategory(CategoryVO c) {
		return this.sqlSession.selectOne("countLcategory", c);
	}
	// 소분류 카운트
	@Override
	public int countScateory(int lno) {
		return this.sqlSession.selectOne("countScateory", lno);
	}
	
	// 카테고리 삭제 - 대분류
	@Override
	public int deleteLCategory(int lno, int lstep) {
		this.sqlSession.update("deleteLCategoryU", lstep);
		return this.sqlSession.delete("deleteLCategory", lno);		
	}
	// 카테고리 삭제 - 소분류
	@Override
	public int updateSStep(int sno, int sstep) {
		Map<String, Object> params = new HashMap<>();
		params.put("sno", sno);
		params.put("sstep", sstep);
		System.out.println(sno+", "+sstep);
		return sqlSession.update("updateSStep", params);
	}
	@Override
	public int deleteSCategory(int sno) {
		return sqlSession.delete("deleteSCategory", sno);
	}
	@Override
	public int getSStep(int sno) {
		return sqlSession.selectOne("getSStep", sno);
	}

	// 카테고리 수정 - 대분류
	@Override
	public int updateLCategoryName(String lcname, int lno) {
	    Map<String, Object> params = new HashMap<>();
	    params.put("lcname", lcname);
	    params.put("lno", lno);
	    return sqlSession.update("updateLCategoryName", params);
	}
	// 카테고리 수정 - 소분류
	@Override
	public int updateSCategoryName(String scname, int sno) {
		Map<String, Object> params = new HashMap<>();
	    params.put("scname", scname);
	    params.put("sno", sno);
	    return sqlSession.update("updateSCategoryName", params);
	}

	// 카테고리 이동 - 대분류 위
	@Override
	public int updateUpLStep(int lno, int lstep) {
		Map<String, Object> params = new HashMap<>();
		params.put("lno", lno);
		params.put("lstep", lstep);
		return sqlSession.update("updateUpLStep", params);
	}
	// 카테고리 이동 - 대분류 위
	@Override
	public int updateDownLStep(int lno, int lstep) {
		Map<String, Object> params = new HashMap<>();
		params.put("lno", lno);
		params.put("lstep", lstep);
		return sqlSession.update("updateDownLStep", params);
	}
	// 카테고리 이동 - 소분류 위
	@Override
	public int updateUpSStep(int lno, int sno, int sstep) {
		Map<String, Object> params = new HashMap<>();
		params.put("lno", lno);
		params.put("sno", sno);
		params.put("sstep", sstep);
		return sqlSession.update("updateUpSStep", params);
	}
	// 카테고리 이동 - 소분류 아래
	@Override
	public int updateDownSStep(int lno, int sno, int sstep) {
		Map<String, Object> params = new HashMap<>();
		params.put("lno", lno);
		params.put("sno", sno);
		params.put("sstep", sstep);	
		return sqlSession.update("updateDownSStep", params);
	}


}
