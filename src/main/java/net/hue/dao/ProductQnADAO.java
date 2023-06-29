package net.hue.dao;

import java.util.List;

import net.hue.vo.ProductQnAReplyVO;
import net.hue.vo.ProductQnAVO;

public interface ProductQnADAO {

	int getListCount(ProductQnAVO pq);//QnA레코드 개수

	List<ProductQnAVO> getBoardList(ProductQnAVO pq);//QnA목록

	ProductQnAVO getBoardView(int board_no);

	void insertBoard(ProductQnAVO pq);

	void editBoard(ProductQnAVO epq);

	void delBoard(int board_no);

	void insertReply(ProductQnAReplyVO pq);

	List<ProductQnAReplyVO> getReplyView(int board_no, int page);

}
