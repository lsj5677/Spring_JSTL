package com.spring.common.page;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;


public class PagingTag extends TagSupport {
	
	Logger logger = Logger.getLogger(PagingTag.class);
	private static final long serialVersionUID = 1L;
	
	/*
	 * @param page : 현재 페이지 번호
	 * @param total : 전체 조회된 Row 수
	 * @param list_size : 페이지에 보여주는 레코드수
	 * @param page_size : 페이지 네비게이터에 표시되는 페이지 버튼의 갯수 
	 */
	
	private int page = 1;
	private int total = 1;
	private int list_size = 10;
	private int page_size =10;
	
	@Override
	public int doStartTag() throws JspException{
		try{
			pageContext.getOut().println(getPaging());
		}catch(IOException e){
			e.printStackTrace();
		}
		return super.doStartTag();
	}


	public void setPage(int page) {
		this.page = page;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setList_size(int list_size) {
		this.list_size = list_size;
	}

	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}
	
	public String getPaging(){
		String ret="";
		if(page<1) {
			page = 1;
		}
		if(total<1) {
			return "";
		}
		
		logger.info("page >>> " + page); //1
		logger.info("total >>> " + total); //19
		logger.info("page_size >>> " + page_size); //10
		
		// page가 1p고 page_size가 2면 currentFirst는 1
		int currentFirst = ((page-1)/page_size)*page_size + 1;
		
		// currentlast = 2
		int currentlast = ((page-1)/page_size)*page_size + page_size;
		
		// nextFirst = 3
		int nextFirst = (((page-1)/page_size)+1)*page_size+1;
		
		//prevLast = 0
		int prevLast = (((page-1)/page_size)-1)*page_size;
		
		int lastPage =1;
		lastPage = total/list_size;
		
		logger.info("list_size >>> " + list_size);
		logger.info("currentFirst >>> " + currentFirst);
		logger.info("currentlast >>> " + currentlast);
		logger.info("nextFirst >>> " + nextFirst);
		logger.info("prevLast >>> " + prevLast);
		logger.info("lastPage >>> " + lastPage);
		
		/*
		 * lastPage(총 페이지 수) 는 total이 11이고  list_size가 10이면 1을 가진다.
		 * 그래서 총 페이지 수가 나누어 떨어지지 않으면 나머지 레코드를 출력할 페이지가 필요하다는 의미
		 */
		if(total%list_size != 0)lastPage=lastPage+1;
		
		/*
		 * currentLast가 lastPage(총 페이지 수)보다 크면 마지막 페이지로 수정
		 */		
		currentlast = (currentlast>lastPage)?lastPage:currentlast;
		
		ret += "<div class='paginate'>";
		
		if(page>1){
			ret += "<a href=\"javascript:goPage('1')\"><span><img src='../images/common/btn_paginate_first.png' alt='처음' /></span></a>";
		}else{
			ret += "<span><img src='../images/common/btn_paginate_first.png' alt='처음' /></span>";
		}
		
		logger.info("prevLast >>> " + prevLast);
		
		if(prevLast>0){
			ret += "<a href=\"javascript:goPage('"+prevLast+"');\"><span><img src='../images/common/btn_paginate_prev.png' alt='이전' /></span></a>";
		}else{
			ret += "<span><img src='../images/common/btn_paginate_prev.png' alt='이전' /></span>";
		}
		
		for(int j=currentFirst; j<currentFirst+page_size && j<=lastPage; j++){
			if(j<=currentlast){
				if(j == page){
					ret+="<a href='#' class='on textAn'>"+j+"</a>";
				}else{
					ret+="<a href=\"javascript:goPage('"+j+"');\" class='textAn'>"+j+"</a>";
				}
			}
		}
		
		logger.info("nextFirst >>> " + nextFirst);
		logger.info("lastPage >>> " + lastPage);
		 
		if(nextFirst <= lastPage){
			ret += "<a href=\"javascript:goPage('"+nextFirst+"');\"><span><img src='../images/common/btn_paginate_next.png' alt='다음' /></span></a>"; 
		}else{
			ret += "<span><img src='../images/common/btn_paginate_next.png' alt='다음' /></span>";
		}
		
		if(page < lastPage){
			ret += "<a href=\"javascript:goPage('"+lastPage+"');\"><span><img src='../images/common/btn_paginate_last.png' alt='마지막' /></span></a>"; 
		}else{
			ret += "<span><img src='../images/common/btn_paginate_last.png' alt='마지막' /></span>";
		}
		
		ret+="</div>";
		
		return ret;
	}

	
}// end of PagingTag class
