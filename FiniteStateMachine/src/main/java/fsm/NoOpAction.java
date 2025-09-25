package fsm;

public class NoOpAction implements IAction {

	@Override
	public void execute(FSMContext context, IState fromState, IEvent event) {
		// Default implementation does nothing
	}

}
