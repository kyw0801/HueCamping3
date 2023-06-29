package net.hue.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.hue.vo.ProductQnAReplyVO;
import net.hue.vo.ProductQnAVO;

@Repository
public class ProductQnADAOImpl implements ProductQnADAO {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public int getListCount(ProductQnAVO pq) {
		return this.sqlSession.selectOne("prolist_cnt", pq);
	}

	@Override
	public List<ProductQnAVO> getBoardList(ProductQnAVO pq) {
		return this.sqlSession.selectList("prolist", pq);
	}

	@Override
	public ProductQnAVO getBoardView(int board_no) {
		return this.sqlSession.selectOne("proview", board_no);
	}

	@Override
	public void insertBoard(ProductQnAVO pq) {
		this.sqlSession.insert("prowrite", pq);
		
	}

	@Override
	public void editBoard(ProductQnAVO epq) {
		this.sqlSession.update("proedit", epq);
		
	}

	@Override
	public void delBoard(int board_no) {
		this.sqlSession.delete("prodel", board_no);
		
	}

	@Override
	public void insertReply(ProductQnAReplyVO pq) {
		this.sqlSession.insert("proreply", pq);
		
	}

	@Override
	public List<ProductQnAReplyVO> getReplyView(int board_no, int page) {
		return this.sqlSession.selectList("proreplist", board_no);
	}

}
