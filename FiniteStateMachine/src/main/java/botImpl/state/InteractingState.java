package botImpl.state;

import java.util.Arrays;

import botBase.BaseBotState;
import botBase.event.NewMessageEvent;
import botBase.event.NewPostEvent;
import botImpl.Bot;
import fsm.EntryAction;
import fsm.ExitAction;
import waterballCommunity.Member;

public class InteractingState extends BaseBotState {
	private final String[] replyMessages = {
			"Hi hi😁",
			"I like your idea!"
		};
	private int replyIndex = 0;

	public InteractingState(String stateName, Bot bot, EntryAction entryStateAction, ExitAction exitStateAction) {
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
			"How do you guys think about it?",
			bot.getWaterballCommunity().getLoggedInMembers().stream().map(Member::getId).toList(),
			event.getPostId()
		);
	}

}
