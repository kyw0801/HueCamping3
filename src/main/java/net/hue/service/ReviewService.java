package net.hue.service;

import java.util.List;

import net.hue.vo.ReviewReplyVO;
import net.hue.vo.ReviewVO;

public interface ReviewService {

	int getListCount(ReviewVO pr);

	List<ReviewVO> getBoardList(ReviewVO pr);

	ReviewVO getBoardView(int board_no);

	void updateHit(int board_no);

	void insertBoard(ReviewVO pr);

	void delReview(int board_no);

	void insertReply(ReviewReplyVO pr_re);

	List<ReviewReplyVO> getReplyView(int board_no, int page);

	void editBoard(ReviewVO epr);


}
