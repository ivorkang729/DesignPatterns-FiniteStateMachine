package fsm;

@FunctionalInterface
public interface IGuard {
	public boolean evaluate(FSMContext context, IState fromState, IEvent event);
}
