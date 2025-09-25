package bot.state.normal;

import bot.BaseBotState;
import bot.Bot;
import fsm.IEntryAction;
import fsm.IExitAction;

public class NormalState extends BaseBotState {

	public NormalState(String stateName, Bot bot, IEntryAction entryStateAction, IExitAction exitStateAction) {
		super(bot, stateName, entryStateAction, exitStateAction);
	}

}
