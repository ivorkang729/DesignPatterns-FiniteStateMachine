package fsm;

public class NoOpEntryAction implements IEntryAction {

	@Override
	public void execute(FSMContext context, IState state) {
		// Default implementation does nothing
	}

}
