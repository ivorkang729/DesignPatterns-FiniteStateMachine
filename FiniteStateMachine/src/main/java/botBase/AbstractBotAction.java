package botBase;

import botImpl.Bot;
import fsm.Action;
import waterballCommunity.WaterballCommunity;

public abstract class AbstractBotAction implements Action {
	
	protected Bot bot;
	protected WaterballCommunity waterballCommunity;
	
	public AbstractBotAction(Bot bot, WaterballCommunity waterballCommunity) {
		this.bot = bot;
		this.waterballCommunity = waterballCommunity;
	}

}
