package fsm;

@FunctionalInterface
public interface Action {
	void execute(Context context, State fromState, Event event);
}
