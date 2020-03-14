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
				// 기본 덧글 목록 불러오기
				var b_num = "<c:out value='${detail.b_num}' />";
				listAll(b_num);
				
				// 댓글 내용 저장
				$("#replyInsert").click(function(){
					alert("replyInsert >>>");
					// 작성자 이름 입력여부 검사
					if(!chkSubmit($("#r_name"),"이름을"))return;
					else if(!chkSubmit($("#r_content"),"내용을"))return;
					else{
						var insertUrl = "/replies/replyInsert.lsj"
						
						// 글 저장을 위한 Post 방식의 Ajax 연동처리
						$.ajax({
							url : insertUrl, // 전송 url
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
								// 실행시 오류가 발생하였을 경우
								alert("시스템 오류입니다. 관리자에게 문의하세요.");
							},
							success : function(resultData){
								if(resultData=="SUCCESS"){
									alert("댓글 등록이 완료되었습니다.");
									dataReset();
									listAll(b_num);
								}else{
									alert("error");
								}
							}
						});// end of ajax
					}// end of else
				});// end of #replyInsert.click
				
				// 수정버튼 클릭시 수정폼 출력
				$(document).on("click",".update_form",function(){
					$(".reset_btn").click();
					var conText = $(this).parents("li").children().eq(1).html();
					console.log("conText >>> " + conText);
					
					$(this).parents("li").find("input[type='button']").hide();
					$(this).parents("li").children().eq(0).html();
					
					var conArea = $(this).parents("li").children().eq(1);
					conArea.html("");
					
					var data="<textarea name='content' id='content'>" + conText + "</textArea>";
					data+="<input type='button' class='update_btn' value='수정완료'>";
					data+="<input type='button' class='reset_btn' value='수정취소'>";
					
					conArea.html(data);
				});// 수정버튼 클릭시 수정폼 출력
				
				// 초기화 버튼
				$(document).on("click",".reset_btn", function(){
					var conText = $(this).parents("li").find("textarea").html();
					$(this).parents("li").find("input[type='button']").show();
					
					var conArea = $(this).parents("li").children().eq(1);
					conArea.html(conText);
				});// 초기화 버튼
				
				//글 수정을 위한 Ajax 연동
				$(document).on("click",".update_btn",function(){
					var r_num = $(this).parents("li").attr("data-num");
					var r_content = $("#content").val();
					
					if(!chkSubmit($("#content"),"댓글 내용을")) return;
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
									alert("수정되었습니다.");
									listAll(b_num);
								}
							}
						}); // end of ajax
					} // end of else
				});// 글 수정
				
				// 글 삭제를 위한 Ajax 연동
				$(document).on("click",".delete_btn",function(){
					var r_num = $(this).parents("li").attr("data-num");
					console.log("r_num >>> " + r_num);
					
					if(confirm("선택하신 댓글을 삭제하시겠습니까?")){
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
									alert("삭제되었습니다.");
									listAll(b_num);
								}
							}
						}); // end of ajax
					}
				});// 글 삭제
				
				// 리스트 요청 함수
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
						alert("댓글 목록을 불러오는데 실패하였습니다. 잠시 후에 다시 시도해 주세요.");
					});
				}
				
				// 새로운 글 화면에 추가
				function addNewItem(r_num,r_name,r_content,r_insertdate){
					// 새로운 글이 추가 될 li태그 객체
					var new_li = $("<li>");
					new_li.attr("data-num",r_num);
					new_li.addClass("comment_item");
					
					// 작성자 정보가 지정될 p태그
					var writer_p = $("<p>");
					writer_p.addClass("writer");
					
					// 작성자 정보 이름
					var name_span = $("<span>");
					name_span.addClass("name");
					name_span.html(r_name + "님");
					
					// 작성일시
					var date_span = $("<span>");
					date_span.html("/" + r_insertdate + " ");
					
					// 수정하기 버튼
					var up_input = $("<input>");
					up_input.attr({"type":"button","value":"수정하기"});
					up_input.addClass("update_form");
					
					// 삭제하기 버튼
					var del_input = $("<input>");
					del_input.attr({"type":"button","value":"삭제하기"});
					del_input.addClass("delete_btn");
					
					// 내용
					var content_p = $("<p>");
					content_p.addClass("con");
					content_p.html(r_content);
					
					// 조립하기
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
						<label for="r_name">작성자</label>
						<input type="text" name="r_name" id="r_name" />
						<label for="r_pw">비밀번호</label>
						<input type="password" name="r_pw" id="r_pw" />
						<input type="button" id="replyInsert" value="저장하기" />
					</div>
					
					<div>
						<label for="r_content">댓글 내용</label>
						<textarea name="r_content" id="r_content"></textarea>
					</div>
				</form>
				<!-- //comment_form -->
			</div>
			<!-- //comment_write -->
			<!-- comment_list -->
			<ul id="comment_list">
				<!-- 동작 생성 요소 들어감 -->
			</ul>
			<!-- //comment_list -->
		</div>
		<!-- //replyContainer -->
	</body>
</html>