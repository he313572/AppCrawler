package com.honpe.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.honpe.crawler.digital.ZolDigitalCrawler;
import com.honpe.crawler.phone.PconPhoneCrawler;
import com.honpe.crawler.phone.ZolPhoneCrawler;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

@Component
public class PhoneTask {
	@Autowired
	private ZolPhoneCrawler zolPhoneCrawler;
	@Autowired
	private PconPhoneCrawler pconPhoneCrawler;
	@Scheduled(cron = "0 30 10 * * ?")
	public void crawlerSinaCar() {
		Spider.create(zolPhoneCrawler).addUrl("http://mobile.zol.com.cn/news/")
				.setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100))).thread(1).run();
	}
	@Scheduled(cron = "0 15 10 * * ?")
	public void crawlerPcon() {
		Spider.create(pconPhoneCrawler).addUrl("http://news.pconline.com.cn/mobile/")
				.setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100))).thread(1).run();
	}
}
