package bot.state.waiting;

import bot.BaseBotState;
import bot.Bot;
import fsm.IEntryAction;
import fsm.IExitAction;

public class WaitingState extends BaseBotState {
	
	public WaitingState(String stateName, Bot bot, IEntryAction entryStateAction, IExitAction exitStateAction) {
		super(bot, stateName, entryStateAction, exitStateAction);
	}
	
	
}