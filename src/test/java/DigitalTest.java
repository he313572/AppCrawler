import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.honpe.crawler.digital.PconDigitalCrawler;
import com.honpe.crawler.digital.ZolDigitalCrawler;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

public class DigitalTest {
	@Test
	public void fn() {
//		test2();
	}

	private void test1() {
		String xmlPath = "ApplicationContext.xml";
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(xmlPath);
		ZolDigitalCrawler zolDigitalCrawler = context.getBean(ZolDigitalCrawler.class);
		Spider.create(zolDigitalCrawler).addUrl("http://dcdv.zol.com.cn/more/2_782.shtml")
				.setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(30))).thread(1).run();
	}

	private void test2() {
		String xmlPath = "ApplicationContext.xml";
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(xmlPath);
		PconDigitalCrawler pconDigitalCrawler = context.getBean(PconDigitalCrawler.class);
		Spider.create(pconDigitalCrawler).addUrl("http://news.pconline.com.cn/digital/")
				.setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(30))).thread(1).run();
	}
}
