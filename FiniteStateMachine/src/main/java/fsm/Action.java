package fsm;

@FunctionalInterface
public interface Action {
	void execute(FSMContext context, State fromState, Event event);
}
