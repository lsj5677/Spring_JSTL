package com.spring.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.board.common.file.FileUploadUtil;
import com.spring.board.service.BoardService;
import com.spring.board.vo.BoardVO;
import com.spring.common.page.Paging;
import com.spring.common.util.Util;

@Controller
@RequestMapping(value="/board")
public class BoardController {
	
	private static final String PATH = "board";
	Logger logger = Logger.getLogger(BoardController.class);
	
	@Autowired
	private BoardService boardService;
	
	/*list*/
	@RequestMapping("/boardList")
	public ModelAndView boardList(@ModelAttribute BoardVO bvo){
		logger.info("boardController.boardList >>>");
		
		// 정렬에 대한 기본값
		if(bvo.getOrder_by()==null) bvo.setOrder_by("b_num");
		if(bvo.getOrder_sc()==null) bvo.setOrder_sc("DESC");
		
		// 정렬에 대한 데이터 확인
		logger.info("order_by >>> " + bvo.getOrder_by());
		logger.info("order_sc >>> " + bvo.getOrder_sc());
		
		//검색에 대한 데이터 확인
		logger.info("search >>> " + bvo.getSearch());
		logger.info("keyword >>> " + bvo.getKeyword());
		
		// 페이지 세팅
		Paging.setPage(bvo);
		
		// 전체 레코드수 구현
		int total = boardService.boardListCnt(bvo);
		logger.info("total >>> " + total);
		
		// 글번호 재설정
		int count = total-(Util.nvl(bvo.getPage())-1)*Util.nvl(bvo.getPageSize());
		logger.info("count >>> " + count);
		
		// ModelAndView instance
		ModelAndView mav = new ModelAndView();
		List<BoardVO> list = boardService.boardList(bvo);
		
		mav.addObject("boardList",list);
		mav.addObject("count",count);
		mav.addObject("total",total);
		mav.addObject("data",bvo);
		mav.setViewName(PATH+"/boardList");
		
		logger.info("boardController.boardList <<<");
		return mav;
	}
	
	/*writeform*/
	@RequestMapping("/writeForm")
	public String writeForm(){
		logger.info("boardController.writeForm >>>");
		
		logger.info("boardController.writeForm <<<");
		return "board/writeForm";
	}
	
	/*insert*/
	@RequestMapping(value="/boardInsert", method=RequestMethod.POST)
	public ModelAndView boardInsert(@ModelAttribute BoardVO bvo, HttpServletRequest req) throws IllegalStateException, IOException{
		logger.info("boardController.boardInsert >>>");
		logger.info("filtName >>> " + bvo.getFile().getOriginalFilename());
		logger.info("b_title >>> " + bvo.getB_title());
		
		System.out.println("(log) boardInsert >>>>>>>");
		//ModelAndView instance
		ModelAndView mav = new ModelAndView();
		
		// chaebun
		BoardVO bvoChaebun = boardService.boardChaebun();
		logger.info("bvoChaebun >>> " + bvoChaebun);
		String chaebun = bvoChaebun.getB_num();
		
		// 숫자 자리수에 따라 0붙이기
		if (1 == chaebun.length()) {
			chaebun = "000" + chaebun;
		}
		// 숫자 자릿수가 두 개 일때 00 + 숫자
		if (2 == chaebun.length()) {
			chaebun = "00" + chaebun;
		}
		// 숫자 자릿수가 세 개 일때 0 + 숫자
		if (3 == chaebun.length()) {
			chaebun = "0" + chaebun;
		}
		
		// 번호 앞에 B붙이기
		chaebun = "B"+chaebun;
		logger.info("chaebun >>>" + chaebun);
		
		// set으로 초기화 후 채번 담기
		bvo.setB_num(chaebun);
	
		// int 변수에 boardInsert 담기
		String b_file = FileUploadUtil.fileUpload(bvo.getFile(), req);
		bvo.setB_file(b_file);
		
		int result = boardService.boardInsert(bvo);
		
		// 결과값 0보다 크면 등록, 반대면 실패
		if(result==1){
			logger.info("등록완료");
			System.out.println("등록완료");
			//boardList 담아서 불러오기
//			List<BoardVO> list = boardService.boardList(bvo);
//			mav.addObject("boardList", list);
//			mav.setViewName(PATH+"/boardList");
			
			BoardVO detail = boardService.boardDetail(bvo);
			mav.addObject("detail",detail);
			logger.info("detail >>> " + detail);
			mav.setViewName(PATH+"/boardDetail.jsp?b_num"+bvo.getB_num());
			
		}else{
			logger.info("등록실패");
			mav.setViewName(PATH+"/boardInsert");
		}
		
		logger.info("boardController.boardInsert <<<");
		return mav;
	}
	
