package com.spring.board.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.board.dao.BoardDao;
import com.spring.board.vo.BoardVO;

@Service
@Transactional
public class BoardServiceImpl implements BoardService {

	Logger logger = Logger.getLogger(BoardServiceImpl.class);
	
	@Autowired
	private BoardDao boardDao;
	
	/*list*/
	@Override
	public List<BoardVO> boardList(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("BoardServiceImpl.boardList >>>");
		logger.info("BoardServiceImpl.boardList <<<");
		return boardDao.boardList(bvo);
	}

	/*insert*/
	@Override
	public int boardInsert(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("BoardServiceImpl.boardInsert >>>");
		logger.info("BoardServiceImpl.boardInsert <<<");
		return boardDao.boardInsert(bvo);
	}

	@Override
	public BoardVO boardChaebun() {
		// TODO Auto-generated method stub
		logger.info("BoardServiceImpl.boardChaebun >>>");
		logger.info("BoardServiceImpl.boardChaebun <<<");
		return boardDao.boardChaebun();
	}

	@Override
	public BoardVO boardDetail(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("BoardServiceImpl.boardDetail >>>");
		logger.info("BoardServiceImpl.boardDetail <<<");
		
		return boardDao.boardDetail(bvo);
	}

	@Override
	public int pwdConfirm(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("BoardServiceImpl.pwdConfirm >>>");
		logger.info("BoardServiceImpl.pwdConfirm <<<");
		return boardDao.pwdConfirm(bvo);
	}

	@Override
	public int boardUpdate(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("BoardServiceImpl.boardUpdate >>>");
		logger.info("BoardServiceImpl.boardUpdate <<<");
		return boardDao.boardUpdate(bvo);
	}

	@Override
	public int boardDelete(String b_num) {
		// TODO Auto-generated method stub
		logger.info("BoardServiceImpl.boardDelete >>>");
		logger.info("BoardServiceImpl.boardDelete <<<");
		return boardDao.boardDelete(b_num);
	}

	@Override
	public int boardListCnt(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("BoardServiceImpl.boardListCnt >>>");
		logger.info("BoardServiceImpl.boardListCnt <<<");
		return boardDao.boardListCnt(bvo);
	}

}
