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


public class IntegrationTest07 extends BaseTest {

	String expectedFilePath = System.getProperty("user.dir") + "/src/test-data/integration-tests/recording.normal.out";
	
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
		
		// 準備錄音
		waterballCommunity.newMessage("3", "等等有演講，我來幫忙錄音一下", new String[] {});
		
		// 時間流逝
		bot.increaseElapsedTime(3, TimeUnit.SECONDS);
		
		// 開始錄音
		waterballCommunity.newMessage("3", "record", new String[] {"bot"});
		waterballCommunity.newMessage("2", "現在應該就正在錄音了？", new String[] {"1", "bot"});
		waterballCommunity.newMessage("1", "對，等演講開始！", new String[] {"2"});
		
		// 等待演講開始（40分鐘）
		bot.increaseElapsedTime(40, TimeUnit.MINUTES);
		
		// user4 開始廣播演講
		waterballCommunity.goBroadcasting(user4);
		waterballCommunity.newMessage("4", "我要開始演講囉！", new String[] {"1", "2", "3", "5", "bot"});
		waterballCommunity.newMessage("2", "期待很久", new String[] {"4"});
		
		// 演講內容
		waterballCommunity.speak(user4, "大家好，我是小華！");
		
		// 時間流逝
		bot.increaseElapsedTime(4, TimeUnit.SECONDS);
		
		waterballCommunity.speak(user4, "歡迎來到小華脫口秀");
		
		// 時間流逝
		bot.increaseElapsedTime(4, TimeUnit.SECONDS);
		
		waterballCommunity.speak(user4, "請問大家⋯⋯軟體工程師最常說的謊言有哪些？");
		
		// 時間流逝
		bot.increaseElapsedTime(3, TimeUnit.SECONDS);
		
		waterballCommunity.speak(user4, "是 TODO 註解！！");
		waterballCommunity.newMessage("3", "哈哈哈太白癡了", new String[] {"4"});
		waterballCommunity.speak(user4, "身為IT從業人員，你覺得有什麼工具大大提高了工作效率？");
		
		// 時間流逝
		bot.increaseElapsedTime(4, TimeUnit.SECONDS);
		
		waterballCommunity.speak(user4, "單身");
		
		// 時間流逝
		bot.increaseElapsedTime(4, TimeUnit.SECONDS);
		
		waterballCommunity.newMessage("2", "QQQ", new String[] {"4"});
		waterballCommunity.speak(user4, "感謝大家的支持，這就是今晚的小華脫口秀啦！");
		
		// 時間流逝
		bot.increaseElapsedTime(4, TimeUnit.SECONDS);
		
		// user4 停止廣播
		waterballCommunity.stopBroadcasting(user4);
		
		waterballCommunity.newMessage("1", "讚讚讚，好笑好笑！", new String[] {"4"});
		waterballCommunity.newMessage("4", "謝謝！", new String[] {"1"});
		
		// user3 開始廣播
		waterballCommunity.goBroadcasting(user3);
		waterballCommunity.speak(user3, "我再來補一個笑話！");
		waterballCommunity.speak(user3, "這個世界上有 10 種人啊");
		waterballCommunity.speak(user3, "懂二進制的人⋯⋯");
		waterballCommunity.speak(user3, "和不懂二進制的人！！");
		waterballCommunity.newMessage("4", "唉唷讚喔，哈哈哈", new String[] {"3"});
		
		// user3 停止廣播
		waterballCommunity.stopBroadcasting(user3);
		
		// 嘗試停止錄音（非錄音者，應該無效）
		waterballCommunity.newMessage("2", "stop-recording", new String[] {"bot"});
		waterballCommunity.newMessage("3", "只有原本下達 record 指令的人可以停止錄音", new String[] {"2"});
		
		// 正確停止錄音（錄音者）
		waterballCommunity.newMessage("3", "stop-recording", new String[] {"bot"});
		waterballCommunity.newMessage("3", "謝謝！", new String[] {"4"});

		// 比較期望檔案與實際日誌檔案
		assertLogFileEquals(expectedFilePath);
	}

}
