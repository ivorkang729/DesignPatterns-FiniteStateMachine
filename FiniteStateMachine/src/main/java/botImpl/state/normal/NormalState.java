package botImpl.state.normal;

import botBase.BaseBotState;
import botImpl.Bot;
import fsm.IEntryAction;
import fsm.IExitAction;

public class NormalState extends BaseBotState {

	public NormalState(String stateName, Bot bot, IEntryAction entryStateAction, IExitAction exitStateAction) {
		super(bot, stateName, entryStateAction, exitStateAction);
	}

}
