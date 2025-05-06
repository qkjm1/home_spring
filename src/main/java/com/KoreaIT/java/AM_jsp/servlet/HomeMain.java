package com.KoreaIT.java.AM_jsp.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;


@WebServlet("/home/main")
public class HomeMain extends HttpServlet {


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
		
		
		request.getRequestDispatcher("/jsp/home/main.jsp").forward(request, response);
	
	}

}
