package net.hue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.hue.dao.ReviewDAO;
import net.hue.vo.ReviewReplyVO;
import net.hue.vo.ReviewVO;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewDAO reviewDao;

	@Override
	public int getListCount(ReviewVO pr) {
		return this.reviewDao.getListCount(pr);
	}

	@Override
	public List<ReviewVO> getBoardList(ReviewVO pr) {
		return this.reviewDao.getBoardList(pr);
	}

	@Override
	public ReviewVO getBoardView(int board_no) {
		return this.reviewDao.getBoardView(board_no);
	}

	@Override
	public void updateHit(int board_no) {
		this.reviewDao.updateHit(board_no);
		
	}

	@Override
	public void insertBoard(ReviewVO pr) {
		this.reviewDao.insertBoard(pr);
		
	}

	@Override
	public void delReview(int board_no) {
		this.reviewDao.delReview(board_no);
		
	}

	@Override
	public void insertReply(ReviewReplyVO pr_re) {
		this.reviewDao.insertReply(pr_re);
		
	}

	@Override
	public List<ReviewReplyVO> getReplyView(int board_no, int page) {
		return this.reviewDao.getReplyView(board_no,page);
	}

	@Override
	public void editBoard(ReviewVO epr) {
		this.reviewDao.editBoard(epr);
	}

	
}
