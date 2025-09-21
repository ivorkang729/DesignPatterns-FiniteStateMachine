package botImpl.state;

import botBase.BaseBotState;
import botImpl.Bot;
import fsm.EntryAction;
import fsm.ExitAction;

public class WaitingState extends BaseBotState {
	
	public WaitingState(String stateName, Bot bot, EntryAction entryStateAction, ExitAction exitStateAction) {
		super(bot, stateName, entryStateAction, exitStateAction);
	}
	
	
}