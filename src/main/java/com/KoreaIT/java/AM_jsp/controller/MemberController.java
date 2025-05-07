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

import com.KoreaIT.java.AM_jsp.util.DBUtil;
import com.KoreaIT.java.AM_jsp.util.SecSql;

public class MemberController {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Connection conn;
	
	public MemberController(HttpServletRequest request, HttpServletResponse response, Connection conn) {
		this.conn = conn;
		this.request = request;
		this.response = response;
	}

	public void showJoin() throws ServletException, IOException {
		request.getRequestDispatcher("/jsp/member/join.jsp").forward(request, response);
		
	}

	public void doJoin() throws ServletException, IOException {
		String loginId = request.getParameter("loginId");
		String loginPw = request.getParameter("loginPw");
		String name = request.getParameter("name");
		
		SecSql sql = SecSql.from("SELECT COUNT(*) AS cnt FROM `member`");
		sql.append("WHERE loginId = ?;", loginId);
		
		boolean isJoinableLoginId = DBUtil.selectRowIntValue(conn, sql) == 0;
		
		if(isJoinableLoginId == false) {
			response.getWriter().append(String
					.format("<script>alert('%s는 이미 사용중'); location.replace('../member/join');</script>", loginId));
			return;
		}
		
		sql = SecSql.from("INSERT INTO `member`");
		sql.append("SET regDate = NOW(),");
		sql.append("loginId = ?,", loginId);
		sql.append("loginPw = ?,", loginPw);
		sql.append("`name` = ?;", name);
		
		int id = DBUtil.insert(conn, sql);

		response.getWriter().append(
				String.format("<script>alert('%d번 회원이 가입됨'); location.replace('../article/list');</script>", id));
		
	}
	
	public void showLogin() throws ServletException, IOException {
		request.getRequestDispatcher("/jsp/member/login.jsp").forward(request, response);
		
	}
	
	public void doLogin() throws ServletException, IOException{
		String loginId = request.getParameter("loginId");
		String loginPw = request.getParameter("loginPw");

		SecSql sql = SecSql.from("SELECT *");
		sql.append("FROM `member`");
		sql.append("WHERE loginId = ?;", loginId);

		Map<String, Object> memberRow = DBUtil.selectRow(conn, sql);

		if (memberRow.isEmpty()) {
			response.getWriter().append(String.format(
					"<script>alert('%s는 없는 아이디입니다'); location.replace('../member/login');</script>", loginId));
			return;
		}
		if (memberRow.get("loginPw").equals(loginPw) == false) {
			response.getWriter().append(
					String.format("<script>alert('비밀번호가 불일치합니다'); location.replace('../member/login');</script>"));
			return;
		}

		HttpSession session = request.getSession();

		session.setAttribute("loginMember", memberRow);
		session.setAttribute("loginMemberId", memberRow.get("id"));
		session.setAttribute("loginMemberLoginId", memberRow.get("loginId"));

		response.getWriter().append(String.format(
					"<script>alert('%s님 로그인됨'); location.replace('../home/main');</script>", memberRow.get("name")));
		
	}

	public void doLogout() throws ServletException, IOException{
		HttpSession session = request.getSession();

		session.removeAttribute("loginMember");
		session.removeAttribute("loginMemberId");
		session.removeAttribute("loginMemberLoginId");

		response.getWriter()
				.append(String.format("<script>alert('로그아웃 되었습니다'); location.replace('../home/main');;</script> "));
		
	}


	
	

}
