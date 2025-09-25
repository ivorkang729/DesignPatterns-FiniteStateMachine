package bot.state.defaultConversation;

import fsm.FSMContext;
import fsm.IEntryAction;
import fsm.IState;

public class DefaultConversationStateEntryAction implements IEntryAction {

	@Override
	public void execute(FSMContext context, IState state) {
		((DefaultConversationState) state).initState();
	}

}
