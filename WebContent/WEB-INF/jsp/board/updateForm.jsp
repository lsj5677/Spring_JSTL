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

					 // �������� ���뿡 ���� �� ������ �̰�����
					 // document.getElementById("ir1").value�� �̿��ؼ� ó���Ѵ�.

					 try {
					     elClickedObj.form.submit();
					 } catch(e) {}
					// �Է°� üũ
					if(!chkSubmit($('#b_name'),"�̸���"))return;
					else if (!chkSubmit($('#b_title'),"������"))return;
					else if (!chkSubmit($('#b_content'),"�ۼ��� ������"))return;
					else{
						//�迭 �� ���� ã�Ƽ� �ε��� ����(��Ұ� ���� �� -1 ����)
						//jquery.inArray(ã����, �̻� ����� �迭)
						var ext = $("#file").val().split('.').pop().toLowerCase();
						
						// ÷�������� �̹����� �����ϵ��� Ȯ���ڿ� ���� ��ȿ�� üũ
						if(jQuery.inArray(ext,['gif','png','jpg','jpeg'])==-1){
							alert("gif, png, jpg, jpeg ���ϸ� ���ε� �����մϴ�.");
							return;
						}
						
						$("#f_writeForm").attr("action","/board/boardUpdate.lsj").submit();
					}
				});
				
				// ��� ��ư Ŭ�� �̺�Ʈ
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
			<h3>�ۼ���</h3>
		</div>
		<!-- //boardTit -->
		
		<!-- f_writeForm -->
		<form name="f_writeForm" id="f_writeForm" method="POST" enctype="multipart/form-data">
			<input type="hidden" name="b_num" id="b_num" value=<%=bvo.getB_num()%> />
			<input type="hidden" name="b_file" id="b_file" value=<%=bvo.getB_file() %> />
			<!-- boardWrite -->
			<table id="boardWrite">
				<tr>
					<td>�ۼ���</td>
					<td>
						<input type="text" name="b_name" id="b_name" value=<%=bvo.getB_name()%> />
					</td>
				</tr>
				<tr>
					<td>������</td>
					<td>
						<input type="text" name="b_title" id="b_title" value=<%=bvo.getB_title()%> />
					</td>
				</tr>
				<tr>
					<td>����</td>
					<td>
						<textarea name="b_content" id="b_content" rows="10" cols="100">
						
						<%=bvo.getB_content()%>
						</textarea>
					</td>
				</tr>
				<tr>
					<td>÷������</td>
					<td>
						<input type="file" name="file" id="file" />
					</td>
				</tr>
				<tr>
					<td>��й�ȣ</td>
					<td>
						<input type="password" name="b_pw" id="b_pw"  />
						<label>������ ��й�ȣ�� �Է��� �ּ���</label>
					</td>
				</tr>
			</table>
			<!-- //boardWrite -->
		</form>
		<!-- //f_writeForm -->
		<!-- boardBut -->
		<div id="boardBut">
			<input type="button" value="����" class="but" id="boardUpdate" />
			<input type="button" value="���" class="but" id="boardList" />
		</div>
		<!-- //boardBut -->
		</div>
		<!-- //boardContainer -->
	</body>
</html>