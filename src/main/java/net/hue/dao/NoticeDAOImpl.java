package net.hue.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.hue.vo.NoticeReplyVO;
import net.hue.vo.NoticeVO;

@Repository
public class NoticeDAOImpl implements NoticeDAO {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public void insertBoard(NoticeVO pn) {
		this.sqlSession.insert("notice_write", pn);
		
	}

	@Override
	public int getListCount(NoticeVO pn) {
		return this.sqlSession.selectOne("notice_cnt", pn);
	}

	@Override
	public List<NoticeVO> getBoardList(NoticeVO pn) {
		return this.sqlSession.selectList("notice_list", pn);
	}

	@Override
	public NoticeVO getBoardView(int board_no) {
		return this.sqlSession.selectOne("notice_view", board_no);
	}

	@Override
	public void insertReply(NoticeReplyVO rpn) {
		this.sqlSession.insert("notice_reply", rpn);
		
	}

	@Override
	public List<NoticeReplyVO> getReplyView(int board_no, int page) {
		return this.sqlSession.selectList("notice_replist", board_no);
	}

	@Override
	public void editBoard(NoticeVO epn) {
		this.sqlSession.update("notice_edit", epn);
		
	}

	@Override
	public void delBoard(int board_no) {
		this.sqlSession.delete("notice_del", board_no);
		
	}
}
