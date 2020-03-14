package com.spring.common.page;

import org.apache.log4j.Logger;

import com.spring.common.util.Util;
import com.spring.common.vo.CommonVO;

public class Paging {
	static Logger logger = Logger.getLogger(Paging.class);
	
	/*
	 * paging setting
	 * @param cvo
	 */
	
	public static void setPage(CommonVO cvo){
		logger.info("Paging.setPage >>>");
		int page = Util.nvl(cvo.getPage(), 1);
		int pageSize = Util.nvl(cvo.getPageSize(),10);
		
		logger.info("page >>>" + page);
		logger.info("pageSize >>>" + pageSize);
		
		if(cvo.getPage()==null) cvo.setPage(page+"");
		if(cvo.getPageSize()==null) cvo.setPageSize(pageSize+"");
		
		int start_row = (page-1)*pageSize+1;
		int end_row = (page-1)*pageSize+pageSize;
		
		logger.info("start_row >>>" + start_row);
		logger.info("end_row >>>" + end_row);
		
		cvo.setStart_row(start_row+"");
		cvo.setEnd_row(end_row+"");
		
		logger.info("Paging.setPage <<<");
		
	}
	
	
}// end of Paging class
