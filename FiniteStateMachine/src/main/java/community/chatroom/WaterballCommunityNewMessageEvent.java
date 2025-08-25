package community.chatroom;

import community.WaterballCommunityEvent;

public class WaterballCommunityNewMessageEvent extends WaterballCommunityEvent {
	private final static String EVENT_NAME = "waterball-event-new-message";
	private Message message;
	
	public WaterballCommunityNewMessageEvent(Message message) {
		super(EVENT_NAME);
		this.message = message;
	}
	
	public Message getMessage() {
		return message;
	}
	
}
