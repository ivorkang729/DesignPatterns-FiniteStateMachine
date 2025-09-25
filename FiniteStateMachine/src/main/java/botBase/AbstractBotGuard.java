package botBase;

import botImpl.Bot;
import fsm.Guard;
import waterballCommunity.WaterballCommunity;

public abstract class AbstractBotGuard implements Guard {
	
	protected Bot bot;
	protected WaterballCommunity waterballCommunity;
	
	public AbstractBotGuard(Bot bot, WaterballCommunity waterballCommunity) {
		this.bot = bot;
		this.waterballCommunity = waterballCommunity;
	}

}
