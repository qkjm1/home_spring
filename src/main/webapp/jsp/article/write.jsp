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
<title>글쓰기</title>
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
	<h2>글쓰기</h2>
	
	<form action="doWrite" method="POST">
	

	<div>글쓰기</div>
	<div>제목 : <input type="text" placeholder="제목 입력" name="title"></div>
	<div>내용 : <textarea type="text" placeholder="내용 입력" name="body"></textarea></div>
	
	
	<button type="submit">저장</button>
	</form>
	
	<div><a href="list"> 목록으로 돌아가기 </a></div>
</div>

</body>
</html>