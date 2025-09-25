package fsm;

public class NoOpExitAction implements EntryAction {

	@Override
	public void execute(FSMContext context, State state) {
		// Default implementation does nothing
	}

}
