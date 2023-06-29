package net.hue.service;

import java.util.List;


import net.hue.vo.ProductQnAReplyVO;
import net.hue.vo.ProductQnAVO;


public interface ProductQnAService {

	int getListCount(ProductQnAVO pq);

	List<ProductQnAVO> getBoardList(ProductQnAVO pq);

	ProductQnAVO getBoardView(int board_no);

	void insertBoard(ProductQnAVO pq);

	void editBoard(ProductQnAVO epq);

	void delBoard(int board_no);

	void insertReply(ProductQnAReplyVO pq);

	List<ProductQnAReplyVO> getReplyView(int board_no, int page);

	

}
