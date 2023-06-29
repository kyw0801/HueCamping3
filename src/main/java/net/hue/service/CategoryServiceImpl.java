package net.hue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.hue.dao.CategoryDAO;
import net.hue.vo.CategoryVO;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDAO categoryDao;

	@Override
	public List<CategoryVO> getAllCategory(CategoryVO c) {
		return categoryDao.getAllCategory(c);
	}

	// 대분류 추가
	@Override
	public void insertLCategory(CategoryVO c) {
		categoryDao.insertLCategory(c);
	}
	// 소분류 추가
	@Override
	public void insertSCategory(CategoryVO c) {
		categoryDao.insertSCategory(c);
	}

	// 상품등록-대분류리스트
	@Override
	public List<CategoryVO> getLCategory(CategoryVO c) {
		return categoryDao.getLCategory(c);
	}
	// 상품등록-소분류리스트
	@Override
	public List<CategoryVO> getOnlySmallCategory(int lno) {
		return categoryDao.getOnlySmallCategory(lno);
	}
	
	// 대분류 카운트
	@Override
	public int countLcategory(CategoryVO c) {
		return categoryDao.countLcategory(c);
	}
	// 소분류 카운트
	@Override
	public int countScategory(int lno) {
		return categoryDao.countScateory(lno);
	}
	
	// 카테고리 삭제 - 대분류
	@Override
	public int deleteLCategory(int lno, int lstep) {
		return categoryDao.deleteLCategory(lno, lstep);
	}
	// 카테고리 삭제 - 소분류
	@Override
    @Transactional
    public int deleteSCategory(int sno) {
        int cnt = 0;
        int sstep = categoryDao.getSStep(sno);
        cnt += categoryDao.updateSStep(sno, sstep); // 최대값으로 설정하여 삭제할 대상을 맨 뒤로 이동시킴
        cnt += categoryDao.deleteSCategory(sno);
        return cnt;
    }

	// 카테고리 수정 - 대분류
	@Override
	public int updateLCategoryName(String lcname, int lno) {
		return categoryDao.updateLCategoryName(lcname, lno);
	}
	// 카테고리 수정 - 소분류
	@Override
	public int updateSCategoryName(String scname, int sno) {
		return categoryDao.updateSCategoryName(scname, sno);
	}

	// 카테고리 이동 - 대분류 위
	@Override
	public int updateUpLStep(int lno, int lstep) {
		return categoryDao.updateUpLStep(lno, lstep);
	}
	// 카테고리 이동 - 대분류 위
	@Override
	public int updateDownLStep(int lno, int lstep) {
		return categoryDao.updateDownLStep(lno, lstep);
	}
	// 카테고리 이동 - 소분류 위
	@Override
	public int updateUpSStep(int lno, int sno, int sstep) {
		return categoryDao.updateUpSStep(lno, sno, sstep);
	}
	// 카테고리 이동 - 소분류 아래
	@Override
	public int updateDownSStep(int lno, int sno, int sstep) {
		return categoryDao.updateDownSStep(lno, sno, sstep);
	}

}
