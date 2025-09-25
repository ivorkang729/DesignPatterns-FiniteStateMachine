package bot.state.thanksForJoining;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import bot.BaseBotState;
import bot.Bot;
import bot.event.TimeoutEvent;
import bot.state.knowledgeKing.KnowledgeKingState;
import fsm.IEntryAction;
import fsm.IExitAction;
import fsm.FSMContext;

public class ThanksForJoiningState extends BaseBotState {

	// 計算自進入本狀態，已經過了幾秒
	protected int elapsedSeconds = 0;

	private FSMContext context;
	private KnowledgeKingState knowledgeKingState;

	public ThanksForJoiningState(String stateName, KnowledgeKingState knowledgeKingState, FSMContext context, Bot bot, IEntryAction entryStateAction, IExitAction exitStateAction) {
		super(bot, stateName, entryStateAction, exitStateAction);
		this.knowledgeKingState = knowledgeKingState;
		this.context = context;
	}

	@Override
	public void initState() {
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
