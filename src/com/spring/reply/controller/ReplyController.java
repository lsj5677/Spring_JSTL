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

/*RestController �� spring 4�̻�*/
@Controller
@RequestMapping(value="/replies")
public class ReplyController {
	Logger logger = Logger.getLogger(ReplyController.class);
	
	@Autowired
	private ReplyService replyService;
	
	/*
	 * ��� ��� �����ϱ�
	 * @return List<ReplyVO>
	 * ���� : @PathVariable�� URI�� ��ο��� ���ϴ� �����͸� �����ϴ� �뵵�� ���.
	 *  	ResponseEntity type�� �����ڰ� ���� ��� �����Ϳ� HTTP�� ���� �ڵ带
	 *  	���� ���� �� �� �ִ� Ŭ�����̴�. 404�� 500���� �����ڵ带 �����ϰ� ���� �����Ϳ� �Բ� ������ �� �ֱ� ������ �� �� ������ ��� ����.
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
	 * ��� �۾��� �����ϱ�
	 * @return String
	 * ���� : @RequestBody
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
			chaebun = "R"+chaebun;
			logger.info("chaebun >>>" + chaebun);
			
			// set���� �ʱ�ȭ �� ä�� ���
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
	 * ��� ���� �����ϱ�
	 * @return
	 * ���� : REST��Ŀ��� UPDATE �۾��� PUT, PATCH ����� �̿��ؼ� ó��
	 * 		��ü �����͸� �����ϴ� ��쿡�� PUT�� �̿��ϰ�,
	 * 		�Ϻ��� �����͸� �����ϴ� ��쿡�� PATCH�� �̿�
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
	 * ��� ���� �����ϱ�
	 * @return
	 * ���� : REST ��Ŀ��� DELETE �۾��� DELETE ����� �̿��ؼ� ó��
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
