package net.hue.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.hue.vo.ReviewReplyVO;
import net.hue.vo.ReviewVO;

@Repository
public class ReviewDAOImpl implements ReviewDAO {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public int getListCount(ReviewVO pr) {
		return this.sqlSession.selectOne("revlist_cnt", pr);
	}

	@Override
	public List<ReviewVO> getBoardList(ReviewVO pr) {
		return this.sqlSession.selectList("revlist", pr);
	}

	@Override
	public ReviewVO getBoardView(int board_no) {
		return this.sqlSession.selectOne("revview", board_no);
	}

	@Override
	public void updateHit(int board_no) {
		this.sqlSession.update("revhit", board_no);
		
	}

	@Override
	public void insertBoard(ReviewVO pr) {
		this.sqlSession.insert("revwrite", pr);
		
	}

	@Override
	public void delReview(int board_no) {
		this.sqlSession.delete("revdel", board_no);
		
	}

	@Override
	public void insertReply(ReviewReplyVO pr_re) {
		this.sqlSession.insert("revreply", pr_re);
		
	}

	@Override
	public List<ReviewReplyVO> getReplyView(int board_no, int page) {
		return this.sqlSession.selectList("revreplist", board_no);
	}

	@Override
	public void editBoard(ReviewVO epr) {
		this.sqlSession.update("review_edit", epr);
	}

	
}
