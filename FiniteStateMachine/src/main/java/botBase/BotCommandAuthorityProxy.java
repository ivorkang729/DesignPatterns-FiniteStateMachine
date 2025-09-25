package botBase;

import java.util.List;

import botImpl.Bot;
import fsm.IEvent;
import fsm.FSMContext;
import fsm.IState;
import waterballCommunity.Role;
import waterballCommunity.WaterballCommunity;

public class BotCommandAuthorityProxy extends AbstractBotGuard {
	
	private List<Role> allowedRoles;
	private AbstractBotGuard commandGuard;
	
	public BotCommandAuthorityProxy(Bot bot, WaterballCommunity waterballCommunity, List<Role> allowedRoles, AbstractBotGuard commandGuard) {
		super(bot, waterballCommunity);
		this.allowedRoles = allowedRoles;
		this.commandGuard = commandGuard;
	}
	
	@Override
	public boolean evaluate(FSMContext context, IState fromState, IEvent event) {
		botBase.event.NewMessageEvent newMsgEvent = (botBase.event.NewMessageEvent)event;
		return allowedRoles.contains(waterballCommunity.getMemberById(newMsgEvent.getMessageAuthorId()).getRole())	// 權限
				&& commandGuard.evaluate(context, fromState, event);
	}
	
}