package com.honpe.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.honpe.crawler.digital.PconDigitalCrawler;
import com.honpe.crawler.digital.ZolDigitalCrawler;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

@Component
public class DigitalTask {
	@Autowired
	private ZolDigitalCrawler zolDigitalCrawler;
	@Autowired
	private PconDigitalCrawler pconDigitalCrawler;

	@Scheduled(cron = "0 0 11 * * ?")
	public void crawlerSinaCar() {
		Spider.create(zolDigitalCrawler).addUrl("http://dcdv.zol.com.cn/more/2_782.shtml")
				.setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100))).thread(1).run();
	}

	@Scheduled(cron = "0 45 10 * * ?")
	public void crawlerPcon() {
		Spider.create(pconDigitalCrawler).addUrl("http://news.pconline.com.cn/digital/")
				.setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100))).thread(1).run();
	}
}
