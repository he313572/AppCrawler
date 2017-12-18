
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.honpe.crawler.technology.ZolTechnologylCrawler;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

public class TechnologyTest {
	@Test
	public void fn() {
//		test1();
	}

	private void test1() {
		String xmlPath = "ApplicationContext.xml";
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(xmlPath);
		ZolTechnologylCrawler zolTechnologylCrawler = context.getBean(ZolTechnologylCrawler.class);
		Spider.create(zolTechnologylCrawler).addUrl("http://geek.zol.com.cn")
				.setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(30))).thread(1).run();
	}
}
