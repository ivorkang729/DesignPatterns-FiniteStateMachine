package fsm;

public class NoOpEntryAction implements EntryAction {

	@Override
	public void execute(FSMContext context, State state) {
		// Default implementation does nothing
	}

}
