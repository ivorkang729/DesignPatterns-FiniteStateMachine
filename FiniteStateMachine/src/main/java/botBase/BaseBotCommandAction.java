package botBase;

import botImpl.Bot;
import fsm.Event;
import fsm.FSMContext;
import fsm.State;
import waterballCommunity.WaterballCommunity;

public class BaseBotCommandAction extends AbstractBotAction {
	
	private int quotaCost;
	
	public BaseBotCommandAction(Bot bot, WaterballCommunity waterballCommunity, int quotaCost) {
		super(bot, waterballCommunity);
		this.quotaCost = quotaCost;
	}
	
	@Override
	public void execute(FSMContext context, State fromState, Event event) {
		bot.deductCommandQuota(quotaCost);	// 扣除額度
		extendAction(context, fromState, event);
	}
	
	protected void extendAction(FSMContext context, State fromState, Event event) {
		// default do nothing
	}
	
}