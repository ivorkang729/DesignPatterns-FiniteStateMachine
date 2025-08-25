package bot.state;

import bot.Bot;
import bot.BotState;
import fsm.EntryAction;
import fsm.ExitAction;

public class WaitingState extends BotState {
	
	public WaitingState(Bot bot, EntryAction entryStateAction, ExitAction exitStateAction) {
		super(bot, WaitingState.class.getSimpleName(), entryStateAction, exitStateAction);
	}
	
	
}