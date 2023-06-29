package net.hue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.hue.dao.CartDAO;
import net.hue.vo.CartVO;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartDAO CartDao;

	@Override
	public int countItemInCart(int memno) {
		return this.CartDao.countItemInCart(memno);
	}

	@Override
	public List<CartVO> getAllItem(int mno) {
		return this.CartDao.getAllItem(mno);
	}

	@Override
	public int deleteItem(int itemno) {
		return this.CartDao.deleteItem(itemno);
	}

	@Override
	public int insertItem(int mno, int pno, int qty, String opname) {
		return this.CartDao.insertItem(mno,pno,qty,opname);
	}

	@Override
	public int updateItem(int no, int qty) {
		return this.CartDao.updateItem(no,qty);
	}
	
	@Override
	public CartVO getItem(int ctno) {
		return this.CartDao.getItem(ctno);
	}

	@Override
	public List<CartVO> selectItem(int ctno) {
		return this.CartDao.selectItem(ctno);
	}

	@Override
	public int getMaxCtno(int mno) {
		return this.CartDao.getMaxCtno(mno);
	}
}
