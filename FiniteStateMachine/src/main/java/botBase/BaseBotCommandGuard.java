package botBase;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.Strings;

import botImpl.Bot;
import fsm.Event;
import fsm.FSMContext;
import fsm.State;
import fsm.base.BaseGuard;
import waterballCommunity.Role;
import waterballCommunity.WaterballCommunity;

public abstract class BaseBotCommandGuard extends BaseGuard {
	
	protected Bot bot;
	protected WaterballCommunity waterballCommunity;
	
	public BaseBotCommandGuard(Bot bot, WaterballCommunity waterballCommunity) {
		this.bot = bot;
		this.waterballCommunity = waterballCommunity;
	}
	
	@Override
	public boolean evaluate(FSMContext context, State fromState, Event event) {
		botBase.event.NewMessageEvent newMsgEvent = (botBase.event.NewMessageEvent)event;
		
		return Strings.CS.equals(getCommand(), newMsgEvent.getMessageContent())		// 指令
				&& newMsgEvent.getMessageTags().stream().anyMatch(tag -> tag.equals(Bot.BOT_TAG)
				&& getAllowedRoles().contains(waterballCommunity.getMemberById(newMsgEvent.getMessageAuthorId()).getRole())	// 權限
				&& bot.isCommandQuotaEnough(getCost())) ; 	// 額度需足夠
	}
	
	// 指令
	protected abstract String getCommand();
	// 耗費額度
	protected abstract int getCost();
	// 允許使用此指令的身分
	protected abstract List<Role> getAllowedRoles();
	
}