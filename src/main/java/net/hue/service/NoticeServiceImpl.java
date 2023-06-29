package net.hue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.hue.dao.NoticeDAO;
import net.hue.vo.NoticeReplyVO;
import net.hue.vo.NoticeVO;


@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeDAO noticeDao;

	@Override
	public void insertBoard(NoticeVO pn) {
		this.noticeDao.insertBoard(pn);
		
	}

	@Override
	public int getListCount(NoticeVO pn) {
		return this.noticeDao.getListCount(pn);
	}

	@Override
	public List<NoticeVO> getBoardList(NoticeVO pn) {
		return this.noticeDao.getBoardList(pn);
	}

	@Override
	public NoticeVO getBoardView(int board_no) {
		return this.noticeDao.getBoardView(board_no);
	}

	@Override
	public void insertReply(NoticeReplyVO rpn) {
		this.noticeDao.insertReply(rpn);
		
	}

	@Override
	public List<NoticeReplyVO> getReplyView(int board_no, int page) {
		return this.noticeDao.getReplyView(board_no,page);
	}

	@Override
	public void editBoard(NoticeVO epn) {
		this.noticeDao.editBoard(epn);
		
	}

	@Override
	public void delBoard(int board_no) {
		this.noticeDao.delBoard(board_no);
		
	}
}
