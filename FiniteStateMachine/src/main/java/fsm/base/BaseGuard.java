package fsm.base;

import fsm.Event;
import fsm.FSMContext;
import fsm.Guard;
import fsm.State;

public class BaseGuard implements Guard {

	@Override
	public boolean evaluate(FSMContext context, State fromState, Event event) {
		// Default implementation always returns true
		return true;
	}

}
