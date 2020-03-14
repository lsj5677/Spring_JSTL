package com.spring.common.util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.spring.board.service.BoardService;

public class Util {
	static Logger logger = Logger.getLogger(Util.class);
	

	
	public static int nvl(String text){
		logger.info("Util.nvl(String text) >>>");
		logger.info("Util.nvl(String text) <<<");
		return nvl(text,0);
	}
	/*
	 nvl() method�� ���ڿ��� ���ڷ� ��ȯ�ϴ� method
	 @param (���ڷ� ��ȯ�� ���ڿ�, �ʱⰪ���� ����� ��(��ü��))
	 !! ���� ó���� üũ���ܿ� ��üũ���ܷ� ����.
	 	üũ���ܴ� ���������, ��Ʈ��ũ �����, �����ͺ��̽� �����
	 	�������� ��üũ ���ܷ� �ν�
	 @return int
	 */
	
	public static int nvl(String text, int def){
		logger.info("Util.nvl(String text, int def) >>>");
		logger.info("Util.nvl(String text, int def) <<<");
		int ret = def;
		try{
			ret = Integer.parseInt(text);
		}catch(Exception e){
			ret=def;
		}
		return ret;
	}
	
	public static String nvl(Object text, String def){
		logger.info("Util.nvl(Object text, String def) >>>");
		logger.info("Util.nvl(Object text, String def) <<<");
		if(text==null || "".equals(text.toString().trim())){
			logger.info("Util class if start >>>");
			logger.info("Util class if end <<<");
			return def;
		}else{
			logger.info("Util class else start >>>");
			logger.info("Util class else end <<<");
			return text.toString();
		}
	}
}// end of Util class
