package com.spring.board.common.file;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	static Logger logger = Logger.getLogger(FileUploadUtil.class);
			
	
/*
	   	 경로.isFile() 경로가 file인지 확인한다.
    	 경로.isDirectory() 경로가 directory(folder)인지 확인한다.
		 경로.exists() 경로에 file/directory(folder)가 존재하는지 확인한다.
 */
	/*file upload*/
	public static String fileUpload(MultipartFile file, HttpServletRequest req) throws IOException{
		logger.info("fileUpload 호출 성공");
		String real_name=null;
		
		// MultipartFile class의 getFile() method로 client가 업로드한 파일명
		String org_name=file.getOriginalFilename();
		logger.info("(log) org_name >>> " + org_name);
		
		// 파일명 중복되지 않게 변경
		if(org_name!=null&&(!org_name.equals(""))){
			real_name = "board_"+System.currentTimeMillis() +"_"+ org_name;
			String docRoot = req.getSession().getServletContext().getRealPath("/uploadStorage");
			
			File fileDir = new File(docRoot);
			if(!fileDir.exists()){
				fileDir.mkdir();
			}
			
			File fileAdd = new File(docRoot+"/"+real_name);
			logger.info("(log) 경로 >>> " + fileAdd);
			
			file.transferTo(fileAdd);
		}// end if
		return real_name; 
	}
	
	/*file delete*/
	public static void fileDelete(String fileName, HttpServletRequest req)throws IOException{
		logger.info("fileDelete 호출 성공");
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
