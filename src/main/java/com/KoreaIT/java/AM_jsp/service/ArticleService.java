package com.KoreaIT.java.AM_jsp.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.KoreaIT.java.AM_jsp.util.DBUtil;
import com.KoreaIT.java.AM_jsp.util.SecSql;

public class ArticleService {
	private Connection conn;
	
	public ArticleService(Connection conn) {
		this.conn = conn;
	}
	
	public int getTotalCnt() {
		SecSql sql = SecSql.from("SELECT COUNT(*) ");
		sql.append("FROM article;");
		
		return  DBUtil.selectRowIntValue(conn, sql);
	}

	public List<Map<String,Object>> getForPrintArticles(int limitFrom,int listInApage) {
		SecSql sql = SecSql.from("SELECT A.*, M.name ");
		sql.append("FROM article A");
		sql.append("INNER JOIN `member` M");
		sql.append("ON A.memberId = M.id");
		sql.append("ORDER BY id DESC");
		sql.append("LIMIT ?, ?;", limitFrom, listInApage);		
		
		return DBUtil.selectRows(conn, sql);
	}

	public Map<String, Object> getArticleRow(int id) {
		SecSql sql = SecSql.from("SELECT A.*, M.name");
		sql.append("FROM article A");
		sql.append("INNER JOIN `member` M");
		sql.append("ON A.memberId = M.id");
		sql.append("WHERE A.id = ?", id);
		
		return DBUtil.selectRow(conn, sql);
	}
	
	
}
