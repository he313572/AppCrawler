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
public class ChinaCarCrawler implements PageProcessor {

	@Autowired
	private NewsDao newsDao;
	@Autowired
	private SimpleDateFormat simpleDateFormat;
	private Site site = Site.me().setRetryTimes(3).setSleepTime(500).setTimeOut(10000);

	public Site getSite() {
		return site;
	}

	public void process(Page page) {
		if (!page.getUrl().regex("http://www.chinaautonews.com.cn/show-13-\\d+-1.html").match()) {
			page.addTargetRequests(
					page.getHtml().css("div .lside_nr").links().regex("http://www.chinaautonews.com.cn/show-13-\\d+-1.html").all());
		} else {
			String title = page.getHtml().xpath("//div[@class=lside]/h1/text()").get();
			if (title != null) {
				int result = newsDao.findOneByTitle(title, TableType.CAR.getTable());
				if (result > 0)
					return;
				String img = page.getHtml().xpath("//div[@class=detail-content]/p[2]//img/@src").get();
				if (StringUtils.isBlank(img))
					return;
				String[] info = page.getHtml().xpath("//div[@class=source]/text()").get().split("\\|");
				String newsTime = info[1].substring(1, info[1].length() - 2);
				Date createTime = null;
				try {
					createTime = simpleDateFormat.parse(newsTime);
				} catch (ParseException e) {
					e.printStackTrace();
					return;
				}
				News news = new News();
				news.setTitle(title);
				news.setContent(page.getHtml().xpath("//div[@class=detail-content]/html()").get());
				news.setCreateTime(createTime);
				news.setNewsTime(newsTime);
				String com = info[0].substring(info[0].indexOf("：") + 1);
				news.setCom(com.length() == 1 ? "中国汽车新闻网" : com);
				news.setImg(img);
				news.setPreview(page.getHtml().xpath("//div[@class=desc]/text()").get());
				try {
					newsDao.insertOne(news,TableType.CAR.getTable());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
