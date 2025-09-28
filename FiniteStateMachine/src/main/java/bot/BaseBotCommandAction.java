package bot;

import fsm.FSMEvent;
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
	public void execute(FSMContext context, IState fromState, FSMEvent event) {
		bot.deductCommandQuota(quotaCost);	// 扣減額度
		extendAction(context, fromState, event);
	}
	
	protected void extendAction(FSMContext context, IState fromState, FSMEvent event) {
		// default do nothing
	}
	
}