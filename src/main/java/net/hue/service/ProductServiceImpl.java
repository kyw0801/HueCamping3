package net.hue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oreilly.servlet.MultipartRequest;

import net.hue.dao.ProductDAO;
import net.hue.vo.ProductVO;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDAO productDao;

	@Override
	public List<ProductVO> getAllProduct(ProductVO p) {
		return productDao.getAllProduct(p);
	}
	@Override
	public List<ProductVO> getAllProduct2(ProductVO p) {
		return productDao.getAllProduct2(p);
	}
	@Override
	public List<ProductVO> getAllProduct3(ProductVO p) {
		return productDao.getAllProduct3(p);
	}

	// 어드민 상품관리
	@Override
	public int getListCount(ProductVO p) {
		return productDao.getListCount(p);
	}
	@Override
	public List<ProductVO> getProdList(ProductVO p) {
		return productDao.getProdList(p);
	}
	
	@Override
	public int getLargeListCount(int lcno) {
		return productDao.getLargeListCount(lcno);
	}

	@Override
	public List<ProductVO> getLcList(int lcno, ProductVO p) {
		return this.productDao.getLcList(lcno,p);
	}
	@Override
	public List<ProductVO> getLcList2(int lcno, ProductVO p) {
		return this.productDao.getLcList2(lcno,p);
	}
	@Override
	public List<ProductVO> getLcList3(int lcno, ProductVO p) {
		return this.productDao.getLcList3(lcno,p);
	}

	@Override
	public int getSmallListCount(int lcno, int scno) {
		return this.productDao.getSmallListCount(lcno,scno);
	}

	@Override
	public List<ProductVO> getScList(int lcno, int scno, ProductVO p) {
		return this.productDao.getScList(lcno,scno,p);
	}
	@Override
	public List<ProductVO> getScList2(int lcno, int scno, ProductVO p) {
		return this.productDao.getScList2(lcno,scno,p);
	}
	@Override
	public List<ProductVO> getScList3(int lcno, int scno, ProductVO p) {
		return this.productDao.getScList3(lcno,scno,p);
	}

	@Override
	public ProductVO getProduct(int no) {
		return this.productDao.getProduct(no);
	}
	
	@Override
	public List<ProductVO> getSearchProduct(ProductVO p) {
		return productDao.getSearchProduct(p);
	}

	@Override
	public int getSearchListCount(ProductVO p) {
		return this.productDao.getSearchListCount(p);
	}

	// 어드민 상품 등록
	@Override
	public int insertProduct(ProductVO p) {
		return this.productDao.insertProduct(p);
	}
	// 어드민 상품 삭제
	@Override
	public int prodDel(int pno) {
		return this.productDao.prodDel(pno);
	}

}
