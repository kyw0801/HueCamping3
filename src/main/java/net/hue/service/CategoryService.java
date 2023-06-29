package net.hue.service;

import java.util.ArrayList;
import java.util.List;

import net.hue.vo.CategoryVO;

public interface CategoryService {

	List<CategoryVO> getAllCategory(CategoryVO c);

	//대분류 추가
	void insertLCategory(CategoryVO c);
	// 소분류 추가
	void insertSCategory(CategoryVO c);

	// 상품등록-대분류리스트
	List<CategoryVO> getLCategory(CategoryVO c);
	// 상품등록-소분류리스트
	List<CategoryVO> getOnlySmallCategory(int lno);
	
	// 소분류 카운트
	int countScategory(int lno);
	// 대분류 카운트
	int countLcategory(CategoryVO c);
	
	// 카테고리 삭제 - 대분류
	int deleteLCategory(int lno, int lstep);
	// 카테고리 삭제 - 소분류
	int deleteSCategory(int sno);

	// 카테고리 수정 - 대분류
	int updateLCategoryName(String lcname, int lno);
	// 카테고리 수정 - 소분류
	int updateSCategoryName(String scname, int sno);

	// 카테고리 이동 - 대분류 위
	int updateUpLStep(int lno, int lstep);
	// 카테고리 이동 - 대분류 아래
	int updateDownLStep(int lno, int lstep);
	// 카테고리 이동 - 소분류 위
	int updateUpSStep(int lno, int sno, int sstep);
	// 카테고리 이동 - 소분류 아래
	int updateDownSStep(int lno, int sno, int sstep);


}
