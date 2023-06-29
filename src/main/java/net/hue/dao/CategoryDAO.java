package net.hue.dao;

import java.util.List;

import net.hue.vo.CategoryVO;

public interface CategoryDAO {

	List<CategoryVO> getAllCategory(CategoryVO c); // 헤더부분 카테고리

	// 대분류 추가
	int insertLCategory(CategoryVO c);
	// 소분류 추가
	int insertSCategory(CategoryVO c);

	// 대분류 카운트
	int countLcategory(CategoryVO c);
	// 소분류 카운트
	int countScateory(int lno);
	// 카테고리 삭제 - 대분류 삭제
	int deleteLCategory(int lno, int lstep);
	// 카테고리 삭제 - 소분류 삭제
	int updateSStep(int sno, int sstep);
	int deleteSCategory(int sno);
	int getSStep(int sno);

	// 상품등록 - 대분류리스트
	List<CategoryVO> getLCategory(CategoryVO c);
	// 상품등록 - 소분류리스트
	List<CategoryVO> getOnlySmallCategory(int lno);

	// 카테고리 수정 - 대분류
	int updateLCategoryName(String lcname, int lno);
	// 카테고리 수정 - 소분류
	int updateSCategoryName(String scname, int sno);

	// 카테고리 이동 - 대분류 위
	int updateUpLStep(int lno, int lstep);
	// 카테고리 이동 - 대분류 위
	int updateDownLStep(int lno, int lstep);
	// 카테고리 이동 - 소분류 위
	int updateUpSStep(int lno, int sno, int sstep);
	// 카테고리 이동 - 소분류 아래
	int updateDownSStep(int lno, int sno, int sstep);


}
