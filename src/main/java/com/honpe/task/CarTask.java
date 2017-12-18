package com.honpe.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.honpe.crawler.car.SinaCarCrawler;
import com.honpe.crawler.car.ChinaCarCrawler;
import com.honpe.crawler.car.NetEaseCarCrawler;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

@Component
public class CarTask {

	@Autowired
	private SinaCarCrawler sinaCarCrawler;
	@Autowired
	private NetEaseCarCrawler netEaseCarCrawler;
	@Autowired
	private ChinaCarCrawler chinaCarCrawler;

	@Scheduled(cron = "0 0 12 * * ?")
	public void crawlerSinaCar() {
		Spider.create(sinaCarCrawler).addUrl("http://auto.sina.com.cn/news/")
				.setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100))).thread(1).run();
	}

	@Scheduled(cron = "0 30 11 * * ?")
	public void crawlerNetEase() {
		Spider.create(netEaseCarCrawler).addUrl("http://auto.163.com/news/")
				.setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100))).thread(1).run();
	}

	@Scheduled(cron = "0 15 11 * * ?")
	public void crawlerChinaCar() {
		Spider.create(chinaCarCrawler).addUrl("http://www.chinaautonews.com.cn/list-13-1.html")
				.setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100))).thread(1).run();
	}
}
