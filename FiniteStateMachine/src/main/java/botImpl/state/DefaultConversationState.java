package botImpl.state;

import java.util.Arrays;

import botBase.BotBaseState;
import botBase.event.NewMessageEvent;
import botBase.event.NewPostEvent;
import botImpl.Bot;
import fsm.EntryAction;
import fsm.ExitAction;

public class DefaultConversationState extends BotBaseState {
	
	private final String[] replyMessages = {
		"good to hear",
		"thank you",
		"How are you"
	};
	private int replyIndex = 0;

	public DefaultConversationState(String stateName, Bot bot, EntryAction entryStateAction, ExitAction exitStateAction) {
		super(bot, stateName, entryStateAction, exitStateAction);
	}
	
	@Override
	public void initState() {
		replyIndex = 0;
	}
	
	@Override
	public void onNewMessage(NewMessageEvent event) {
		bot.sendNewMessageToChatRoom(
			replyMessages[replyIndex++ % replyMessages.length],
			Arrays.asList(event.getMessageAuthorId())
		);
	}

	@Override
	public void onNewPost(NewPostEvent event) {
		bot.sendNewCommentToForum(
			"Nice post",
			Arrays.asList(event.getPostAuthorId()),
			event.getPostId()
		);
	}

}