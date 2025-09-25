package botBase.event;

import java.util.List;

import fsm.IEvent;
import waterballCommunity.Tag;
import waterballCommunity.chatroom.Message;
import waterballCommunity.chatroom.WaterballCommunityNewMessageEvent;

public class NewMessageEvent extends IEvent {
	private final static String EVENT_NAME = "new-message";
	private Message message;

	public NewMessageEvent(WaterballCommunityNewMessageEvent event) {
		super(EVENT_NAME);
		this.message = event.getMessage();
	}
	
	public String getMessageAuthorId() {
		return message.getAuthorId();
	}

	public String getMessageContent() {
		return message.getContent();
	}
	
	public List<String> getMessageTags() {
		return message.getTags().stream().map(Tag::getId).toList();
	}

}
