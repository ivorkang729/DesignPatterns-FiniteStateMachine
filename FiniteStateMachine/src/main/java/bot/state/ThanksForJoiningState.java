package bot.state;

import java.util.ArrayList;

import bot.Bot;
import bot.BotState;
import fsm.EntryAction;
import fsm.ExitAction;

public class ThanksForJoiningState extends BotState {

	private KnowledgeKingState knowledgeKingState;

	public ThanksForJoiningState(Bot bot, EntryAction entryStateAction, ExitAction exitStateAction) {
		super(bot, QuestioningState.class.getSimpleName(), entryStateAction, exitStateAction);
	}

	public void setKnowledgeKingState(KnowledgeKingState knowledgeKingState) {
		this.knowledgeKingState = knowledgeKingState;
	}

	public void winnerAnnouncement(){
		String gameResultMessage = getGameResultMessage();
		if (!bot.getWaterballCommunity().isSomeoneBroadcasting()) {
			bot.sendBroadcasting(gameResultMessage);
		}
		else{
			bot.sendNewMessageToChatRoom(gameResultMessage, new ArrayList<>());
		}
	}

	public String getGameResultMessage(){
		String winnerId = knowledgeKingState.getWinnerId();
		return (winnerId == null) ? "Tie!" : "The winner is " + winnerId;
	}
	
}
