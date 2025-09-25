package fsm;

public class NoOpGuard implements Guard {

	@Override
	public boolean evaluate(FSMContext context, State fromState, Event event) {
		// Default implementation always returns true
		return true;
	}

}
