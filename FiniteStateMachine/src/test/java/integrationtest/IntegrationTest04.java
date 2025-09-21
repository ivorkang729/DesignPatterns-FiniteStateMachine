package integrationtest;

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


public class IntegrationTest04 extends BaseTest {

	String expectedFilePath = System.getProperty("user.dir") + "/src/test-data/integration-tests/knowledge-king.timeout.out";
	
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
		
		// 登入事件序列
		Member user1 = waterballCommunity.login("1", true);   // admin
		Member user2 = waterballCommunity.login("2", false);
		Member user3 = waterballCommunity.login("3", false);
		Member user4 = waterballCommunity.login("4", false);
		
		// 管理員啟動知識王
		waterballCommunity.newMessage("1", "king", new String[] {"bot"});
		
		// 時間流逝
		bot.increaseElapsedTime(3, TimeUnit.SECONDS);
		
		waterballCommunity.newMessage("3", "開始遊戲了！", new String[] {"1", "2", "3", "4", "5"});
		
		// 第一題答對
		waterballCommunity.newMessage("2", "A", new String[] {"bot"});
		
		// 大家決定去吃飯
		waterballCommunity.newMessage("1", "我先去吃飯好了", new String[] {"1", "2", "3", "4", "5"});
		
		// 時間流逝（3分鐘）
		bot.increaseElapsedTime(3, TimeUnit.MINUTES);
		
		waterballCommunity.newMessage("3", "也是，先吃飯", new String[] {"1", "2", "3", "4", "5"});
		
		// 時間流逝（3分鐘）
		bot.increaseElapsedTime(3, TimeUnit.MINUTES);
		
		waterballCommunity.newMessage("2", "好啦，先吃飯", new String[] {});
		
		// 時間流逝（30分鐘）
		bot.increaseElapsedTime(30, TimeUnit.MINUTES);
		
		waterballCommunity.newMessage("4", "好好，先吃飯", new String[] {});
		
		// 時間流逝（27分鐘）- 總計超過1小時，觸發遊戲超時
		bot.increaseElapsedTime(27, TimeUnit.MINUTES);
		
		// 遊戲超時後的對話
		waterballCommunity.newMessage("2", "哈，好險我有偷回答，又是我贏。", new String[] {"1", "2", "3", "4", "5"});

		// 比較期望檔案與實際日誌檔案
		assertLogFileEquals(expectedFilePath);
	}

}
