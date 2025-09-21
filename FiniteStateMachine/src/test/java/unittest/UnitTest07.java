package unittest;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import botImpl.Bot;
import botImpl.BotFactory;
import waterballCommunity.Member;
import waterballCommunity.WaterballCommunity;


public class UnitTest07 extends BaseTest {

	String expectedFilePath = System.getProperty("user.dir") + "/src/test-data/unit-tests/ChatBot_Interactive_LogoutSwitchToDefault.out";
	
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
		Member user3 = waterballCommunity.login("3", false);
		Member user4 = waterballCommunity.login("4", false);
		Member user5 = waterballCommunity.login("5", false);
		Member user6 = waterballCommunity.login("6", false);
		Member user7 = waterballCommunity.login("7", false);
		Member user8 = waterballCommunity.login("8", false);
		Member user9 = waterballCommunity.login("9", false);
		waterballCommunity.logout("9");
		waterballCommunity.newMessage("1", "Now we only have 9 people online", new String[] {});

		// 比較期望檔案與實際日誌檔案
		assertLogFileEquals(expectedFilePath);
	}

}
