package bot.state.defaultConversation;

import java.util.Arrays;

import bot.BaseBotState;
import bot.Bot;
import bot.event.NewMessageEvent;
import bot.event.NewPostEvent;
import fsm.IEntryAction;
import fsm.IExitAction;

public class DefaultConversationState extends BaseBotState {
	
	private final String[] replyMessages = {
		"good to hear",
		"thank you",
		"How are you"
	};
	private int replyIndex = 0;

	public DefaultConversationState(String stateName, Bot bot, IEntryAction entryStateAction, IExitAction exitStateAction) {
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