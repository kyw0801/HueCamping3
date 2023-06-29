package net.hue.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.hue.vo.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public MemberVO getMemberById(String memid) {
		return this.sqlSession.selectOne("mem_byId", memid);
	}

	// 어드민 회원관리
	@Override
	public List<MemberVO> getMemList(MemberVO m) {
		return this.sqlSession.selectList("member_list", m);
	}
	@Override
	public int getListCount(MemberVO m) {
		return this.sqlSession.selectOne("member_count", m);
	}
	
	@Override
	public int insertMember(MemberVO m) {
		return this.sqlSession.insert("mem_join", m);
	}

	@Override
	public boolean checkDuplicateId(String id) {
		int count = sqlSession.selectOne("chekDiplicateId", id);
		boolean isDuplicate = (count > 0);
		return isDuplicate;
	}

	@Override
	public MemberVO idCheck(String id) {
		return this.sqlSession.selectOne("idCheck", id);
	}

	@Override
	public MemberVO findId(MemberVO m) {
		return this.sqlSession.selectOne("find_id", m);
	}

	@Override
	public MemberVO findPwd(MemberVO m) {
		return this.sqlSession.selectOne("find_pwd", m);
	}
}
