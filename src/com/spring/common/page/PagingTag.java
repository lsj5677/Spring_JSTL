package com.spring.common.page;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;


public class PagingTag extends TagSupport {
	
	Logger logger = Logger.getLogger(PagingTag.class);
	private static final long serialVersionUID = 1L;
	
	/*
	 * @param page : ���� ������ ��ȣ
	 * @param total : ��ü ��ȸ�� Row ��
	 * @param list_size : �������� �����ִ� ���ڵ��
	 * @param page_size : ������ �׺�����Ϳ� ǥ�õǴ� ������ ��ư�� ���� 
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
		
		// page�� 1p�� page_size�� 2�� currentFirst�� 1
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
		 * lastPage(�� ������ ��) �� total�� 11�̰�  list_size�� 10�̸� 1�� ������.
		 * �׷��� �� ������ ���� ������ �������� ������ ������ ���ڵ带 ����� �������� �ʿ��ϴٴ� �ǹ�
		 */
		if(total%list_size != 0)lastPage=lastPage+1;
		
		/*
		 * currentLast�� lastPage(�� ������ ��)���� ũ�� ������ �������� ����
		 */		
		currentlast = (currentlast>lastPage)?lastPage:currentlast;
		
		ret += "<div class='paginate'>";
		
		if(page>1){
			ret += "<a href=\"javascript:goPage('1')\"><span><img src='../images/common/btn_paginate_first.png' alt='ó��' /></span></a>";
		}else{
			ret += "<span><img src='../images/common/btn_paginate_first.png' alt='ó��' /></span>";
		}
		
		logger.info("prevLast >>> " + prevLast);
		
		if(prevLast>0){
			ret += "<a href=\"javascript:goPage('"+prevLast+"');\"><span><img src='../images/common/btn_paginate_prev.png' alt='����' /></span></a>";
		}else{
			ret += "<span><img src='../images/common/btn_paginate_prev.png' alt='����' /></span>";
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
			ret += "<a href=\"javascript:goPage('"+nextFirst+"');\"><span><img src='../images/common/btn_paginate_next.png' alt='����' /></span></a>"; 
		}else{
			ret += "<span><img src='../images/common/btn_paginate_next.png' alt='����' /></span>";
		}
		
		if(page < lastPage){
			ret += "<a href=\"javascript:goPage('"+lastPage+"');\"><span><img src='../images/common/btn_paginate_last.png' alt='������' /></span></a>"; 
		}else{
			ret += "<span><img src='../images/common/btn_paginate_last.png' alt='������' /></span>";
		}
		
		ret+="</div>";
		
		return ret;
	}

	
}// end of PagingTag class
