package fsm;

public interface Transition {
	boolean evaluate(FSMContext context, State fromState, Event event);
	void trigger(FSMContext context, State fromState, Event event);
}