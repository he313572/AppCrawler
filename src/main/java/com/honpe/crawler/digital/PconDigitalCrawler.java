package com.honpe.crawler.digital;

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
public class PconDigitalCrawler implements PageProcessor {

	@Autowired
	private NewsDao newsDao;
	@Autowired
	private SimpleDateFormat simpleDateFormat;
	private Site site = Site.me().setRetryTimes(3).setSleepTime(500).setTimeOut(10000);

	public Site getSite() {
		return site;
	}

	public void process(Page page) {
		if (!page.getUrl().regex("http://news.pconline.com.cn/\\d{4}/\\d+.html").match()) {
			page.addTargetRequests(page.getHtml().css("div .list").links().regex("http://news.pconline.com.cn/\\d{4}/\\d+.html").all());
		} else {
			String title = page.getHtml().xpath("//h1[@class=content-tit-1]/text()").get();
			if (title != null) {
				int result = newsDao.findOneByTitle(title, TableType.DIGITAL.getTable());
				if (result > 0)
					return;
				String img = page.getHtml().xpath("//div[@id=JCmtImg]//img/@#src").get();
				if (StringUtils.isBlank(img))
					return;
				String newsTime = page.getHtml().xpath("//span[@class=pubtime]/text()").get() + ":00";
				Date createTime = null;
				try {
					createTime = simpleDateFormat.parse(newsTime);
				} catch (ParseException e) {
					e.printStackTrace();
					return;
				}
				News news = new News();
				news.setTitle(title);
				String oldChar = "src=\"http://www1.pconline.com.cn/images/articleImageLoading.gif\" #";
				String content = page.getHtml().xpath("//div[@class=content]/outerHtml()").get().replace(oldChar, "");
				news.setContent("<meta name=\"referrer\" content=\"never\">" + content);
				news.setNewsTime(newsTime);
				news.setCreateTime(createTime);
				news.setCom("太平洋网络");
				news.setImg(img);
				String preview = page.getHtml().xpath("//div[@class=content]/allText()").get().trim().substring(0, 100) + "…";
				news.setPreview(preview);
				try {
					newsDao.insertOne(news, TableType.DIGITAL.getTable());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
