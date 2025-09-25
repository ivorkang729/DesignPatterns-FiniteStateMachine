package botBase;

import botImpl.Bot;
import fsm.IGuard;
import waterballCommunity.WaterballCommunity;

public abstract class AbstractBotGuard implements IGuard {
	
	protected Bot bot;
	protected WaterballCommunity waterballCommunity;
	
	public AbstractBotGuard(Bot bot, WaterballCommunity waterballCommunity) {
		this.bot = bot;
		this.waterballCommunity = waterballCommunity;
	}

}
