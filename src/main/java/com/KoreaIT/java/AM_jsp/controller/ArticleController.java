package com.KoreaIT.java.AM_jsp.controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.KoreaIT.java.AM_jsp.service.ArticleService;
import com.KoreaIT.java.AM_jsp.util.DBUtil;
import com.KoreaIT.java.AM_jsp.util.SecSql;


public class ArticleController {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Connection conn;
	private ArticleService articleService;
	
	public ArticleController(HttpServletRequest request, HttpServletResponse response, Connection conn) {
		this.conn = conn;
		this.request = request;
		this.response = response;
		this.articleService = new ArticleService(conn);
	}
	
	public void showList() throws ServletException, IOException {
		int page = 1;
		
		// parameter("객체명")는 ?"객체명"="값"에서 값을 가지고옴
		if(request.getParameter("page") != null && request.getParameter("page").length() != 0) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		int listInApage = 10;
		int limitFrom = (page-1)*listInApage;
		
		int totalCnt = articleService.getTotalCnt();
		int totalPage = (int)Math.ceil(totalCnt/(double)listInApage);		
		
		List<Map<String,Object>> aticleRows = articleService.getForPrintArticles(limitFrom,listInApage);

		request.setAttribute("aticleRows", aticleRows);
		request.setAttribute("page", page);
		request.setAttribute("totalCnt", totalCnt);
		request.setAttribute("totalPage", totalPage);
	
		request.getRequestDispatcher("/jsp/article/list.jsp").forward(request, response);
	}
	
	public void showDetail() throws ServletException, IOException{
		int id = Integer.parseInt(request.getParameter("id"));
		
		Map<String, Object> articleRow = articleService.getArticleRow(id);
		
		request.setAttribute("articleRow",articleRow);
		request.getRequestDispatcher("/jsp/article/detail.jsp").forward(request, response);
	}
	
	public void doDelete() throws IOException {
		
		HttpSession session = request.getSession();
		
		if (session.getAttribute("loginMemberLoginId") == null) {
			response.getWriter().append(String.format("<script>alert('로그인이 필요합니다'); location.replace('../member/login');</script>"));
			return;
		}
		
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		SecSql sql = SecSql.from("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);
		
		Map<String, Object> articleRow = DBUtil.selectRow(conn, sql);
		
		int loginedMemberId = (int) session.getAttribute("loginMemberId");
		
		if (loginedMemberId != (int) articleRow.get("memberId")) {
			response.getWriter().append(String.format("<script>alert('%d번 글에 대한 권한이 없습니다'); location.replace('list')</script>", id));
			return;
		}
		
		sql = SecSql.from("DELETE");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);
		
		DBUtil.delete(conn, sql);
		
		response.getWriter().append(String.format(
					"<script>alert('%d번 글이 삭제되었습니다'); location.replace('../article/list');</script>", id));
	}
	
	public void doModify() throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		
		String title = request.getParameter("title");
		String body = request.getParameter("body");
		
		SecSql sql = SecSql.from("UPDATE article");
		sql.append("SET");
		sql.append("regDate = NOW()," );
		sql.append("title = ?,", title);
		sql.append("`body` = ?", body);
		sql.append("WHERE id = ?;", id);
		
		DBUtil.update(conn, sql);
		
		response.getWriter().append(String.format("<script>alert('%d번 글이 수정되었습니다'); location.replace('detail?id=%d');</script>", id, id));
	}
	
	public void doWrite() throws IOException {
		HttpSession session = request.getSession();
		
		String title = request.getParameter("title");
		String body = request.getParameter("body");
		int loginMemberId = (int) session.getAttribute("loginMemberId");
		System.out.println(loginMemberId);
		
		SecSql sql = SecSql.from("INSERT INTO article");
		sql.append("SET regDate = NOW(),");
		sql.append("memberId = ?,", loginMemberId);
		sql.append("title = ?,", title);
		sql.append("`body` = ?", body);
		
		int id = DBUtil.insert(conn, sql);
		
		System.out.println("insert 성공");
		
		response.getWriter()
		.append(String.format("<script>alert('%d번 글이 작성됨'); location.replace('list');</script>", id));
	}

	public void showModify() throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		if (session.getAttribute("loginMemberLoginId") == null) {
			response.getWriter().append(String.format("<script>alert('로그인이 필요합니다'); location.replace('../member/login');</script>"));
			return;
		}
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		SecSql sql = SecSql.from("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);
		
		Map<String, Object> articleRow = DBUtil.selectRow(conn, sql);
		
		int loginedMemberId = (int) session.getAttribute("loginMemberId");
		
		if (loginedMemberId != (int) articleRow.get("memberId")) {
			response.getWriter().append(String.format("<script>alert('%d번 글에 대한 권한이 없습니다'); location.replace('list')</script>", id));
			return;
		}
		
		sql = SecSql.from("SELECT A.*, M.name");
		sql.append("FROM article A");
		sql.append("INNER JOIN `member` M");
		sql.append("ON A.memberId = M.id");
		sql.append("WHERE A.id = ?", id);
		
		articleRow = DBUtil.selectRow(conn, sql);
		
		request.setAttribute("articleRow",articleRow);
		request.getRequestDispatcher("/jsp/article/modify.jsp").forward(request, response);
	}
	
	public void showWrite() throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("loginMemberLoginId") == null) {
			response.getWriter().append(
					String.format("<script>alert('로그인이 필요합니다'); location.replace('../member/login');</script>"));
			return;
		}
		
		request.getRequestDispatcher("/jsp/article/write.jsp").forward(request, response);
	}
	
	
}
