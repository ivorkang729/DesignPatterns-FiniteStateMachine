package main;

import bot.Bot;
import bot.BotFactory;
import community.Member;
import community.Role;
import community.WaterballCommunity;
import community.chatroom.Message;
import community.forum.Post;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
	private static final Logger logger = LogManager.getLogger(Main.class);
	
	public static void main(String[] args) {
		
		// WaterballCommunity 建立
		WaterballCommunity waterballCommunity = new WaterballCommunity();
		
		// Bot 建立
		Bot bot = BotFactory.createBot(waterballCommunity, "2025-08-24 10:00:00", 100);
		
		// Bot 監聽 WaterballCommunity 事件
		waterballCommunity.registerEventListener(bot);	

		// 模擬 WaterballCommunity 事件
		waterballCommunity.login("1", false);
		waterballCommunity.login("2", false);
		waterballCommunity.login("3", false);
		waterballCommunity.login("4", false);


		Post post = new Post("4", "分享一個關於 單一職責原則 的笑話，每次講起來都還是覺得很好笑", "(1) 欸你這個類別這樣做太多事了吧，違反單一職責原則啊，每個類別只能有一個職責，只能做一件事。 (2) 這個類別，確實只做一件事，那就是實現需求！");
		post.addTags("1", "3");
		waterballCommunity.newPost(post);

		waterballCommunity.login("5", false);
		waterballCommunity.login("6", false);
		waterballCommunity.login("7", true); // 這個人是管理員
		waterballCommunity.login("8", false);
		
		Message msg = new Message("1", "大家早安，今天我第一天上班呢");
//		waterballCommunity.newMessage(msg);
//		
//		msg = new Message("4", "祝大家今天事事順利");
//		msg.addTag("1");
//		waterballCommunity.newMessage(msg);
//		
//		msg = new Message("5", "嗯嗯嗯！");
//		waterballCommunity.newMessage(msg);
//
//		msg = new Message("5", "還行啊");
//		msg.addTag("bot");
//		waterballCommunity.newMessage(msg);
//		
//		waterballCommunity.login("9", false);
//
//		post = new Post("8", "分享一個關於 單一職責原則 的笑話，每次講起來都還是覺得很好笑", "(1) 欸你這個類別這樣做太多事了吧，違反單一職責原則啊，每個類別只能有一個職責，只能做一件事。 (2) 這個類別，確實只做一件事，那就是實現需求！");
//		post.addTags("1", "3");
//		waterballCommunity.newPost(post);
//		
//		
//		msg = new Message("7", "king");
//		msg.addTag("bot");
//		waterballCommunity.newMessage(msg);

//		waterballCommunity.logout("8");
//		waterballCommunity.logout("9");
		
		Member m3 = new Member("3", Role.MEMBER);
		Member m4 = new Member("4", Role.MEMBER);
		
		msg = new Message("3", "record");
		msg.addTag("bot");
		waterballCommunity.newMessage(msg);
		
		waterballCommunity.goBroadcasting(m4); // 4 開始廣播
		waterballCommunity.speak(m4, "大家好，我是錄音者");
		waterballCommunity.speak(m4, "歡迎來到小華脫口秀");
		waterballCommunity.speak(m4, "請問大家⋯⋯軟體工程師最常說的謊言有哪些？");
		waterballCommunity.speak(m4, "感謝大家的支持，這就是今晚的小華脫口秀啦！");
		waterballCommunity.stopBroadcasting(m4);
		
		waterballCommunity.goBroadcasting(m3); // 3 開始廣播
		waterballCommunity.speak(m3, "我再來補一個笑話！");
		waterballCommunity.speak(m3, "這個世界上有 10 種人啊");
		waterballCommunity.speak(m3, "懂二進制的人⋯⋯");
		waterballCommunity.speak(m3, "和不懂二進制的人！！");
		
		msg = new Message("3", "stop-recording");
		msg.addTag("bot");
		waterballCommunity.newMessage(msg);
		
		logger.debug("Recorder--------- ");
		
	}

	

}
