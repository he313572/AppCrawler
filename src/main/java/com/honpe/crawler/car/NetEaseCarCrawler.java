package com.honpe.crawler.car;

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
public class NetEaseCarCrawler implements PageProcessor {

	@Autowired
	private NewsDao newsDao;
	@Autowired
	private SimpleDateFormat simpleDateFormat;
	private Site site = Site.me().setRetryTimes(3).setSleepTime(500).setTimeOut(10000);

	public Site getSite() {
		return site;
	}

	public void process(Page page) {
		if (!page.getUrl().regex("http://auto.163.com/\\d{2}/\\d{4}/\\d{2}/[0-9|A-Z]+.html").match()) {
			page.addTargetRequests(page.getHtml().css("div .wrap-tab-panels").links()
					.regex("http://auto.163.com/\\d{2}/\\d{4}/\\d{2}/[0-9|A-Z]+.html").all());
		} else {
			String title = page.getHtml().xpath("//div[@class=post_content_main]/h1/text()").get();
			if (title != null) {
				int result = newsDao.findOneByTitle(title,TableType.CAR.getTable());
				if (result > 0)
					return;
				String img = page.getHtml().xpath("//p[@class=f_center]/img/@src").get();
				if (StringUtils.isBlank(img))
					return;
				String time = page.getHtml().xpath("//div[@class=post_time_source]/text()").get().split("来源:")[0];
				String newsTime = time.substring(1, time.length() - 1);
				Date createTime = null;
				try {
					createTime = simpleDateFormat.parse(newsTime);
				} catch (ParseException e) {
					e.printStackTrace();
					return;
				}
				News news = new News();
				news.setTitle(title);
				news.setContent(page.getHtml().xpath("//div[@class=post_text]/html()").get());
				news.setNewsTime(newsTime);
				news.setCreateTime(createTime);
				news.setCom(page.getHtml().xpath("//div[@class=post_time_source]/a[1]/text()").get());
				news.setImg(img);
				String preview = page.getHtml().xpath("//div[@class=post_text]/allText()").get().trim().substring(0, 100) + "…";
				news.setPreview(preview);
				try {
					newsDao.insertOne(news, TableType.CAR.getTable());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
