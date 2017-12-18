package com.honpe.crawler.phone;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.honpe.dao.NewsDao;
import com.honpe.domain.News;
import com.honpe.table.TableType;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

@Component
public class ZolPhoneCrawler implements PageProcessor {

	@Autowired
	private NewsDao newsDao;
	@Autowired
	private SimpleDateFormat simpleDateFormat;
	private Site site = Site.me().setRetryTimes(3).setSleepTime(500).setTimeOut(10000);

	public Site getSite() {
		return site;
	}

	public void process(Page page) {
		if (!page.getUrl().regex("http://mobile.zol.com.cn/\\d{3}/\\d+.html").match()) {
			page.addTargetRequests(page.getHtml().css("div #mobile-news").links().regex("http://mobile.zol.com.cn/\\d{3}/\\d+.html").all());
		} else {
			String title = page.getHtml().xpath("//div[@class=article-header]/h1/text()").get();
			if (title != null) {
				int result = newsDao.findOneByTitle(title, TableType.PHONE.getTable());
				if (result > 0)
					return;
				String img = page.getHtml().xpath("//img[@id=content-first-img]/@src").get();
				if (StringUtils.isBlank(img))
					return;
				String newsTime = page.getHtml().xpath("//span[@id=pubtime_baidu]/text()").get();
				Date createTime = null;
				try {
					createTime = simpleDateFormat.parse(newsTime);
				} catch (ParseException e) {
					e.printStackTrace();
					return;
				}
				News news = new News();
				news.setTitle(title);
				String article_content = page.getHtml().xpath("//div[@id=article-content]/outerHtml()").get();
				news.setContent(article_content);
				news.setNewsTime(newsTime);
				news.setCreateTime(createTime);
				news.setCom("中关村在线");
				news.setImg(img);
				String preview = page.getHtml().xpath("//div[@class=article-cont]/allText()").get().trim().substring(0, 100) + "…";
				news.setPreview(preview);
				try {
					newsDao.insertOne(news, TableType.PHONE.getTable());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
