package unittest;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bot.Bot;
import bot.BotFactory;
import community.Member;
import community.WaterballCommunity;


public class UnitTest02 extends BaseTest {

	String expectedFilePath = System.getProperty("user.dir") + "/src/test-data/unit-tests/ChatBot_Default_MessageCycle.out";
	
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
		bot = BotFactory.createBot(waterballCommunity, "2023-08-07 00:00:00", 20);
		waterballCommunity.registerEventListener(bot);
	}

	@After
	public void tearDown() throws Exception {
	}

	@SuppressWarnings("unused")
	@Test
	public void test() throws IOException {
		Member user = waterballCommunity.login("1", false);
		waterballCommunity.newMessage("1", "第一句", new String[] {});
		waterballCommunity.newMessage("1", "第二句", new String[] {});
		waterballCommunity.newMessage("1", "第三句", new String[] {});
		waterballCommunity.newMessage("1", "第四句", new String[] {});

		// 比較期望檔案與實際日誌檔案
		assertLogFileEquals(expectedFilePath);
	}

}