	/*detail*/
	@RequestMapping("/boardDetail")
	public ModelAndView boardDetail(@ModelAttribute BoardVO bvo, Model model){
		logger.info("boardController.boardDetail >>>");
		logger.info("b_num >>> " + bvo.getB_num());
		
		ModelAndView mav = new ModelAndView();
		BoardVO detail = boardService.boardDetail(bvo);
		
		// 값이 null이 아니고 공백이 아닐 때 실행
		if(detail!=null&&(!detail.equals(""))){
			detail.setB_content(detail.getB_content().toString().replaceAll("\n", "<br>"));
		}
		
		mav.addObject("detail",detail);
		mav.setViewName(PATH+"/boardDetail");
		logger.info("boardController.boardDetail <<<");
		return mav;
	}
	
	/*비밀번호 확인*/
	@ResponseBody
	@RequestMapping(value="/pwdConfirm", method=RequestMethod.POST)
	public String pwdConfirm(@ModelAttribute BoardVO bvo){
		logger.info("boardController.pwdConfirm >>>");
		
		// 입력 성공에 대한 상태값 저장
		int result = boardService.pwdConfirm(bvo);
		logger.info("result >>>" + result);
		
		logger.info("boardController.pwdConfirm <<<");
		return result+"";
	}
	
	/*updateForm*/
	@RequestMapping(value="/updateForm", method=RequestMethod.POST)
	public ModelAndView updateForm(@ModelAttribute BoardVO bvo){
		logger.info("boardController.updateForm >>>");
		logger.info("b_num >>> "+ bvo.getB_num());
		
		ModelAndView mav = new ModelAndView();
		BoardVO updateData = boardService.boardDetail(bvo);
		
		mav.addObject("updateData", updateData);
		mav.setViewName(PATH+"/updateForm");
		
		logger.info("boardController.updateForm >>>");
		return mav;
	}
	
	/*update*/
	@RequestMapping(value="/boardUpdate", method=RequestMethod.POST)
	public ModelAndView boardUpdate(@ModelAttribute BoardVO bvo, HttpServletRequest req) throws IllegalStateException, IOException{
		logger.info("boardController.boardUpdate >>>");
		
		ModelAndView mav = new ModelAndView();
		int result = boardService.boardUpdate(bvo);
		String b_file = FileUploadUtil.fileUpload(bvo.getFile(), req);
		
		if(!bvo.getFile().isEmpty()){
			FileUploadUtil.fileDelete(bvo.getB_file(), req);
			bvo.setB_file(b_file);
		}else{
			logger.info("첨부파일 없음");
			bvo.setB_file("");
		}
		
		logger.info("b_file >>> " + bvo.getB_file());
		
		if(result==1){
			
//			mav.addObject("boardUpdate", result);
//			mav.setViewName(PATH+"/boardList"); // 수정 후 목록		
			BoardVO detail = boardService.boardDetail(bvo);
			mav.addObject("detail",detail);
			logger.info("detail >>> " + detail);
			mav.setViewName(PATH+"/boardDetail.jsp?b_num"+bvo.getB_num()); // 수정 후 상세페이지로
		}
		System.out.println("(log) mav >>> " + mav);
		logger.info("boardController.boardUpdate <<<");
		return mav;
	}
	
	/*delete*/
	@RequestMapping("/boardDelete")
	public ModelAndView boardDelete(@ModelAttribute BoardVO bvo, HttpServletRequest req)throws IOException{
		logger.info("boardController.boardDelete >>>");
		ModelAndView mav = new ModelAndView();
		
		FileUploadUtil.fileDelete(bvo.getB_file(), req);
		int result = boardService.boardDelete(bvo.getB_num());
		
		if(result==1){
			List<BoardVO> list = boardService.boardList(bvo);
			mav.addObject("boardList", list);
			mav.setViewName(PATH+"/boardList");
		}
		logger.info("boardController.boardDelete <<<");
		return mav;
	}
	
	
}// end of BoardController
