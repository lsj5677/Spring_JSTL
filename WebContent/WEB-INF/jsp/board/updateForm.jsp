<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="com.spring.board.dao.BoardDao"%>
<%@ page import="com.spring.board.dao.BoardDaoImpl"%>
<%@ page import="com.spring.board.vo.BoardVO"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%
BoardVO bvo = (BoardVO)request.getAttribute("updateData");	
System.out.println("bvo >>> : " + bvo);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
		<title>boardUpdate</title>
		<link rel="stylesheet" type="text/css" href="/include/css/board.css" />
		<script type="text/javascript" src="/include/js/common.js"></script>
		<script type="text/javascript"
		src="https://code.jquery.com/jquery-1.11.0.min.js"></script>
		
		<script type="text/javascript" src="/se2/js/service/HuskyEZCreator.js" charset="utf-8"></script>
		<script type="text/javascript" src="/se2/js/smarteditor2.js" charset="utf-8"></script>
		<script>
			$(function(){
				$("#boardUpdate").click(function(){
					oEditors.getById["b_content"].exec("UPDATE_CONTENTS_FIELD", []);

					 // 에디터의 내용에 대한 값 검증은 이곳에서
					 // document.getElementById("ir1").value를 이용해서 처리한다.

					 try {
					     elClickedObj.form.submit();
					 } catch(e) {}
					// 입력값 체크
					if(!chkSubmit($('#b_name'),"이름을"))return;
					else if (!chkSubmit($('#b_title'),"제목을"))return;
					else if (!chkSubmit($('#b_content'),"작성할 내용을"))return;
					else{
						//배열 내 값을 찾아서 인덱스 리턴(요소가 없을 시 -1 리턴)
						//jquery.inArray(찾을값, 겁색 대상의 배열)
						var ext = $("#file").val().split('.').pop().toLowerCase();
						
						// 첨부파일은 이미지만 가능하도록 확장자에 대한 유효성 체크
						if(jQuery.inArray(ext,['gif','png','jpg','jpeg'])==-1){
							alert("gif, png, jpg, jpeg 파일만 업로드 가능합니다.");
							return;
						}
						
						$("#f_writeForm").attr("action","/board/boardUpdate.lsj").submit();
					}
				});
				
				// 목록 버튼 클릭 이벤트
				$("#boardList").click(function(){
					location.href="/board/boardList.lsj";
				});
				

				var oEditors = [];
				nhn.husky.EZCreator.createInIFrame({
				 oAppRef: oEditors,
				 elPlaceHolder: "b_content",
				 sSkinURI: "/se2/SmartEditor2Skin.html",
				 fCreator: "createSEditor2"
				});
			});
		</script>
	</head>
	<body>
	<!-- boardContainer -->
		<div id="boardContainer">
		<!-- boardTit -->
		<div id="boardTit">
			<h3>글수정</h3>
		</div>
		<!-- //boardTit -->
		
		<!-- f_writeForm -->
		<form name="f_writeForm" id="f_writeForm" method="POST" enctype="multipart/form-data">
			<input type="hidden" name="b_num" id="b_num" value=<%=bvo.getB_num()%> />
			<input type="hidden" name="b_file" id="b_file" value=<%=bvo.getB_file() %> />
			<!-- boardWrite -->
			<table id="boardWrite">
				<tr>
					<td>작성자</td>
					<td>
						<input type="text" name="b_name" id="b_name" value=<%=bvo.getB_name()%> />
					</td>
				</tr>
				<tr>
					<td>글제목</td>
					<td>
						<input type="text" name="b_title" id="b_title" value=<%=bvo.getB_title()%> />
					</td>
				</tr>
				<tr>
					<td>내용</td>
					<td>
						<textarea name="b_content" id="b_content" rows="10" cols="100">
						
						<%=bvo.getB_content()%>
						</textarea>
					</td>
				</tr>
				<tr>
					<td>첨부파일</td>
					<td>
						<input type="file" name="file" id="file" />
					</td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td>
						<input type="password" name="b_pw" id="b_pw"  />
						<label>수정할 비밀번호를 입력해 주세요</label>
					</td>
				</tr>
			</table>
			<!-- //boardWrite -->
		</form>
		<!-- //f_writeForm -->
		<!-- boardBut -->
		<div id="boardBut">
			<input type="button" value="수정" class="but" id="boardUpdate" />
			<input type="button" value="목록" class="but" id="boardList" />
		</div>
		<!-- //boardBut -->
		</div>
		<!-- //boardContainer -->
	</body>
</html>