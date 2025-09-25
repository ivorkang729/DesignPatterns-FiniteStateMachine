package fsm;

public class NoOpExitAction implements IEntryAction {

	@Override
	public void execute(FSMContext context, IState state) {
		// Default implementation does nothing
	}

}
