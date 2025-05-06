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
<title>상세보기</title>
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
	<h1>상세 페이지</h1>

	<div>번호 : <%=articleRow.get("id")%></div>
	<div>게시날짜 : <%=articleRow.get("regDate")%></div>
	<div>작성자 : <%=articleRow.get("name") %></div>
	<div>제목 : <%=articleRow.get("title")%></div>
	<div>내용 : <%=articleRow.get("body")%></div>
	
	<div><a href="modify?id=<%=articleRow.get("id")%>">수정</a></div>
	<div><a href="doDelete?id=<%=articleRow.get("id")%>">삭제</a></div>
	<div><a href="list"> 목록으로 돌아가기 </a></div>
</div>

</body>
</html>