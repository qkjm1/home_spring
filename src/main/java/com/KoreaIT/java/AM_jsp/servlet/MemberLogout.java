package com.KoreaIT.java.AM_jsp.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/member/doLogout")
public class MemberLogout extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = request.getSession();

		session.removeAttribute("loginMember");
		session.removeAttribute("loginMemberId");
		session.removeAttribute("loginMemberLoginId");

		response.getWriter()
				.append(String.format("<script>alert('로그아웃 되었습니다'); location.replace('../home/main');;</script> "));

	}
}
