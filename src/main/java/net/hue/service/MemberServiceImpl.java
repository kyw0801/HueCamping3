package net.hue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.hue.dao.MemberDAO;
import net.hue.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO memberDao;

	@Override
	public MemberVO getMemberById(String memid) {
		return this.memberDao.getMemberById(memid);
	}

	// 어드민 회원관리
	@Override
	public List<MemberVO> getMemList(MemberVO m) {
		return memberDao.getMemList(m);
	}
	@Override
	public int getListCount(MemberVO m) {
		return memberDao.getListCount(m);
	}
	
	@Override
	public int insertMember(MemberVO m) {
		return this.memberDao.insertMember(m);
	}

	@Override
	public boolean checkDuplicateId(String id) {
		boolean isDuplicate = memberDao.checkDuplicateId(id);
		return isDuplicate;
	}

	@Override
	public MemberVO idCheck(String id) {
		return this.memberDao.idCheck(id);
	}

	@Override
	public MemberVO findId(MemberVO m) {
		return this.memberDao.findId(m);
	}

	@Override
	public MemberVO findPwd(MemberVO m) {
		return this.memberDao.findPwd(m);
	}

}
