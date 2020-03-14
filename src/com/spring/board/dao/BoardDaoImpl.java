package com.spring.board.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.board.service.BoardServiceImpl;
import com.spring.board.vo.BoardVO;

@Repository
public class BoardDaoImpl implements BoardDao {
	
	Logger logger = Logger.getLogger(BoardDaoImpl.class);
	
	@Autowired
	private SqlSession session;

	@Override
	public List<BoardVO> boardList(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("BoardDAOImpl.boardList >>>");
		logger.info("BoardDAOImpl.boardList <<<");
		return session.selectList("boardList");
	}

	@Override
	public int boardInsert(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("BoardDAOImpl.boardInsert >>>");
		logger.info("BoardDAOImpl.boardInsert <<<");
		return session.insert("boardInsert");
	}

	@Override
	public BoardVO boardChaebun() {
		// TODO Auto-generated method stub
		logger.info("BoardDAOImpl.boardChaebun >>>");
		logger.info("BoardDAOImpl.boardChaebun <<<");
		return session.selectOne("boardChaebun");
	}

	@Override
	public BoardVO boardDetail(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("BoardDAOImpl.boardDetail >>>");
		logger.info("BoardDAOImpl.boardDetail <<<");
		return (BoardVO)session.selectOne("boardDetail");
	}

	@Override
	public int pwdConfirm(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("BoardDAOImpl.pwdConfirm >>>");
		logger.info("BoardDAOImpl.pwdConfirm <<<");
		return (int)session.selectOne("pwdConfirm");
	}

	@Override
	public int boardUpdate(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("BoardDAOImpl.boardUpdate >>>");
		logger.info("BoardDAOImpl.boardUpdate <<<");
		return session.update("boardUpdate");
	}

	@Override
	public int boardDelete(String b_num) {
		// TODO Auto-generated method stub
		logger.info("BoardDAOImpl.boardDelete >>>");
		logger.info("BoardDAOImpl.boardDelete <<<");
		return session.delete("boardDelete",b_num);
	}

	@Override
	public int boardListCnt(BoardVO bvo) {
		// TODO Auto-generated method stub
		logger.info("BoardDAOImpl.boardListCnt >>>");
		logger.info("BoardDAOImpl.boardListCnt <<<");
		return (int)session.selectOne("boardListCnt");
	}

}
