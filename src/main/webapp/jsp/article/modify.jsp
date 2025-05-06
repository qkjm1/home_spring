<%@page import="java.util.List"%>
<%@page import="java.util.Map" %>


<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
Map<String,Object> articleRow = (Map<String,Object>) request.getAttribute("articleRow");
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>수정</title>
</head>
<body>


<style>
.con {
	margin-right:auto;
	margin-left:auto;
	width: 50%;
	text-align: left;
}

</style>

<div class="con">
	<h2><%=articleRow.get("id")%>번 글</h2>
	<h2><%=articleRow.get("name")%>님 글 수정</h2>
	
	<form action="doModify" method="POST">
	<input type="hidden" value="<%=articleRow.get("id")%>" name="id" />

	<div><%=articleRow.get("id")%>번 게시글</div>
	<div>새 제목 : <input type="text" placeholder="제목 입력" name="title"></div>
	<div>새 내용 : <textarea type="text" placeholder="내용 입력" name="body"></textarea></div>
	
	
	<button type="submit">저장</button>
	</form>
	
	<div><a href="detail?id=<%=articleRow.get("id")%>"> 상세보기로 돌아가기 </a></div>
</div>

</body>
</html>