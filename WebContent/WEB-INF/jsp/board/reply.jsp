<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
		<title>Reply</title>
		<link rel="stylesheet" type="text/css" href="/include/css/reply.css" />
		<script type="text/javascript" src="/include/js/common.js"></script>
		<script type="text/javascript"
		src="https://code.jquery.com/jquery-1.11.0.min.js"></script>
		<script>
			$(function(){
				// �⺻ ���� ��� �ҷ�����
				var b_num = "<c:out value='${detail.b_num}' />";
				listAll(b_num);
				
				// ��� ���� ����
				$("#replyInsert").click(function(){
					alert("replyInsert >>>");
					// �ۼ��� �̸� �Է¿��� �˻�
					if(!chkSubmit($("#r_name"),"�̸���"))return;
					else if(!chkSubmit($("#r_content"),"������"))return;
					else{
						var insertUrl = "/replies/replyInsert.lsj"
						
						// �� ������ ���� Post ����� Ajax ����ó��
						$.ajax({
							url : insertUrl, // ���� url
							type: "post",
							headers:{
								"Content-Type" : "application/json",
								"X-HTTP-Method-Override" : "POST"
							},
							dataType: "text",
							data : JSON.stringify({
								b_num:b_num,
								r_name:$("#r_name").val(),
								r_pw:$("#r_pw").val(),
								r_content:$("#r_content").val()
							}),
							error : function(){
								// ����� ������ �߻��Ͽ��� ���
								alert("�ý��� �����Դϴ�. �����ڿ��� �����ϼ���.");
							},
							success : function(resultData){
								if(resultData=="SUCCESS"){
									alert("��� ����� �Ϸ�Ǿ����ϴ�.");
									dataReset();
									listAll(b_num);
								}else{
									alert("error");
								}
							}
						});// end of ajax
					}// end of else
				});// end of #replyInsert.click
				
				// ������ư Ŭ���� ������ ���
				$(document).on("click",".update_form",function(){
					$(".reset_btn").click();
					var conText = $(this).parents("li").children().eq(1).html();
					console.log("conText >>> " + conText);
					
					$(this).parents("li").find("input[type='button']").hide();
					$(this).parents("li").children().eq(0).html();
					
					var conArea = $(this).parents("li").children().eq(1);
					conArea.html("");
					
					var data="<textarea name='content' id='content'>" + conText + "</textArea>";
					data+="<input type='button' class='update_btn' value='�����Ϸ�'>";
					data+="<input type='button' class='reset_btn' value='�������'>";
					
					conArea.html(data);
				});// ������ư Ŭ���� ������ ���
				
				// �ʱ�ȭ ��ư
				$(document).on("click",".reset_btn", function(){
					var conText = $(this).parents("li").find("textarea").html();
					$(this).parents("li").find("input[type='button']").show();
					
					var conArea = $(this).parents("li").children().eq(1);
					conArea.html(conText);
				});// �ʱ�ȭ ��ư
				
				//�� ������ ���� Ajax ����
				$(document).on("click",".update_btn",function(){
					var r_num = $(this).parents("li").attr("data-num");
					var r_content = $("#content").val();
					
					if(!chkSubmit($("#content"),"��� ������")) return;
					else{
						$.ajax({
							url : '/replies/'+r_num+'.lsj',
							type: 'put',
							headers:{
								"Content-Type":"application/json",
								"X-HTTP-Method-Override":"PUT"
							},
							data:JSON.stringify({
								r_content:r_content
							}),
							dataType:'text',
							success:function(result){
								console.log("result >>> " + result);
								if(result=='SUCCESS'){
									alert("�����Ǿ����ϴ�.");
									listAll(b_num);
								}
							}
						}); // end of ajax
					} // end of else
				});// �� ����
				
				// �� ������ ���� Ajax ����
				$(document).on("click",".delete_btn",function(){
					var r_num = $(this).parents("li").attr("data-num");
					console.log("r_num >>> " + r_num);
					
					if(confirm("�����Ͻ� ����� �����Ͻðڽ��ϱ�?")){
						$.ajax({
							type: 'delete',
							url : '/replies/'+r_num+'.lsj',
							headers:{
								"Content-Type":"application/json",
								"X-HTTP-Method-Override":"DELETE"
							}, 
							dataType:'text',
							success:function(result){
								console.log("result >>> " + result);
								if(result=='SUCCESS'){
									alert("�����Ǿ����ϴ�.");
									listAll(b_num);
								}
							}
						}); // end of ajax
					}
				});// �� ����
				
				// ����Ʈ ��û �Լ�
				function listAll(b_num){
					$("#comment_list").html("");
					var url = "/replies/all/" + b_num + ".lsj";
					$.getJSON(url,function(data){
						console.log(data.length);
						
						$(data).each(function(){
							var r_num = this.r_num;
							var r_name = this.r_name;
							var r_content = this.r_content;
							var r_insertdate = this.r_insertdate;
							
							addNewItem(r_num,r_name,r_content,r_insertdate);
						});
					}).fail(function(){
						alert("��� ����� �ҷ����µ� �����Ͽ����ϴ�. ��� �Ŀ� �ٽ� �õ��� �ּ���.");
					});
				}
				
				// ���ο� �� ȭ�鿡 �߰�
				function addNewItem(r_num,r_name,r_content,r_insertdate){
					// ���ο� ���� �߰� �� li�±� ��ü
					var new_li = $("<li>");
					new_li.attr("data-num",r_num);
					new_li.addClass("comment_item");
					
					// �ۼ��� ������ ������ p�±�
					var writer_p = $("<p>");
					writer_p.addClass("writer");
					
					// �ۼ��� ���� �̸�
					var name_span = $("<span>");
					name_span.addClass("name");
					name_span.html(r_name + "��");
					
					// �ۼ��Ͻ�
					var date_span = $("<span>");
					date_span.html("/" + r_insertdate + " ");
					
					// �����ϱ� ��ư
					var up_input = $("<input>");
					up_input.attr({"type":"button","value":"�����ϱ�"});
					up_input.addClass("update_form");
					
					// �����ϱ� ��ư
					var del_input = $("<input>");
					del_input.attr({"type":"button","value":"�����ϱ�"});
					del_input.addClass("delete_btn");
					
					// ����
					var content_p = $("<p>");
					content_p.addClass("con");
					content_p.html(r_content);
					
					// �����ϱ�
					writer_p.append(name_span).append(date_span).append(up_input).append(del_input)
					new_li.append(writer_p).append(content_p);
					$("#comment_list").append(new_li);
				}
				
				// input tag reset method
				function dataReset(){
					$("#r_name").val("");
					$("#r_pw").val("");
					$("#r_content").val("");
				}
			});
		</script>
	</head>
	<body>
		<!-- replyContainer -->
		<div id="replyContainer">
			<h1></h1>
			<!-- comment_write -->
			<div id="comment_write">
				<!-- comment_form -->
				<form id="comment_form">
					<div>
						<label for="r_name">�ۼ���</label>
						<input type="text" name="r_name" id="r_name" />
						<label for="r_pw">��й�ȣ</label>
						<input type="password" name="r_pw" id="r_pw" />
						<input type="button" id="replyInsert" value="�����ϱ�" />
					</div>
					
					<div>
						<label for="r_content">��� ����</label>
						<textarea name="r_content" id="r_content"></textarea>
					</div>
				</form>
				<!-- //comment_form -->
			</div>
			<!-- //comment_write -->
			<!-- comment_list -->
			<ul id="comment_list">
				<!-- ���� ���� ��� �� -->
			</ul>
			<!-- //comment_list -->
		</div>
		<!-- //replyContainer -->
	</body>
</html>