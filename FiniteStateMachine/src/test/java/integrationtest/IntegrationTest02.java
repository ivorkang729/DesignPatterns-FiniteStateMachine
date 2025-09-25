package integrationtest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bot.Bot;
import bot.facade.BotFactory;
import waterballCommunity.Member;
import waterballCommunity.WaterballCommunity;


public class IntegrationTest02 extends BaseTest {

	String expectedFilePath = System.getProperty("user.dir") + "/src/test-data/integration-tests/knowledge-king.normal-play.out";
	
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
		Member user5 = waterballCommunity.login("5", false);
		
		// 嘗試啟動知識王（非管理員）
		waterballCommunity.newMessage("2", "king", new String[] {"bot"});
		waterballCommunity.newMessage("1", "king", new String[] {"2"});
		
		// 時間流逝
		bot.increaseElapsedTime(3, TimeUnit.SECONDS);
		
		waterballCommunity.newMessage("2", "怎麼不能用", new String[] {"bot"});
		waterballCommunity.newMessage("4", "要 admin 才能用這個指令吧", new String[] {"2"});
		
		// 管理員正確啟動知識王
		waterballCommunity.newMessage("1", "king", new String[] {"2", "bot"});
		waterballCommunity.newMessage("3", "開始遊戲了！", new String[] {"1", "2", "3", "4", "5"});
		
		// 第一題答題（沒有標記機器人的回答）
		waterballCommunity.newMessage("2", "A", new String[] {});
		waterballCommunity.newMessage("3", "B", new String[] {});
		waterballCommunity.newMessage("1", "record", new String[] {"bot"});  // 嘗試錄音指令
		waterballCommunity.newMessage("5", "D", new String[] {});
		
		// 時間流逝
		bot.increaseElapsedTime(3, TimeUnit.SECONDS);
		
		waterballCommunity.newMessage("1", "你們要標他才行", new String[] {"2", "3", "5"});
		
		// 第一題正確答案（標記機器人）
		waterballCommunity.newMessage("1", "A", new String[] {"bot"});
		waterballCommunity.newMessage("4", "可惡！！", new String[] {"1"});
		
		// 時間流逝
		bot.increaseElapsedTime(3, TimeUnit.SECONDS);
		
		// 第二題
		waterballCommunity.newMessage("2", "看我一個鬥智鬥勇！", new String[] {});
		
		// 時間流逝
		bot.increaseElapsedTime(3, TimeUnit.SECONDS);
		
		waterballCommunity.newMessage("2", "C", new String[] {"bot"});
		waterballCommunity.newMessage("1", "C", new String[] {"bot"});
		
		// 時間流逝
		bot.increaseElapsedTime(3, TimeUnit.SECONDS);
		
		// 第三題
		waterballCommunity.newMessage("2", "A", new String[] {"bot"});
		
		// 時間流逝
		bot.increaseElapsedTime(3, TimeUnit.SECONDS);
		
		// 遊戲結束，user2 獲勝
		waterballCommunity.newMessage("2", "哈我贏了", new String[] {"bot"});
		
		// 時間流逝
		bot.increaseElapsedTime(3, TimeUnit.SECONDS);
		
		// 再玩一次的請求
		waterballCommunity.newMessage("4", "再玩一次啦", new String[] {"1", "2", "3", "4", "5"});
		
		// 時間流逝
		bot.increaseElapsedTime(3, TimeUnit.SECONDS);
		
		waterballCommunity.newMessage("2", "好啊", new String[] {"4"});
		waterballCommunity.newMessage("2", "play again", new String[] {"bot"});
		
		// 長時間流逝（3分鐘）
		bot.increaseElapsedTime(3, TimeUnit.MINUTES);
		
		// 第二輪遊戲
		waterballCommunity.newMessage("3", "A", new String[] {"bot"});
		waterballCommunity.newMessage("2", "C", new String[] {"bot"});
		waterballCommunity.newMessage("2", "A", new String[] {"bot"});
		
		// 時間流逝
		bot.increaseElapsedTime(10, TimeUnit.SECONDS);
		
		waterballCommunity.newMessage("3", "你也太快了吧", new String[] {"2"});
		
		// 時間流逝
		bot.increaseElapsedTime(10, TimeUnit.SECONDS);
		
		waterballCommunity.newMessage("2", "還好啦", new String[] {"3"});
		waterballCommunity.newMessage("4", "可惡，沒玩到⋯⋯", new String[] {});

		// 比較期望檔案與實際日誌檔案
		assertLogFileEquals(expectedFilePath);
	}

}
