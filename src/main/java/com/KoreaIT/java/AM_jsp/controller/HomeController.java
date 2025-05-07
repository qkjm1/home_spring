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

public class HomeController {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Connection conn;
	
	public HomeController(HttpServletRequest request, HttpServletResponse response, Connection conn) {
		this.conn = conn;
		this.request = request;
		this.response = response;
	}
	
	public void showMain() throws ServletException, IOException {
		
		request.getRequestDispatcher("/jsp/home/main.jsp").forward(request, response);
	}
	
}
