package bot;

import fsm.FSMTransition;

public class BaseBotCommandTransition extends FSMTransition {

	public BaseBotCommandTransition(BaseBotCommandGuard guard, BaseBotCommandAction action, Class<? extends BaseBotState>toStateClass) {
		super(bot.event.NewMessageEvent.class	// 指令 --> 固定為 NewMessageEvent
				, guard, action, toStateClass);
	}

}
