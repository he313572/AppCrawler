package com.honpe.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.honpe.crawler.technology.ZolTechnologylCrawler;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

@Component
public class TechnologyTask {
	@Autowired
	private ZolTechnologylCrawler zolTechnologylCrawler;

	@Scheduled(cron = "0 0 10 * * ?")
	public void crawlerSinaCar() {
		Spider.create(zolTechnologylCrawler).addUrl("http://geek.zol.com.cn")
				.setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100))).thread(1).run();
	}
}
