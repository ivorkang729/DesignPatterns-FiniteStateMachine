package botBase;

import fsm.base.BaseTransition;

public class BaseBotCommandTransition extends BaseTransition {

	public BaseBotCommandTransition(BaseBotGuard guard, BaseBotAction action, Class<? extends BaseBotState> toStateClass) {
		super(botBase.event.NewMessageEvent.class	// 指令的-->固定為 NewMessageEvent
				, guard, action, toStateClass);
	}

}
