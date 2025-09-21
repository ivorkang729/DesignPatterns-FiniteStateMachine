package botBase;

import fsm.Action;
import fsm.Guard;
import fsm.base.BaseState;
import fsm.base.BaseTransition;

public class BaseBotCommandTransition extends BaseTransition {

	public BaseBotCommandTransition(Guard guard, Action action, Class<? extends BaseState> toStateClass) {
		super(botBase.event.NewMessageEvent.class	// 指令的-->固定為 NewMessageEvent
				, guard, action, toStateClass);
	}

}
