package com.spring.reply.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.reply.vo.ReplyVO;

@Repository
public class ReplyDaoImpl implements ReplyDao {
	
	@Autowired
	private SqlSession session;
	
	Logger logger = Logger.getLogger(ReplyDaoImpl.class);

	@Override
	public List<ReplyVO> replyList(String b_num) {
		// TODO Auto-generated method stub
		logger.info("ReplyDaoImpl.replyList >>>");
		logger.info("ReplyDaoImpl.replyList <<<");
		return session.selectList("replyList",b_num);
	}

	@Override
	public int replyInsert(ReplyVO rvo) {
		// TODO Auto-generated method stub
		logger.info("ReplyDaoImpl.replyInsert >>>");
		logger.info("ReplyDaoImpl.replyInsert <<<");
		return session.insert("replyInsert");
	}

	@Override
	public int replyUpdate(ReplyVO rvo) {
		// TODO Auto-generated method stub
		logger.info("ReplyDaoImpl.replyUpdate >>>");
		logger.info("ReplyDaoImpl.replyUpdate <<<");
		return session.update("replyUpdate");
	}

	@Override
	public int replyDelete(String r_num) {
		// TODO Auto-generated method stub
		logger.info("ReplyDaoImpl.replyDelete >>>");
		logger.info("ReplyDaoImpl.replyDelete <<<");
		return session.delete("replyInsert",r_num);
	}

	@Override
	public ReplyVO replyChaebun() {
		// TODO Auto-generated method stub
		logger.info("ReplyDaoImpl.replyChaebun >>>");
		logger.info("ReplyDaoImpl.replyChaebun <<<");
		return session.selectOne("replyChaebun");
	}

}
