package bot.state.defaultConversation;

import bot.AbstractBotGuard;
import bot.Bot;
import fsm.FSMContext;
import fsm.FSMEvent;
import fsm.IState;
import waterballCommunity.WaterballCommunity;

public class DefaultConversationStateLoginEventGuard extends AbstractBotGuard {
	
	public DefaultConversationStateLoginEventGuard(Bot bot, WaterballCommunity waterballCommunity) {
		super(bot, waterballCommunity);
	}

	@Override
	public boolean evaluate(FSMContext context, IState fromState, FSMEvent event) {
		return waterballCommunity.getLoggedInMemberCount() >= 10;
	}

}
