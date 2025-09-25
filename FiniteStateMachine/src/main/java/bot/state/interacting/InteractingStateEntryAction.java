package bot.state.interacting;

import fsm.FSMContext;
import fsm.IEntryAction;
import fsm.IState;

public class InteractingStateEntryAction implements IEntryAction {

	@Override
	public void execute(FSMContext context, IState state) {
		((InteractingState) state).initState();
	}

}
