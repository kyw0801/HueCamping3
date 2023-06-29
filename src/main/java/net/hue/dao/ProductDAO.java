package net.hue.dao;

import java.util.List;

import com.oreilly.servlet.MultipartRequest;

import net.hue.vo.ProductVO;

public interface ProductDAO {

	List<ProductVO> getAllProduct(ProductVO p);
	List<ProductVO> getAllProduct2(ProductVO p);
	List<ProductVO> getAllProduct3(ProductVO p);

	// 어드민 상품관리
	int getListCount(ProductVO p);
	List<ProductVO> getProdList(ProductVO p);
	
	int getLargeListCount(int lcno); // 대분류 카테고리 개수

	//대분류 페이징 리스트
	List<ProductVO> getLcList(int lcno, ProductVO p);
	List<ProductVO> getLcList2(int lcno, ProductVO p);
	List<ProductVO> getLcList3(int lcno, ProductVO p);

	int getSmallListCount(int lcno, int scno); // 소분류 카테고리 상품 갯수

	//소분류 페이징 리스트
	List<ProductVO> getScList(int lcno, int scno, ProductVO p);
	List<ProductVO> getScList2(int lcno, int scno, ProductVO p);
	List<ProductVO> getScList3(int lcno, int scno, ProductVO p);

	ProductVO getProduct(int no); // 상품정보 확인
	
	List<ProductVO> getSearchProduct(ProductVO p);

	int getSearchListCount(ProductVO p);

	// 어드민 상품 등록
	int insertProduct(ProductVO p);
	// 어드민 상품 삭제
	int prodDel(int pno);

}
