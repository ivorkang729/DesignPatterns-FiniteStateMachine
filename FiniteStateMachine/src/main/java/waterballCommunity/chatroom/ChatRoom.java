package waterballCommunity.chatroom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import waterballCommunity.WaterballCommunity;

public class ChatRoom {
	private static final Logger logger = LogManager.getLogger(ChatRoom.class);
	
	private WaterballCommunity community;
	
	public ChatRoom(WaterballCommunity community) {
		this.community = community;
	}
	
	/** 有人在聊天室發送新訊息 */
	public void newMessage(Message message) {
		logger.info(message);
		if (!"bot".equals(message.getAuthorId())) {
			WaterballCommunityNewMessageEvent event = new WaterballCommunityNewMessageEvent(message);
			community.eventPublish(event);
		}
	}
}
