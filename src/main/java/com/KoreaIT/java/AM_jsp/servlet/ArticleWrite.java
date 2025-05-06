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

@WebServlet("/article/write")
public class ArticleWrite extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");

		HttpSession session = request.getSession();
		if (session.getAttribute("loginMemberLoginId") == null) {
			response.getWriter().append(
					String.format("<script>alert('로그인이 필요합니다'); location.replace('../member/login');</script>"));
			return;
		}
		
		request.getRequestDispatcher("/jsp/article/write.jsp").forward(request, response);

	}

}
