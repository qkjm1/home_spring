package com.KoreaIT.java.AM_jsp.servlet;

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

import com.KoreaIT.java.AM_jsp.util.DBUtil;
import com.KoreaIT.java.AM_jsp.util.SecSql;



@WebServlet("/article/modify")
public class ArticleModify extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = request.getSession();
		
		if (session.getAttribute("loginMemberLoginId") == null) {
			response.getWriter().append(String.format("<scrpit>alert('로그인이 필요합니다'); location.replace('../member/login');</scrpit>"));
			return;
		}
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("클래스 x");
			e.printStackTrace();

		}

		String url = "jdbc:mysql://127.0.0.1:3306/AM_JSP_25_04?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
		String user = "root";
		String password = "";

		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, user, password);
					
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
			response.getWriter().append("jsp까지 안감");
			
		} catch (SQLException e) {
			System.out.println("에러 1 : " + e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		
		
	}


}
