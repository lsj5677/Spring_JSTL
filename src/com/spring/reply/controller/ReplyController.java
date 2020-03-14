package com.spring.reply.controller;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.reply.service.ReplyService;
import com.spring.reply.vo.ReplyVO;

/*RestController 는 spring 4이상*/
@Controller
@RequestMapping(value="/replies")
public class ReplyController {
	Logger logger = Logger.getLogger(ReplyController.class);
	
	@Autowired
	private ReplyService replyService;
	
	/*
	 * 댓글 목록 구현하기
	 * @return List<ReplyVO>
	 * 참고 : @PathVariable는 URI의 경로에서 원하는 데이터를 추출하는 용도로 사용.
	 *  	ResponseEntity type은 개발자가 직접 결과 데이터와 HTTP의 상태 코드를
	 *  	직접 제어 할 수 있는 클래스이다. 404나 500같은 상태코드를 전송하고 싶은 데이터와 함께 전송할 수 있기 때문에 좀 더 세밀한 제어가 가능.
	 */
	@ResponseBody
	@RequestMapping(value="/all/{b_num}.lsj")
	public ResponseEntity<List<ReplyVO>> list(@PathVariable("b_num") String b_num){
		logger.info("ReplyController.list >>>");
		
		ResponseEntity<List<ReplyVO>> entity = null;
				
		try{
			logger.info("ReplyController.list try >>>");
			entity = new ResponseEntity<List<ReplyVO>>(replyService.replyList(b_num),HttpStatus.OK);
		}catch(Exception e){
			logger.info("ReplyController.list catch >>>");
			entity = new ResponseEntity<List<ReplyVO>>(HttpStatus.BAD_REQUEST);
		}
		logger.info("ReplyController.list <<<");
		return entity;
	}
	
	/*
	 * 댓글 글쓰기 구현하기
	 * @return String
	 * 참고 : @RequestBody
	 */
	@ResponseBody
	@RequestMapping(value="/replyInsert")
	public ResponseEntity<String> replyInsert(@RequestBody ReplyVO rvo){
		logger.info("ReplyController.replyInsert >>>");
		
		ResponseEntity<String> entity = null;
		
		try{
			// chaebun
			ReplyVO rvoChaebun = replyService.replyChaebun();
			logger.info("bvoChaebun >>> " + rvoChaebun);
			String chaebun = rvoChaebun.getR_num();
			
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
			chaebun = "R"+chaebun;
			logger.info("chaebun >>>" + chaebun);
			
			// set으로 초기화 후 채번 담기
			rvo.setR_num(chaebun);
			
			int result = replyService.replyInsert(rvo);
			if(result==1){
				entity = new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
			}
		}catch(Exception e){
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
		logger.info("ReplyController.replyInsert <<<");
		return entity;
	}
	
	/*
	 * 댓글 수정 구현하기
	 * @return
	 * 참고 : REST방식에서 UPDATE 작업은 PUT, PATCH 방식을 이용해서 처리
	 * 		전체 데이터를 수정하는 경우에는 PUT을 이용하고,
	 * 		일부의 데이터를 수정하는 경우에는 PATCH를 이용
	 */
	@ResponseBody
	@RequestMapping(value="/{r_num}.lsj", method={RequestMethod.PUT, RequestMethod.PATCH})
	public ResponseEntity<String> replyUpdate(@PathVariable("r_num") String r_num, @RequestBody ReplyVO rvo){
		logger.info("ReplyController.replyUpdate >>>");
		
		ResponseEntity<String> entity = null;
		
		try{
			rvo.setR_num(r_num);
			replyService.replyUpdate(rvo);
			entity = new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
		logger.info("ReplyController.replyUpdate <<<");
		
		return entity;
	}
	
	/*
	 * 댓글 삭제 구현하기
	 * @return
	 * 참고 : REST 방식에서 DELETE 작업은 DELETE 방식을 이용해서 처리
	 */
	@ResponseBody
	@RequestMapping(value="/{r_num}.lsj", method=RequestMethod.DELETE)
	public ResponseEntity<String> replyDelete(@PathVariable("r_num") String r_num){
		logger.info("ReplyController.replyDelete >>>");
		
		ResponseEntity<String> entity = null;
		try{
			replyService.replyDelete(r_num);
			entity = new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
		logger.info("ReplyController.replyDelete >>>");
		return entity;
	}
}
