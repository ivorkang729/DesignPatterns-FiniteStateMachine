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


public class UnitTest12 extends BaseTest {

	String expectedFilePath = System.getProperty("user.dir") + "/src/test-data/unit-tests/Broadcast_MultipleSpeakers.out";
	
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

		waterballCommunity.goBroadcasting(user1);
		waterballCommunity.speak(user1, "Test 1");		
		waterballCommunity.stopBroadcasting(user1);

		waterballCommunity.goBroadcasting(user2);
		waterballCommunity.speak(user2, "Test 2");		
		waterballCommunity.stopBroadcasting(user2);

		// 比較期望檔案與實際日誌檔案
		assertLogFileEquals(expectedFilePath);
	}

}
