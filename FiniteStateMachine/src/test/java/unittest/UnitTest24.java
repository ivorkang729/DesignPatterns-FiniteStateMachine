package unittest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import botImpl.Bot;
import botImpl.BotFactory;
import waterballCommunity.Member;
import waterballCommunity.WaterballCommunity;


public class UnitTest24 extends BaseTest {

	String expectedFilePath = System.getProperty("user.dir") + "/src/test-data/unit-tests/KnowledgeKing_TimeOut.out";
	
	WaterballCommunity waterballCommunity;
	Bot bot;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		waterballCommunity = new WaterballCommunity();
		bot = BotFactory.createBot(waterballCommunity, "2023-08-07 00:00:00", 10);
		waterballCommunity.registerEventListener(bot);
	}

	@After
	public void tearDown() throws Exception {
	}

	@SuppressWarnings("unused")
	@Test
	public void test() throws IOException {
		Member admin = waterballCommunity.login("admin", true);
		waterballCommunity.newMessage("admin", "king", new String[] {"bot"});
		
		bot.increaseElapsedTime(1, TimeUnit.HOURS);
		bot.increaseElapsedTime(20, TimeUnit.SECONDS);

		// 比較期望檔案與實際日誌檔案
		assertLogFileEquals(expectedFilePath);
	}

}
