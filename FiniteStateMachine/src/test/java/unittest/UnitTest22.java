package unittest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bot.Bot;
import bot.BotFactory;
import waterballCommunity.Member;
import waterballCommunity.WaterballCommunity;


public class UnitTest22 extends BaseTest {

	String expectedFilePath = System.getProperty("user.dir") + "/src/test-data/unit-tests/KnowledgeKing_TieGame.out";
	
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
		Member user1 = waterballCommunity.login("1", false);
		Member user2 = waterballCommunity.login("2", false);
		waterballCommunity.newMessage("1", "A", new String[] {"bot"});
		waterballCommunity.newMessage("2", "C", new String[] {"bot"});
		
		bot.increaseElapsedTime(1, TimeUnit.HOURS);

		// 比較期望檔案與實際日誌檔案
		assertLogFileEquals(expectedFilePath);
	}

}
