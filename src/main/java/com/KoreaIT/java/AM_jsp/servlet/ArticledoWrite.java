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



@WebServlet("/article/doWrite")
public class ArticledoWrite extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.setContentType("text/html;charset=UTF-8");
		
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
 			throws ServletException, IOException {
 		doGet(request, response);
 	}

}
