package fsm;

public class NoOpExitAction implements IExitAction {

	@Override
	public void execute(FSMContext context, IState state) {
		// Default implementation does nothing
	}

}
