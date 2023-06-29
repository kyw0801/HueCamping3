package net.hue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.hue.dao.ProductQnADAO;
import net.hue.vo.ProductQnAReplyVO;
import net.hue.vo.ProductQnAVO;

@Service
public class ProductQnAServiceImpl implements ProductQnAService {

	@Autowired
	private ProductQnADAO productQnADao;
	
	@Override
	public int getListCount(ProductQnAVO pq) {
		return this.productQnADao.getListCount(pq);
	}

	@Override
	public List<ProductQnAVO> getBoardList(ProductQnAVO pq) {
		return this.productQnADao.getBoardList(pq);
	}

	@Override
	public ProductQnAVO getBoardView(int board_no) {
		return this.productQnADao.getBoardView(board_no);
	}

	@Override
	public void insertBoard(ProductQnAVO pq) {
		this.productQnADao.insertBoard(pq);
		
	}

	@Override
	public void editBoard(ProductQnAVO epq) {
		this.productQnADao.editBoard(epq);
		
	}

	@Override
	public void delBoard(int board_no) {
		this.productQnADao.delBoard(board_no);
		
	}

	@Override
	public void insertReply(ProductQnAReplyVO pq) {
		this.productQnADao.insertReply(pq);
		
	}

	@Override
	public List<ProductQnAReplyVO> getReplyView(int board_no, int page) {
		return this.productQnADao.getReplyView(board_no,page);
	}

}
