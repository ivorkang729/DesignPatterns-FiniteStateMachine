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


public class UnitTest17 extends BaseTest {

	String expectedFilePath = System.getProperty("user.dir") + "/src/test-data/unit-tests/Command_Record_StopByRecorder.out";
	
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
		Member user3 = waterballCommunity.login("3", false);
		Member user4 = waterballCommunity.login("4", false);

		waterballCommunity.goBroadcasting(user4);
		waterballCommunity.newMessage("3", "record", new String[] {"bot"});
		waterballCommunity.speak(user4, "Test");	
		waterballCommunity.newMessage("3", "stop-recording", new String[] {"bot"});

		// 比較期望檔案與實際日誌檔案
		assertLogFileEquals(expectedFilePath);
	}

}
