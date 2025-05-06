<%@page import="java.util.List"%>
<%@page import="java.util.Map" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
List<Map<String,Object>> aticleRows = (List<Map<String,Object>>) request.getAttribute("aticleRows");
int cPage = (int) request.getAttribute("page");
int totalCnt = (int) request.getAttribute("totalCnt");
int totalPage = (int) request.getAttribute("totalPage");

boolean isLogined = (boolean) request.getAttribute("isLogined");
int loginedMemberId = (int) request.getAttribute("loginedMemberId");
Map<String, Object> loginedMember = (Map<String, Object>) request.getAttribute("loginedMember");
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>목록</title>
</head>
<body>


<style>
table, th, td { border: 2px solid orange; }
table { 
	border-collapse: collapse;
	margin-right:auto;
	margin-left:auto;
	width: 80%;
}


th {text-align:left;}
div{
text-align:center;
}



.page {
font-size: 1.4rem;
}
.page > a {
color: green;
text-decoration: none;
}
.page > a.cpage {
color:black;
text-decoration: underline;
}

</style>
<div>
	<h1>목록</h1>
	
	<h4>총 게시물 :
		<%=totalCnt %>
	</h4>
	
	<%
	if (isLogined){
	%>
	<div><%=loginedMember.get("name")%>님 로그인 중</div>
	<div><a href="../member/doLogout">로그아웃</a></div>
	<% 
	}
	%>
		
	<%
	if (!isLogined){
	%>
	<div><a href="../member/login">로그인</a></div>
	<% 
	}
	%>
	
	<div><a href="../home/main">메인화면</a></div>
	
</div>
	<table>
		<thead  class=con>
			<tr>
				<th>번호</th>
				<th>날짜</th>
				<th>작성자</th>
				<th>제목</th>
			</tr>
		</thead>
		<tbody >
			<%
			for (Map<String,Object> aticleRow : aticleRows){ // 압축해제
			%>
			<tr>
				<td><%=aticleRow.get("id")%>번</td>
				<td><%=aticleRow.get("regDate")%></td>
				<td><%=aticleRow.get("name")%></td>
				<td><a href="detail?id=<%=aticleRow.get("id")%>"><%=aticleRow.get("title")%></a></td>
			</tr>
			<%
			}
			%>			
		</tbody>
	</table>
	
	<div class="page">
		<% for(int i = 1; i < totalPage; i++ ){ %>
		<a class="<%=cPage == 1 ?"cPage" : ""%>" href="list?page=<%=i%>"><%=i%></a>
		<% }%>
	</div>
	
	<div><a href="write">글쓰기</a></div>

</body>
</html>