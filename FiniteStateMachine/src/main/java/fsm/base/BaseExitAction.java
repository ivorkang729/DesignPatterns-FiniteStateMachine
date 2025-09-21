package fsm.base;

import fsm.EntryAction;
import fsm.FSMContext;
import fsm.State;

public class BaseExitAction implements EntryAction {

	@Override
	public void execute(FSMContext context, State state) {
		// Default implementation does nothing
	}

}
