package bot.state;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import bot.Bot;
import bot.BotState;
import bot.event.TimeoutEvent;
import fsm.EntryAction;
import fsm.ExitAction;
import fsm.FSMContext;

public class ThanksForJoiningState extends BotState {

	// 計算自進入本狀態，已經過了幾秒
	protected int elapsedSeconds = 0;

	private FSMContext context;
	private KnowledgeKingState knowledgeKingState;

	public ThanksForJoiningState(String stateName, KnowledgeKingState knowledgeKingState, FSMContext context, Bot bot, EntryAction entryStateAction, ExitAction exitStateAction) {
		super(bot, stateName, entryStateAction, exitStateAction);
		this.knowledgeKingState = knowledgeKingState;
		this.context = context;
	}

	public void reset() {
		elapsedSeconds = 0;
	}

	public void winnerAnnouncement() {
		String gameResultMessage = getGameResultMessage();
		if (!bot.getWaterballCommunity().isSomeoneBroadcasting()) {
			bot.sendBroadcasting(gameResultMessage);
		}
		else{
			bot.sendNewMessageToChatRoom(gameResultMessage, new ArrayList<>());
		}
	}

	public String getGameResultMessage() {
		String winnerId = knowledgeKingState.getWinnerId();
		return (winnerId == null) ? "Tie!" : "The winner is " + winnerId;
	}

	@Override
	public void increaseElapsedTime(int time, TimeUnit unit) {
		if (unit == TimeUnit.SECONDS) {
			elapsedSeconds += time;
		} 
		else if (unit == TimeUnit.MINUTES) {
			elapsedSeconds += time * 60;
		} 
		else if (unit == TimeUnit.HOURS) {
			elapsedSeconds += time * 60 * 60;
		} 
		else {
			throw new IllegalArgumentException("Invalid time unit.");
		}

		if (isTimeOut()) {
			context.sendEventToCurrentState(new TimeoutEvent());
		}
	}

	private boolean isTimeOut() {
		return elapsedSeconds >= 20;
	}
	
}
