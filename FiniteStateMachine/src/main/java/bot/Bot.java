package bot;

import java.util.List;

import bot.event.GoBroadcastingEvent;
import bot.event.LoginEvent;
import bot.event.LogoutEvent;
import bot.event.NewMessageEvent;
import bot.event.NewPostEvent;
import bot.event.SpeakEvent;
import bot.event.StopBroadcastingEvent;
import community.Member;
import community.WaterballCommunity;
import community.WaterballCommunityEvent;
import community.WaterballCommunityEventListener;
import community.WaterballCommunityLoginEvent;
import community.WaterballCommunityLogoutEvent;
import community.broadcast.WaterballCommunityGoBroadcastingEvent;
import community.broadcast.WaterballCommunitySpeakEvent;
import community.broadcast.WaterballCommunityStopBroadcastingEvent;
import community.chatroom.Message;
import community.chatroom.WaterballCommunityNewMessageEvent;
import community.forum.Comment;
import community.forum.WaterballCommunityNewPostEvent;
import fsm.Context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class Bot implements WaterballCommunityEventListener {
	private static final Logger logger = LogManager.getLogger(Bot.class);
	
	public static final String BOT_TAG = "bot";
	
	private Context context;
	private WaterballCommunity waterballCommunity;
	private String startedTime;
	private int commandQuota;
	private Member botMember;
	
	public Bot(Context context, WaterballCommunity waterballCommunity, String startedTime, int commandQuota) {
		this.context = context;
		this.waterballCommunity = waterballCommunity;
		this.startedTime = startedTime;
		this.commandQuota = commandQuota;
	}
	
	/* 機器人自己登入 Waterball Community */
	public void loginWaterballCommunity() {
		this.botMember = waterballCommunity.login(BOT_TAG, false);
	}
	
	/* 機器人傳送訊息到聊天室 */
	public void sendNewMessageToChatRoom(String content, List<String> tagIDs) {
		Message message = new Message(botMember, content);
		for (String tagID : tagIDs) {
			message.addTag(tagID);
		}
		waterballCommunity.newMessage(message);
	}

	/** 機器人發送新留言到論壇 */
	public void sendNewCommentToForum(String content, List<String> tagIDs, String refPostId) {
		Comment comment = new Comment(botMember.getId(), refPostId, content);
		comment.addTagsByIds(tagIDs);
		waterballCommunity.newComment(comment);
	}
	
	// 機器人聆聽 WaterballCommunityEvent
	@Override
	public void onEvent(WaterballCommunityEvent waterballEvent) {

		if (waterballEvent instanceof WaterballCommunityLoginEvent) {
			WaterballCommunityLoginEvent e = (WaterballCommunityLoginEvent) waterballEvent;
			logger.debug("Member ID: " + e.getMember().getId());
			logger.debug("Role: " + e.getMember().getRole());

			// 對FSM發出登入事件
			LoginEvent loginEvent = new LoginEvent(e);
			context.sendEvent(loginEvent);
		}
		else if (waterballEvent instanceof WaterballCommunityLogoutEvent) {
			WaterballCommunityLogoutEvent e = (WaterballCommunityLogoutEvent) waterballEvent;
			logger.debug("Member ID: " + e.getMember().getId());

			// 對FSM發出登出事件
			LogoutEvent logoutEvent = new LogoutEvent(e);
			context.sendEvent(logoutEvent);
		}
		else if (waterballEvent instanceof  WaterballCommunityNewMessageEvent) {
			WaterballCommunityNewMessageEvent e = (WaterballCommunityNewMessageEvent) waterballEvent;
			
			// 機器人先依據當前狀態處理該聊天室新訊息
			NewMessageEvent newMessageEvent = new NewMessageEvent(e);
			BotState currentBotState = (BotState) context.getCurrentState();
			currentBotState.onNewMessage(newMessageEvent);
			
			// 然後再執行指令 <-- 對FSM發出聊天室新訊息事件
			context.sendEvent(newMessageEvent);
		}
		else if (waterballEvent instanceof WaterballCommunityNewPostEvent) {
			WaterballCommunityNewPostEvent e = (WaterballCommunityNewPostEvent) waterballEvent;

			// 機器人依狀態在貼文底下留言
			NewPostEvent newPostEvent = new NewPostEvent(e);
			BotState currentBotState = (BotState) context.getCurrentState();
			currentBotState.onNewPost(newPostEvent);
		}
		else if (waterballEvent instanceof WaterballCommunityGoBroadcastingEvent) {
			WaterballCommunityGoBroadcastingEvent e = (WaterballCommunityGoBroadcastingEvent) waterballEvent;
			
			// 對FSM發出開始廣播事件
			GoBroadcastingEvent goBroadcastingEvent = new GoBroadcastingEvent(e);
			context.sendEvent(goBroadcastingEvent);
		}
		else if (waterballEvent instanceof WaterballCommunitySpeakEvent) {
			WaterballCommunitySpeakEvent e = (WaterballCommunitySpeakEvent) waterballEvent;
	
			// 機器人依狀態錄音
			SpeakEvent speakEvent = new SpeakEvent(e);
			BotState currentBotState = (BotState) context.getCurrentState();
			currentBotState.onSpeak(speakEvent);

		}
		else if (waterballEvent instanceof WaterballCommunityStopBroadcastingEvent) {
			WaterballCommunityStopBroadcastingEvent e = (WaterballCommunityStopBroadcastingEvent) waterballEvent;
	
			// 對FSM發出停止廣播事件
			StopBroadcastingEvent stopBroadcastingEvent = new StopBroadcastingEvent(e);
			context.sendEvent(stopBroadcastingEvent);
		}
		else {
			logger.warn("Unknown event type.");
		}
	}

	/** 檢查機器人是否還有足夠的指令配額 */
	public boolean isCommandQuotaEnough(int cost) {
		return commandQuota >= cost;
	}
	public void deductCommandQuota(int cost) {
		if (cost > commandQuota) {
			throw new IllegalArgumentException("Not enough command quota.");
		}
		commandQuota -= cost;
	}


	public WaterballCommunity getWaterballCommunity() {
		return waterballCommunity;
	}
}
