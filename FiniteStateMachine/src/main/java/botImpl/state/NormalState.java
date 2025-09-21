package botImpl.state;

import botBase.BaseBotState;
import botImpl.Bot;
import fsm.EntryAction;
import fsm.ExitAction;

public class NormalState extends BaseBotState {

	public NormalState(String stateName, Bot bot, EntryAction entryStateAction, ExitAction exitStateAction) {
		super(bot, stateName, entryStateAction, exitStateAction);
	}

}
