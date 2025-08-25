package bot.state;

import bot.Bot;
import bot.BotState;
import fsm.EntryAction;
import fsm.ExitAction;

public class NormalState extends BotState {

	public NormalState(Bot bot, EntryAction entryStateAction, ExitAction exitStateAction) {
		super(bot, NormalState.class.getSimpleName(), entryStateAction, exitStateAction);
	}

}
