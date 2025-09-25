package botBase;

import botImpl.Bot;
import fsm.IAction;
import waterballCommunity.WaterballCommunity;

public abstract class AbstractBotAction implements IAction {
	
	protected Bot bot;
	protected WaterballCommunity waterballCommunity;
	
	public AbstractBotAction(Bot bot, WaterballCommunity waterballCommunity) {
		this.bot = bot;
		this.waterballCommunity = waterballCommunity;
	}

}
