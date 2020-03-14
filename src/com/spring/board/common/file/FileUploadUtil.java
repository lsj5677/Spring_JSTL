package com.spring.board.common.file;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	static Logger logger = Logger.getLogger(FileUploadUtil.class);
			
	
/*
	   	 ���.isFile() ��ΰ� file���� Ȯ���Ѵ�.
    	 ���.isDirectory() ��ΰ� directory(folder)���� Ȯ���Ѵ�.
		 ���.exists() ��ο� file/directory(folder)�� �����ϴ��� Ȯ���Ѵ�.
 */
	/*file upload*/
	public static String fileUpload(MultipartFile file, HttpServletRequest req) throws IOException{
		logger.info("fileUpload ȣ�� ����");
		String real_name=null;
		
		// MultipartFile class�� getFile() method�� client�� ���ε��� ���ϸ�
		String org_name=file.getOriginalFilename();
		logger.info("(log) org_name >>> " + org_name);
		
		// ���ϸ� �ߺ����� �ʰ� ����
		if(org_name!=null&&(!org_name.equals(""))){
			real_name = "board_"+System.currentTimeMillis() +"_"+ org_name;
			String docRoot = req.getSession().getServletContext().getRealPath("/uploadStorage");
			
			File fileDir = new File(docRoot);
			if(!fileDir.exists()){
				fileDir.mkdir();
			}
			
			File fileAdd = new File(docRoot+"/"+real_name);
			logger.info("(log) ��� >>> " + fileAdd);
			
			file.transferTo(fileAdd);
		}// end if
		return real_name; 
	}
	
	/*file delete*/
	public static void fileDelete(String fileName, HttpServletRequest req)throws IOException{
		logger.info("fileDelete ȣ�� ����");
		boolean result = false;
		String docRoot = req.getSession().getServletContext().getRealPath("/uploadStorage");
		
		File fileDelete = new File(docRoot+"/"+fileName);
		logger.info("(log) fileDelete >>> " + fileDelete);
		if(fileDelete.exists()&&fileDelete.isFile()){
			result = fileDelete.delete();
		}
		
		logger.info(result);
	}
}// end of FileUploadUtil
