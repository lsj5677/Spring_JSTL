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
		
		// ���Ŀ� ���� �⺻��
		if(bvo.getOrder_by()==null) bvo.setOrder_by("b_num");
		if(bvo.getOrder_sc()==null) bvo.setOrder_sc("DESC");
		
		// ���Ŀ� ���� ������ Ȯ��
		logger.info("order_by >>> " + bvo.getOrder_by());
		logger.info("order_sc >>> " + bvo.getOrder_sc());
		
		//�˻��� ���� ������ Ȯ��
		logger.info("search >>> " + bvo.getSearch());
		logger.info("keyword >>> " + bvo.getKeyword());
		
		// ������ ����
		Paging.setPage(bvo);
		
		// ��ü ���ڵ�� ����
		int total = boardService.boardListCnt(bvo);
		logger.info("total >>> " + total);
		
		// �۹�ȣ �缳��
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
		
		// ���� �ڸ����� ���� 0���̱�
		if (1 == chaebun.length()) {
			chaebun = "000" + chaebun;
		}
		// ���� �ڸ����� �� �� �϶� 00 + ����
		if (2 == chaebun.length()) {
			chaebun = "00" + chaebun;
		}
		// ���� �ڸ����� �� �� �϶� 0 + ����
		if (3 == chaebun.length()) {
			chaebun = "0" + chaebun;
		}
		
		// ��ȣ �տ� B���̱�
		chaebun = "B"+chaebun;
		logger.info("chaebun >>>" + chaebun);
		
		// set���� �ʱ�ȭ �� ä�� ���
		bvo.setB_num(chaebun);
	
		// int ������ boardInsert ���
		String b_file = FileUploadUtil.fileUpload(bvo.getFile(), req);
		bvo.setB_file(b_file);
		
		int result = boardService.boardInsert(bvo);
		
		// ����� 0���� ũ�� ���, �ݴ�� ����
		if(result==1){
			logger.info("��ϿϷ�");
			System.out.println("��ϿϷ�");
			//boardList ��Ƽ� �ҷ�����
//			List<BoardVO> list = boardService.boardList(bvo);
//			mav.addObject("boardList", list);
//			mav.setViewName(PATH+"/boardList");
			
			BoardVO detail = boardService.boardDetail(bvo);
			mav.addObject("detail",detail);
			logger.info("detail >>> " + detail);
			mav.setViewName(PATH+"/boardDetail.jsp?b_num"+bvo.getB_num());
			
		}else{
			logger.info("��Ͻ���");
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
		
		// ���� null�� �ƴϰ� ������ �ƴ� �� ����
		if(detail!=null&&(!detail.equals(""))){
			detail.setB_content(detail.getB_content().toString().replaceAll("\n", "<br>"));
		}
		
		mav.addObject("detail",detail);
		mav.setViewName(PATH+"/boardDetail");
		logger.info("boardController.boardDetail <<<");
		return mav;
	}
	
	/*��й�ȣ Ȯ��*/
	@ResponseBody
	@RequestMapping(value="/pwdConfirm", method=RequestMethod.POST)
	public String pwdConfirm(@ModelAttribute BoardVO bvo){
		logger.info("boardController.pwdConfirm >>>");
		
		// �Է� ������ ���� ���°� ����
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
			logger.info("÷������ ����");
			bvo.setB_file("");
		}
		
		logger.info("b_file >>> " + bvo.getB_file());
		
		if(result==1){
			
//			mav.addObject("boardUpdate", result);
//			mav.setViewName(PATH+"/boardList"); // ���� �� ���		
			BoardVO detail = boardService.boardDetail(bvo);
			mav.addObject("detail",detail);
			logger.info("detail >>> " + detail);
			mav.setViewName(PATH+"/boardDetail.jsp?b_num"+bvo.getB_num()); // ���� �� ����������
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
