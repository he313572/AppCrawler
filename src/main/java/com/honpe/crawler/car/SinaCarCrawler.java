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
public class SinaCarCrawler implements PageProcessor {

	@Autowired
	private NewsDao newsDao;
	@Autowired
	private SimpleDateFormat simpleDateFormat;
	private Site site = Site.me().setRetryTimes(3).setSleepTime(500).setTimeOut(10000);

	public Site getSite() {
		return site;
	}

	public void process(Page page) {
		if (!page.getUrl().regex("http://auto.sina.com.cn/news/hy/\\d{4}-\\d{2}-\\d{2}/detail-ifyp\\w{4}\\d+.shtml").match()) {
			page.addTargetRequests(page.getHtml().css("div .con").links()
					.regex("http://auto.sina.com.cn/news/hy/\\d{4}-\\d{2}-\\d{2}/detail-ifyp\\w{4}\\d+.shtml").all());
		} else {
			String title = page.getHtml().xpath("//h1[@class=main-title]/text()").get();
			if (title != null) {
				int result = newsDao.findOneByTitle(title,TableType.CAR.getTable());
				if (result > 0)
					return;
				String img = page.getHtml().xpath("//div[@class=img_wrapper]/img/@src").get();
				if (StringUtils.isBlank(img))
					return;
				String newsTime = page.getHtml().xpath("//span[@class=date]/text()").get();
				Date createTime = null;
				try {
					createTime = simpleDateFormat.parse(newsTime);
				} catch (ParseException e) {
					e.printStackTrace();
					return;
				}
				News news = new News();
				news.setTitle(title);
				news.setContent(page.getHtml().xpath("//div[@class=article]/html()").get());
				news.setNewsTime(newsTime);
				news.setCreateTime(createTime);
				news.setCom("新浪汽车");
				news.setImg(img);
				news.setPreview(page.getHtml().xpath("//div[@class=quotation]/p/text()").get());
				try {
					newsDao.insertOne(news,TableType.CAR.getTable());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
