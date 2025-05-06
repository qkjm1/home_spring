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



@WebServlet("/article/list")
public class ArticleList extends HttpServlet {

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
			
			boolean isLogined = false;
	 		int loginedMemberId = -1;
	 		Map<String, Object> loginedMember = null;
	 		
	 		if (session.getAttribute("loginMemberLoginId") != null) {
	 			isLogined = true;
	 			loginedMemberId = (int) session.getAttribute("loginMemberId");
	 			loginedMember = (Map<String, Object>) session.getAttribute("loginMember");
	 		}
	 		
	 		request.setAttribute("isLogined", isLogined);
	 		request.setAttribute("loginedMemberId", loginedMemberId);
	 		request.setAttribute("loginedMember", loginedMember);
			
			
			int page = 1;
			

			// parameter("객체명")는 ?"객체명"="값"에서 값을 가지고옴
			if(request.getParameter("page") != null && request.getParameter("page").length() != 0) {
				page = Integer.parseInt(request.getParameter("page"));
			}
			
			int listInApage = 10;
			int limitFrom = (page-1)*listInApage;
			
			SecSql sql = SecSql.from("SELECT COUNT(*) ");
			sql.append("FROM article;");
			
			int totalCnt = DBUtil.selectRowIntValue(conn, sql);//행이 몇개인지
			int totalPage = (int)Math.ceil(totalCnt/(double)listInApage);
			
			sql = SecSql.from("SELECT A.*, M.name ");
			sql.append("FROM article A");
			sql.append("INNER JOIN `member` M");
			sql.append("ON A.memberId = M.id");
			sql.append("ORDER BY id DESC");
			sql.append("LIMIT ?, ?;", limitFrom, listInApage);
			
			
			List<Map<String,Object>> aticleRows = DBUtil.selectRows(conn, sql);
	
			request.setAttribute("aticleRows", aticleRows);
			request.setAttribute("page", page);
			request.setAttribute("totalCnt", totalCnt);
			request.setAttribute("totalPage", totalPage);
			
					
			request.getRequestDispatcher("/jsp/article/list.jsp").forward(request, response);
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
