

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.honpe.crawler.car.SinaCarCrawler;
import com.honpe.crawler.car.ChinaCarCrawler;
import com.honpe.crawler.car.NetEaseCarCrawler;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

public class CatTest {
	@Test
	public void fn() {
//		test1();
	}
	private void test3() {
		String xmlPath = "ApplicationContext.xml";
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(xmlPath);
		NetEaseCarCrawler netEaseCrawler = context.getBean(NetEaseCarCrawler.class);
		Spider.create(netEaseCrawler).addUrl("http://auto.163.com/news/")
				.setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(30))).thread(1).run();
	}
	private void test2() {
		String xmlPath = "ApplicationContext.xml";
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(xmlPath);
		ChinaCarCrawler chinaCarCrawler = context.getBean(ChinaCarCrawler.class);
		Spider.create(chinaCarCrawler).addUrl("http://www.chinaautonews.com.cn/list-13-1.html")
				.setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(30))).thread(1).run();
	}
	private void test1() {
		String xmlPath = "ApplicationContext.xml";
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(xmlPath);
		SinaCarCrawler carCrawler = context.getBean(SinaCarCrawler.class);
		Spider.create(carCrawler).addUrl("http://auto.sina.com.cn/news/")
				.setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(30))).thread(1).run();
	}
}
