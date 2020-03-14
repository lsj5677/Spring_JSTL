<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
		<title>boardDetail</title>
		<link rel="stylesheet" type="text/css" href="/include/css/board.css" />
		<script type="text/javascript" src="/include/js/common.js"></script>
		<script type="text/javascript"
		src="https://code.jquery.com/jquery-1.11.0.min.js"></script>
		<script>
			//����, ���� ��ư�� �����ϱ� ���� ����
			var butChk = 0;
			$(function(){
				$("#pwdChk").hide();
				
				// ÷������ �̹��� �����ֱ�
				var file ="<c:out value='${detail.b_file}' />";
				if(file!=""){
					$("#fileImage").attr({
						src : "/uploadStorage/${detail.b_file}",
						width: "550px",
						height: "400px"
					});
				}
				
				// ������ư Ŭ��
				$("#updateForm").click(function(){
					$("#pwdChk").show();
					$("#msg").text("�ۼ��� �Է��� ��й�ȣ�� �Է��� �ּ���.").css("color","#000099");
					butChk = 1;
				});
				
				//������ư Ŭ��
				$("#boardDelete").click(function(){
					$("#pwdChk").show();
					$("#msg").text("�ۼ��� �Է��� ��й�ȣ�� �Է��� �ּ���.").css("color","#000099");
					butChk = 2;
				});
				
				//��й�ȣ üũ
				$("#pwdBut").click(function(){
					pwdConfirm();
				});
				
				// ��Ϲ�ư Ŭ��
				$("#boardList").click(function(){
					location.href="/board/boardList.lsj";
				});
			});
			
			// ��й�ȣ üũ �Լ�
			function pwdConfirm(){
				if(!chkSubmit($('#b_pw'),"��й�ȣ��")) return;
				else{
					$.ajax({
						url : "/board/pwdConfirm.lsj", //���� url
						type : "POST", // ���� �� method
						data : $("#f_pwd").serialize(), // ����ü ������ ����
						error : function(){
							alert("�ý��� �����Դϴ�. �����ڿ��� �����ϼ���");
						},
						success : function(resultData){
							var goUrl ="";
							if(resultData==0){
								$("#msg").text("�ۼ��� �Է��� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.").css("color","#cf0000");
								$("#b_pw").select();
							}else if(resultData==1){
								$("#msg").text("");
								if(butChk==1){
									goUrl="/board/updateForm.lsj";
								}else if(butChk==2){
									goUrl="/board/boardDelete.lsj";
								}
								$("#f_data").attr("action",goUrl).submit();
							}
						}
					});
				}
			}
		</script>
	</head>
	<body>
	<div id="boardContainer">
		<!-- boardTit -->
		<div id="boardTit">
			<h3>�ۻ�</h3>
		</div>
		<!-- //boardTit -->
		
		<!-- f_data -->
		<form name="f_data" id="f_data" method="POST">
			<input type="hidden" name="b_num" id="b_num" value="${detail.b_num}" />
			<input type="hidden" name="b_file" id="b_file" value="${detail.b_file}" />
		</form>
		<!-- //f_data -->
		
		<!-- boardPwdBut -->
		<table id="boardPwdBut">
			<tr>
				<!-- btd1 -->
				<td id="btd1">
					<!-- pwdChk -->
					<div id="pwdChk">
						<!-- f_pwd -->
						<form name="f_pwd" id="f_pwd">
							<input type="hidden" name="b_num" id="b_num" value="${detail.b_num}" />
							<label for="b_pw" id="l_pw">��й�ȣ</label>
							<input type="password" name="b_pw" id="b_pw" />
							<input type="button" name="pwdBut" id="pwdBut" value="Ȯ��" />
							<span id="msg"></span>
						</form>
						<!-- //f_pwd -->
					</div>
					<!-- //pwdChk -->
				</td>
				
				<!-- btd2 -->
				<td id="btd2">
					<input type="button" value="����" id="updateForm" />
					<input type="button" value="����" id="boardDelete" />
					<input type="button" value="���" id="boardList" />
				</td>
				<!-- //btd2 -->
				<!-- //btd1 -->
			</tr>
		</table>
		<!-- //boardPwdBut -->
		
		<!-- boardDetail -->
		<div id="boardDetail">
			<table>
				<colgroup>
					<col width="8%" />
					<col width="42%" />
					<col width="8%" />
					<col width="42%" />
				</colgroup>
				<tbody>
					<tr>
						<td class="ac">�ۼ���</td>
						<td>${detail.b_name}</td>
						<td class="ac">�ۼ���</td>
						<td>${detail.b_insertdate}</td>
					</tr>
					<tr>
						<td class="ac">����</td>
						<td colspan="3">${detail.b_title}</td>
					</tr>
					<tr class="ctr">
						<td class="ac">����</td>
						<td colspan="3">${detail.b_content}</td>
					</tr>
					<tr class="ctr">
						<td class="ac">÷������ �̹���</td>
						<td colspan="3"><img id="fileImage" /></td>
					</tr>
				</tbody>
			</table>
		</div>
		<!-- //boardDetail -->
		
		<!-- reply -->
		<jsp:include page="reply.jsp"></jsp:include>
		<!-- //reply -->
	</div>
	<!-- //boardContainer -->
</body>
</html>