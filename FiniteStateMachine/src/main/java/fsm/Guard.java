package fsm;

@FunctionalInterface
public interface Guard {
	public boolean evaluate(FSMContext context, State fromState, Event event);
}
