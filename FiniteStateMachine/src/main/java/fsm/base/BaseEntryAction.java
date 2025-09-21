package fsm.base;

import fsm.EntryAction;
import fsm.FSMContext;
import fsm.State;

public class BaseEntryAction implements EntryAction {

	@Override
	public void execute(FSMContext context, State state) {
		// Default implementation does nothing
	}

}
