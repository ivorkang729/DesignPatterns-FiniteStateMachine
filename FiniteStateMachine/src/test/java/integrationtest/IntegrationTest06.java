package integrationtest;

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


public class IntegrationTest06 extends BaseTest {

	String expectedFilePath = System.getProperty("user.dir") + "/src/test-data/integration-tests/quota.out";
	
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
		// [started] 已在 setUp() 中處理
		
		// 登入管理員
		Member user1 = waterballCommunity.login("1", true);   // admin
		
		// 第一輪：知識王測試（quota: 20 -> 15）
		waterballCommunity.newMessage("1", "king", new String[] {"botImpl"});
		waterballCommunity.newMessage("1", "king-stop", new String[] {"botImpl"});
		waterballCommunity.newMessage("1", "測試正常對話，現在 quota 應該剩下 15。", new String[] {"botImpl"});
		
		// 第二輪：知識王測試（quota: 15 -> 10）
		waterballCommunity.newMessage("1", "king", new String[] {"botImpl"});
		waterballCommunity.newMessage("1", "king-stop", new String[] {"botImpl"});
		waterballCommunity.newMessage("1", "測試正常對話，現在 quota 應該剩下 10。", new String[] {"botImpl"});
		
		// 第三輪：知識王測試（quota: 10 -> 5）
		waterballCommunity.newMessage("1", "king", new String[] {"botImpl"});
		waterballCommunity.newMessage("1", "king-stop", new String[] {"botImpl"});
		waterballCommunity.newMessage("1", "測試正常對話，現在 quota 應該剩下 5。", new String[] {"botImpl"});
		
		// 錄音測試（quota: 5 -> 2）
		waterballCommunity.newMessage("1", "record", new String[] {"botImpl"});
		waterballCommunity.goBroadcasting(user1);
		waterballCommunity.speak(user1, "測試演講。");
		waterballCommunity.newMessage("1", "stop-recording", new String[] {"botImpl"});
		waterballCommunity.newMessage("1", "測試正常對話，現在 quota 應該剩下 2。", new String[] {"botImpl"});
		
		// 嘗試再次錄音（quota 不足，應該失敗）
		waterballCommunity.newMessage("1", "record", new String[] {"botImpl"});
		waterballCommunity.stopBroadcasting(user1);
		waterballCommunity.goBroadcasting(user1);
		waterballCommunity.speak(user1, "測試演講，現在 quota 應該剩下 2，不能錄音。");
		
		// 嘗試知識王（quota 不足，應該失敗）
		waterballCommunity.newMessage("1", "king", new String[] {"botImpl"});
		waterballCommunity.newMessage("1", "測試正常對話，現在 quota 應該剩下 2，不能玩 Knowledge King。", new String[] {"botImpl"});

		// 比較期望檔案與實際日誌檔案
		assertLogFileEquals(expectedFilePath);
	}

}
