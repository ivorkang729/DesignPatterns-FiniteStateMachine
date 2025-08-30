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


public class UnitTest04 extends BaseTest {

	String expectedFilePath = System.getProperty("user.dir") + "/src/test-data/unit-tests/ChatBot_Default_MultipleUsers.out";
	
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
		Member user1 = waterballCommunity.login("1", false);
		Member user2 = waterballCommunity.login("2", false);
		waterballCommunity.newMessage("1", "Hello from user1", new String[] {});
		waterballCommunity.newMessage("2", "Hello from user2", new String[] {});

		// 比較期望檔案與實際日誌檔案
		assertLogFileEquals(expectedFilePath);
	}

}
