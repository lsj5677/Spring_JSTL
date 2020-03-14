<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>writeForm</title>
		<link rel="stylesheet" type="text/css" href="/include/css/board.css" />
		<script type="text/javascript" src="/include/js/common.js"></script>
		<script type="text/javascript"
		src="https://code.jquery.com/jquery-1.11.0.min.js"></script>
		<script type="text/javascript" src="/se2/js/service/HuskyEZCreator.js" charset="utf-8"></script>
		<script type="text/javascript" src="/se2/js/smarteditor2.js" charset="utf-8"></script>
		<script>
			$(function(){
				// 저장 버튼 클릭 이벤트
				$("#boardInsert").click(function(){
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
					else if (!chkSubmit($('#file'),"첨부파일을"))return;
					else if (!chkSubmit($('#b_pw'),"비밀번호를"))return;
					else{
						//배열 내 값을 찾아서 인덱스 리턴(요소가 없을 시 -1 리턴)
						//jquery.inArray(찾을값, 겁색 대상의 배열)
						var ext = $("#file").val().split('.').pop().toLowerCase();
						
						// 첨부파일은 이미지만 가능하도록 확장자에 대한 유효성 체크
						if(jQuery.inArray(ext,['gif','png','jpg','jpeg'])==-1){
							alert("gif, png, jpg, jpeg 파일만 업로드 가능합니다.");
							return;
						}
						
						$("#f_writeForm").attr("action","/board/boardInsert.lsj").submit();
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
		<div id="boardTit">
			<h3>글쓰기</h3>
		</div>
		
		<form id="f_writeForm" name="f_writeForm" enctype="multipart/form-data" method="POST">
			<table id="boardWrite">
				<tr>
					<td>작성자</td>
					<td>
						<input type="text" name="b_name" id="b_name" />
					</td>
				</tr>
				<tr>
					<td>글제목</td>
					<td>
						<input type="text" name="b_title" id="b_title" />
					</td>
				</tr>
				<tr>
					<td>내용</td>
					<td height="200">
						<textarea name="b_content" id="b_content" rows="10" cols="100">
						
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
						<input type="password" name="b_pw" id="b_pw" />
					</td>
				</tr>
			</table>
		</form> 
		
		<div id="boardBut">
			<input type="button" value="저장" class="but" id="boardInsert" />
			<input type="button" value="목록" class="but" id="boardList" />
		</div>
		</div>
		<!-- //boardContainer -->
	</body>
</html>