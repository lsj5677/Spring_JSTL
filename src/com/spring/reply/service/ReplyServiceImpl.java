package com.spring.reply.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.reply.dao.ReplyDao;
import com.spring.reply.vo.ReplyVO;

@Service
@Transactional
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyDao replyDao;
	
	Logger logger = Logger.getLogger(ReplyServiceImpl.class);
	
	@Override
	public List<ReplyVO> replyList(String b_num) {
		// TODO Auto-generated method stub
		logger.info("ReplyServiceImpl.replyList >>>");
		logger.info("ReplyServiceImpl.replyList <<<");
		return replyDao.replyList(b_num);
	}

	@Override
	public int replyInsert(ReplyVO rvo) {
		// TODO Auto-generated method stub
		logger.info("ReplyServiceImpl.replyInsert >>>");
		logger.info("ReplyServiceImpl.replyInsert <<<");
		return replyDao.replyInsert(rvo);
	}

	@Override
	public int replyUpdate(ReplyVO rvo) {
		// TODO Auto-generated method stub
		logger.info("ReplyServiceImpl.replyUpdate >>>");
		logger.info("ReplyServiceImpl.replyUpdate <<<");
		return replyDao.replyUpdate(rvo);
	}

	@Override
	public int replyDelete(String r_num) {
		// TODO Auto-generated method stub
		logger.info("ReplyServiceImpl.replyDelete >>>");
		logger.info("ReplyServiceImpl.replyDelete <<<");
		return replyDao.replyDelete(r_num);
	}

	@Override
	public ReplyVO replyChaebun() {
		// TODO Auto-generated method stub
		logger.info("ReplyServiceImpl.replyChaebun >>>");
		logger.info("ReplyServiceImpl.replyChaebun <<<");
		return replyDao.replyChaebun();
	}

}
