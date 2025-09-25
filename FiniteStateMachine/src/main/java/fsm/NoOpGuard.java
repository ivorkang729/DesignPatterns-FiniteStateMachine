package fsm;

public class NoOpGuard implements IGuard {

	@Override
	public boolean evaluate(FSMContext context, IState fromState, IEvent event) {
		// Default implementation always returns true
		return true;
	}

}
