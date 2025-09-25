package botBase;

import botImpl.Bot;
import fsm.base.BaseGuard;
import waterballCommunity.WaterballCommunity;

public class BaseBotGuard extends BaseGuard {
	
	protected Bot bot;
	protected WaterballCommunity waterballCommunity;
	
	public BaseBotGuard(Bot bot, WaterballCommunity waterballCommunity) {
		this.bot = bot;
		this.waterballCommunity = waterballCommunity;
	}

}
