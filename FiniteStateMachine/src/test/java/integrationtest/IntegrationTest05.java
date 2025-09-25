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


public class IntegrationTest05 extends BaseTest {

	String expectedFilePath = System.getProperty("user.dir") + "/src/test-data/integration-tests/normal.chit-chat.out";
	
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
		
		// 時間流逝
		bot.increaseElapsedTime(10, TimeUnit.SECONDS);
		
		// 開始聊天
		waterballCommunity.newMessage("1", "大家早安，今天我第一天上班呢", new String[] {});
		
		// 時間流逝
		bot.increaseElapsedTime(7, TimeUnit.SECONDS);
		
		waterballCommunity.newMessage("4", "祝大家今天事事順利", new String[] {"1"});
		waterballCommunity.newMessage("5", "嗯嗯嗯！", new String[] {});
		
		// 時間流逝
		bot.increaseElapsedTime(3, TimeUnit.SECONDS);
		
		// 標記機器人的訊息
		waterballCommunity.newMessage("5", "還行啊", new String[] {"bot"});
		
		// 論壇貼文
		waterballCommunity.newPost("1", "2", "物件導向冷知識", "和大家分享一個冷知識，其實是先有「物件」才有「類別」喔！大家學會了嗎？", new String[] {});
		
		// 更多聊天
		waterballCommunity.newMessage("3", "哈哈哈，今天有點疲憊呢，不想上班", new String[] {"1", "2", "4"});
		waterballCommunity.newMessage("2", "你看看他和你說謝謝呢，上班加油", new String[] {"3", "bot"});
		
		// 第二個論壇貼文
		waterballCommunity.newPost("2", "4", "分享一個關於 單一職責原則 的笑話，每次講起來都還是覺得很好笑", "(1) 欸你這個類別這樣做太多事了吧，違反單一職責原則啊，每個類別只能有一個職責，只能做一件事。 (2) 這個類別，確實只做一件事，那就是實現需求！", new String[] {"1", "2", "3"});
		
		waterballCommunity.newMessage("2", "發了一個文，分享笑話，哈哈", new String[] {});

		// 比較期望檔案與實際日誌檔案
		assertLogFileEquals(expectedFilePath);
	}

}
