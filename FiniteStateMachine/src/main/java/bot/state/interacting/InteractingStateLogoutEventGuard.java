package bot.state.interacting;

import bot.AbstractBotGuard;
import bot.Bot;
import fsm.FSMContext;
import fsm.FSMEvent;
import fsm.IState;
import waterballCommunity.WaterballCommunity;

public class InteractingStateLogoutEventGuard extends AbstractBotGuard {
	
	public InteractingStateLogoutEventGuard(Bot bot, WaterballCommunity waterballCommunity) {
		super(bot, waterballCommunity);
	}

	@Override
	public boolean evaluate(FSMContext context, IState fromState, FSMEvent event) {
		return waterballCommunity.getLoggedInMemberCount() < 10;
	}

}
