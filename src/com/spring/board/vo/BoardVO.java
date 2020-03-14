package com.spring.board.vo;

import org.springframework.web.multipart.MultipartFile;

import com.spring.common.vo.CommonVO;

public class BoardVO extends CommonVO{
	private String b_num;
	private String b_name;
	private String b_pw;
	private String b_title;
	private String b_content;
	private MultipartFile file; // 첨부파일
	private String b_file; //실제 서버에서 저장한 파일명
	private String b_deleteyn;
	private String b_insertdate;
	private String b_updatedate;
	public String getB_num() {
		return b_num;
	}
	public void setB_num(String b_num) {
		this.b_num = b_num;
	}
	public String getB_name() {
		return b_name;
	}
	public void setB_name(String b_name) {
		this.b_name = b_name;
	}
	public String getB_pw() {
		return b_pw;
	}
	public void setB_pw(String b_pw) {
		this.b_pw = b_pw;
	}
	public String getB_title() {
		return b_title;
	}
	public void setB_title(String b_title) {
		this.b_title = b_title;
	}
	public String getB_content() {
		return b_content;
	}
	public void setB_content(String b_content) {
		this.b_content = b_content;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getB_file() {
		return b_file;
	}
	public void setB_file(String b_file) {
		this.b_file = b_file;
	}
	public String getB_deleteyn() {
		return b_deleteyn;
	}
	public void setB_deleteyn(String b_deleteyn) {
		this.b_deleteyn = b_deleteyn;
	}
	public String getB_insertdate() {
		return b_insertdate;
	}
	public void setB_insertdate(String b_insertdate) {
		this.b_insertdate = b_insertdate;
	}
	public String getB_updatedate() {
		return b_updatedate;
	}
	public void setB_updatedate(String b_updatedate) {
		this.b_updatedate = b_updatedate;
	}
	
}// end of BoardVO
