package botBase;

import java.util.List;

import org.apache.commons.lang3.Strings;

import botImpl.Bot;
import fsm.Event;
import fsm.FSMContext;
import fsm.State;
import fsm.base.BaseAction;
import waterballCommunity.Role;
import waterballCommunity.WaterballCommunity;

public abstract class BaseBotCommandAction extends BaseAction {
	
	protected Bot bot;
	protected WaterballCommunity waterballCommunity;
	
	public BaseBotCommandAction(Bot bot, WaterballCommunity waterballCommunity) {
		this.bot = bot;
		this.waterballCommunity = waterballCommunity;
	}
	
	@Override
	public void execute(FSMContext context, State fromState, Event event) {
		bot.deductCommandQuota(getCost());	// 扣除額度
	}
	
	// 耗費額度
	protected abstract int getCost();
	protected abstract void performAction(FSMContext context, State fromState, Event event);
	
}