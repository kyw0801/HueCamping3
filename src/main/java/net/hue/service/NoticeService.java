package net.hue.service;

import java.util.List;

import net.hue.vo.NoticeReplyVO;
import net.hue.vo.NoticeVO;


public interface NoticeService {

	void insertBoard(NoticeVO pn);

	int getListCount(NoticeVO pn);

	List<NoticeVO> getBoardList(NoticeVO pn);

	NoticeVO getBoardView(int board_no);

	void insertReply(NoticeReplyVO rpn);

	List<NoticeReplyVO> getReplyView(int board_no, int page);

	void editBoard(NoticeVO epn);

	void delBoard(int board_no);

}
