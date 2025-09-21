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


public class IntegrationTest01 extends BaseTest {

	String expectedFilePath = System.getProperty("user.dir") + "/src/test-data/integration-tests/interaction.chit-chat.out";
	
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
		Member user1 = waterballCommunity.login("1", true);
		Member user2 = waterballCommunity.login("2", false);
		Member user3 = waterballCommunity.login("3", false);
		Member user4 = waterballCommunity.login("4", false);
		Member user5 = waterballCommunity.login("5", false);
		Member user6 = waterballCommunity.login("6", false);
		
		// 第一波互動
		waterballCommunity.newMessage("1", "大家早安，今天我第一天上班呢", new String[] {});
		
		Member user7 = waterballCommunity.login("7", false);
		waterballCommunity.newMessage("4", "祝大家今天事事順利", new String[] {"1"});
		
		Member user8 = waterballCommunity.login("8", false);
		
		// 時間流逝
		bot.increaseElapsedTime(10, TimeUnit.SECONDS);
		
		Member user9 = waterballCommunity.login("9", false);
		
		// 互動狀態下的對話（10人在線）
		waterballCommunity.newMessage("1", "wow 有 10 個人在線上了呢（包含機器人）", new String[] {});
		waterballCommunity.newMessage("1", "大家早安，今天要吃麥當勞嗎？", new String[] {});
		
		// 時間流逝
		bot.increaseElapsedTime(7, TimeUnit.SECONDS);
		
		waterballCommunity.newMessage("4", "是啊大家早安喔", new String[] {"1"});
		waterballCommunity.newMessage("5", "別吧，吃肯德基好了", new String[] {"1"});
		
		// 時間流逝
		bot.increaseElapsedTime(3, TimeUnit.SECONDS);
		
		// 標記機器人的訊息
		waterballCommunity.newMessage("7", "還行啊", new String[] {"bot"});
		
		// 論壇貼文
		waterballCommunity.newPost("1", "9", "物件導向冷知識", "和大家分享一個冷知識，其實是先有「物件」才有「類別」喔！大家學會了嗎？", new String[] {});
		
		// 更多互動
		waterballCommunity.newMessage("6", "哈哈哈，今天有點疲憊呢，不想上班", new String[] {"1", "2", "4"});
		waterballCommunity.newMessage("2", "你看看機器人同意你呢，別上班了", new String[] {"6", "bot"});
		
		// 第二個論壇貼文
		waterballCommunity.newPost("2", "8", "分享一個關於 單一職責原則 的笑話，每次講起來都還是覺得很好笑", "(1) 欸你這個類別這樣做太多事了吧，違反單一職責原則啊，每個類別只能有一個職責，只能做一件事。 (2) 這個類別，確實只做一件事，那就是實現需求！", new String[] {"1", "2", "3"});
		
		waterballCommunity.newMessage("8", "發了一個文，分享笑話，哈哈", new String[] {});
		
		// 用戶登出序列
		waterballCommunity.logout("9");
		waterballCommunity.logout("8");
		waterballCommunity.logout("7");
		waterballCommunity.logout("6");
		
		// 回到預設狀態後的對話
		waterballCommunity.newMessage("1", "呀，大家下線了", new String[] {});
		waterballCommunity.newMessage("3", "是啊～！", new String[] {});

		// 比較期望檔案與實際日誌檔案
		assertLogFileEquals(expectedFilePath);
	}

}
