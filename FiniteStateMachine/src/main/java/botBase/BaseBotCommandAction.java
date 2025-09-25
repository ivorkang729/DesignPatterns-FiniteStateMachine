package botBase;

import botImpl.Bot;
import fsm.IEvent;
import fsm.FSMContext;
import fsm.IState;
import waterballCommunity.WaterballCommunity;

public class BaseBotCommandAction extends AbstractBotAction {
	
	private int quotaCost;
	
	public BaseBotCommandAction(Bot bot, WaterballCommunity waterballCommunity, int quotaCost) {
		super(bot, waterballCommunity);
		this.quotaCost = quotaCost;
	}
	
	@Override
	public void execute(FSMContext context, IState fromState, IEvent event) {
		bot.deductCommandQuota(quotaCost);	// 扣除額度
		extendAction(context, fromState, event);
	}
	
	protected void extendAction(FSMContext context, IState fromState, IEvent event) {
		// default do nothing
	}
	
}