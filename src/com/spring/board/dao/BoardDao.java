package com.spring.board.dao;

import java.util.List;

import com.spring.board.vo.BoardVO;

public interface BoardDao {
	public List<BoardVO> boardList(BoardVO bvo);
	public int boardInsert(BoardVO bvo);
	public BoardVO boardChaebun();
	public BoardVO boardDetail(BoardVO bvo);
	public int pwdConfirm(BoardVO bvo);
	public int boardUpdate(BoardVO bvo);
	public int boardDelete(String b_num);
	public int boardListCnt(BoardVO bvo);
}// end of BoardDAO
