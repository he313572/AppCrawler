package com.honpe.domain;

import java.util.Date;

public class News {
	private String id;
	private String title;
	private String content;
	private String newsTime;
	private Date createTime;
	private String com;
	private String img;
	private String preview;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getNewsTime() {
		return newsTime;
	}
	public void setNewsTime(String newsTime) {
		this.newsTime = newsTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCom() {
		return com;
	}
	public void setCom(String com) {
		this.com = com;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getPreview() {
		return preview;
	}
	public void setPreview(String preview) {
		this.preview = preview;
	}
	@Override
	public String toString() {
		return "Car [id=" + id + ", title=" + title + ", content=" + content + ", newsTime=" + newsTime + ", createTime=" + createTime
				+ ", com=" + com + ", img=" + img + ", preview=" + preview + "]";
	}
	
}
