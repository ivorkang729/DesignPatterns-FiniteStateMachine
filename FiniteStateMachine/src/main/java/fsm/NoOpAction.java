package fsm;

public class NoOpAction implements Action {

	@Override
	public void execute(FSMContext context, State fromState, Event event) {
		// Default implementation does nothing
	}

}
