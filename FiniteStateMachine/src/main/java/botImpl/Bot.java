package botImpl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import botBase.BaseBotState;
import botBase.event.GoBroadcastingEvent;
import botBase.event.LoginEvent;
import botBase.event.LogoutEvent;
import botBase.event.NewMessageEvent;
import botBase.event.NewPostEvent;
import botBase.event.SpeakEvent;
import botBase.event.StopBroadcastingEvent;
import fsm.FSMContext;
import waterballCommunity.Member;
import waterballCommunity.WaterballCommunity;
import waterballCommunity.WaterballCommunityEvent;
import waterballCommunity.WaterballCommunityEventListener;
import waterballCommunity.WaterballCommunityLoginEvent;
import waterballCommunity.WaterballCommunityLogoutEvent;
import waterballCommunity.broadcast.WaterballCommunityGoBroadcastingEvent;
import waterballCommunity.broadcast.WaterballCommunitySpeakEvent;
import waterballCommunity.broadcast.WaterballCommunityStopBroadcastingEvent;
import waterballCommunity.chatroom.Message;
import waterballCommunity.chatroom.WaterballCommunityNewMessageEvent;
import waterballCommunity.forum.Comment;
import waterballCommunity.forum.WaterballCommunityNewPostEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class Bot implements WaterballCommunityEventListener {
	private static final Logger logger = LogManager.getLogger(Bot.class);
	
	public static final String BOT_TAG = "botImpl";
	
	private FSMContext context;
	private WaterballCommunity waterballCommunity;
	private String startTime;
	private int commandQuota;
	private Member botMember;
	
	public Bot(FSMContext context, WaterballCommunity waterballCommunity, String startTime, int commandQuota) {
		this.context = context;
		this.waterballCommunity = waterballCommunity;
		this.startTime = startTime;
		this.commandQuota = commandQuota;
	}

	// 機器人聆聽並響應 WaterballCommunityEvent
	@Override
	public void onEvent(WaterballCommunityEvent waterballEvent) {

		if (waterballEvent instanceof WaterballCommunityLoginEvent) {
			WaterballCommunityLoginEvent e = (WaterballCommunityLoginEvent) waterballEvent;
			logger.debug("Member ID: " + e.getMember().getId());
			logger.debug("Role: " + e.getMember().getRole());

			// 對FSM發出登入事件
			LoginEvent loginEvent = new LoginEvent(e);
			context.sendEventToCurrentState(loginEvent);
		}
		else if (waterballEvent instanceof WaterballCommunityLogoutEvent) {
			WaterballCommunityLogoutEvent e = (WaterballCommunityLogoutEvent) waterballEvent;
			logger.debug("Member ID: " + e.getMember().getId());

			// 對FSM發出登出事件
			LogoutEvent logoutEvent = new LogoutEvent(e);
			context.sendEventToCurrentState(logoutEvent);
		}
		else if (waterballEvent instanceof  WaterballCommunityNewMessageEvent) {
			WaterballCommunityNewMessageEvent e = (WaterballCommunityNewMessageEvent) waterballEvent;
			
			// 機器人先依據當前狀態處理該聊天室新訊息
			// 當前狀態 <-- 從FSMContext取得，然後轉型成第二層認識的 BaseBotState
			NewMessageEvent newMessageEvent = new NewMessageEvent(e);
			BaseBotState currentBotState = (BaseBotState) context.getCurrentState();
			currentBotState.onNewMessage(newMessageEvent);
			
			// 然後再執行指令 <-- 對FSM發出聊天室新訊息事件
			context.sendEventToCurrentState(newMessageEvent);
		}
		else if (waterballEvent instanceof WaterballCommunityNewPostEvent) {
			WaterballCommunityNewPostEvent e = (WaterballCommunityNewPostEvent) waterballEvent;

			// 機器人依當前狀態在貼文底下留言
			NewPostEvent newPostEvent = new NewPostEvent(e);
			BaseBotState currentBotState = (BaseBotState) context.getCurrentState();
			currentBotState.onNewPost(newPostEvent);
		}
		else if (waterballEvent instanceof WaterballCommunityGoBroadcastingEvent) {
			WaterballCommunityGoBroadcastingEvent e = (WaterballCommunityGoBroadcastingEvent) waterballEvent;
			
			// 對FSM發出開始廣播事件
			GoBroadcastingEvent goBroadcastingEvent = new GoBroadcastingEvent(e);
			context.sendEventToCurrentState(goBroadcastingEvent);
		}
		else if (waterballEvent instanceof WaterballCommunitySpeakEvent) {
			WaterballCommunitySpeakEvent e = (WaterballCommunitySpeakEvent) waterballEvent;
	
			// 機器人依當前狀態錄音
			SpeakEvent speakEvent = new SpeakEvent(e);
			BaseBotState currentBotState = (BaseBotState) context.getCurrentState();
			currentBotState.onSpeak(speakEvent);
		}
		else if (waterballEvent instanceof WaterballCommunityStopBroadcastingEvent) {
			WaterballCommunityStopBroadcastingEvent e = (WaterballCommunityStopBroadcastingEvent) waterballEvent;
	
			// 對FSM發出停止廣播事件
			StopBroadcastingEvent stopBroadcastingEvent = new StopBroadcastingEvent(e);
			context.sendEventToCurrentState(stopBroadcastingEvent);
		}
		else {
			logger.warn("Unknown event type.");
		}
	}
	
	/* 機器人登入 Waterball Community */
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

	/** 機器人發送廣播 */
	public void sendBroadcasting(String content) {
		waterballCommunity.goBroadcasting(botMember);
		waterballCommunity.speak(botMember, content);
		waterballCommunity.stopBroadcasting(botMember);
	}

	/** 檢查機器人是否還有足夠的指令配額 */
	public boolean isCommandQuotaEnough(int cost) {
		return commandQuota >= cost;
	}

	/** 扣減指令額度 */
	public void deductCommandQuota(int cost) {
		if (cost > commandQuota) {
			throw new IllegalArgumentException("Not enough command quota.");
		}
		commandQuota -= cost;
	}

	/** 增加經過時間 */
	public void increaseElapsedTime(int time, TimeUnit unit) {
		printTimeElapsedMessage(time, unit);
		 // 增加經過時間給當前狀態
		BaseBotState currentBotState = (BaseBotState) context.getCurrentState();
		currentBotState.increaseElapsedTime(time, unit);
	}
	
	private void printTimeElapsedMessage(int time, TimeUnit unit) {
		StringBuilder sb = new StringBuilder("🕑 ").append(time);
		if (unit == TimeUnit.SECONDS) {
			sb.append(" seconds");
		} 
		else if (unit == TimeUnit.MINUTES) {
			sb.append(" minutes");
		} 
		else if (unit == TimeUnit.HOURS) {
			sb.append(" hours");	
		} 
		sb.append(" elapsed...");
		logger.info(sb.toString());
	}

	public WaterballCommunity getWaterballCommunity() {
		return waterballCommunity;
	}
}
