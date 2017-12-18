import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.honpe.crawler.phone.PconPhoneCrawler;
import com.honpe.crawler.phone.ZolPhoneCrawler;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

public class PhoneTest {
	@Test
	public void fn() {
//		test2();
	}

	private void test1() {
		String xmlPath = "ApplicationContext.xml";
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(xmlPath);
		ZolPhoneCrawler zolPhoneCrawler = context.getBean(ZolPhoneCrawler.class);
		Spider.create(zolPhoneCrawler).addUrl("http://mobile.zol.com.cn/news/")
				.setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(30))).thread(1).run();
	}

	private void test2() {
		String xmlPath = "ApplicationContext.xml";
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(xmlPath);
		PconPhoneCrawler pconPhoneCrawler = context.getBean(PconPhoneCrawler.class);
		Spider.create(pconPhoneCrawler).addUrl("http://news.pconline.com.cn/mobile/")
				.setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(30))).thread(1).run();
	}
}
