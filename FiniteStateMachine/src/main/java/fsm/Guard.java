package fsm;

@FunctionalInterface
public interface Guard {
	public boolean evaluate(Context context, State fromState, Event event);
}
