package bot.state;

import bot.Bot;
import bot.BotState;
import fsm.EntryAction;
import fsm.ExitAction;

public class KnowledgeKingState extends BotState {
	
	public KnowledgeKingState(Bot bot, EntryAction entryStateAction, ExitAction exitStateAction) {
		super(bot, KnowledgeKingState.class.getSimpleName(), entryStateAction, exitStateAction);
	}
	
}