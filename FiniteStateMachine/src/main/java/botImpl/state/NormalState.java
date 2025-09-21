package botImpl.state;

import botBase.BotBaseState;
import botImpl.Bot;
import fsm.EntryAction;
import fsm.ExitAction;

public class NormalState extends BotBaseState {

	public NormalState(String stateName, Bot bot, EntryAction entryStateAction, ExitAction exitStateAction) {
		super(bot, stateName, entryStateAction, exitStateAction);
	}

}
