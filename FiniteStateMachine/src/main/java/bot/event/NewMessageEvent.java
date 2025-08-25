package bot.event;

import java.util.List;

import community.Tag;
import community.chatroom.Message;
import community.chatroom.WaterballCommunityNewMessageEvent;
import fsm.Event;

public class NewMessageEvent extends Event {
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
