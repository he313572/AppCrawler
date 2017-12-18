package com.honpe.dao;

import java.sql.SQLException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.honpe.domain.News;

@Transactional(propagation = Propagation.REQUIRED)
public class NewsDao extends JdbcDaoSupport {
	public void insertOne(News news, String table) throws SQLException {
		Object[] args = { news.getTitle(), news.getContent(), news.getNewsTime(), news.getCreateTime(), news.getCom(), news.getImg(),
				news.getPreview() };
		String sql = "INSERT INTO " + table + "(title,content,newsTime,createTime,com,img,preview) VALUES(?,?,?,?,?,?,?)";
		this.getJdbcTemplate().update(sql, args);
	}

	public int findOneByTitle(String title, String table) {
		String sql = "SELECT count(0) FROM " + table + " WHERE title = ?";
		return this.getJdbcTemplate().queryForObject(sql, Integer.class, title);
	}
}
