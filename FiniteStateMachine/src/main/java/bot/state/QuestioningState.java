package bot.state;

import java.util.Arrays;

import bot.Bot;
import bot.BotState;
import bot.event.NewMessageEvent;
import bot.event.NewPostEvent;
import community.Member;
import fsm.EntryAction;
import fsm.ExitAction;

public class QuestioningState extends BotState {
	private final String[] replyMessages = {
			"Hi hi😁",
			"I like your idea!"
		};
	private int replyIndex = 0;

	public QuestioningState(Bot bot, EntryAction entryStateAction, ExitAction exitStateAction) {
		super(bot, QuestioningState.class.getSimpleName(), entryStateAction, exitStateAction);
	}

	public void resetReplyIndex() {
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
