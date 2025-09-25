package botBase;

import java.util.List;

import org.apache.commons.lang3.Strings;

import botImpl.Bot;
import fsm.Event;
import fsm.FSMContext;
import fsm.State;
import waterballCommunity.Role;
import waterballCommunity.WaterballCommunity;

public class BaseBotCommandGuard extends BaseBotGuard {
	
	private String command;
	private int quotaCost;
	private List<Role> allowedRoles;
	
	public BaseBotCommandGuard(Bot bot, WaterballCommunity waterballCommunity, String command, int quotaCost, List<Role> allowedRoles) {
		super(bot, waterballCommunity);
		this.command = command;
		this.quotaCost = quotaCost;
		this.allowedRoles = allowedRoles;
	}
	
	@Override
	public boolean evaluate(FSMContext context, State fromState, Event event) {
		botBase.event.NewMessageEvent newMsgEvent = (botBase.event.NewMessageEvent)event;
		
		return Strings.CS.equals(command, newMsgEvent.getMessageContent())		// 指令
				&& newMsgEvent.getMessageTags().stream().anyMatch(tag -> tag.equals(Bot.BOT_TAG)
				&& allowedRoles.contains(waterballCommunity.getMemberById(newMsgEvent.getMessageAuthorId()).getRole())	// 權限
				&& bot.isCommandQuotaEnough(quotaCost))	// 額度需足夠 
				&& extraEvaluate(context, fromState, event); 	
	}
	
	protected boolean extraEvaluate(FSMContext context, State fromState, Event event) {
		return true;
	}
	
	
}