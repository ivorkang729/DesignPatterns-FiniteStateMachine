package botBase;

import fsm.FSMTransition;

public class BaseBotCommandTransition extends FSMTransition {

	public BaseBotCommandTransition(AbstractBotGuard guard, AbstractBotAction action, Class<? extends BaseBotState> toStateClass) {
		super(botBase.event.NewMessageEvent.class	// 指令的-->固定為 NewMessageEvent
				, guard, action, toStateClass);
	}

}
