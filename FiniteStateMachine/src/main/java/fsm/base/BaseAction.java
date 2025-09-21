package fsm.base;

import fsm.Action;
import fsm.Event;
import fsm.FSMContext;
import fsm.State;

public class BaseAction implements Action {

	@Override
	public void execute(FSMContext context, State fromState, Event event) {
		// Default implementation does nothing
	}

}
