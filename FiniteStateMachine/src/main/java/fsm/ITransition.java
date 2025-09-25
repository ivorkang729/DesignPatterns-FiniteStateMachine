package fsm;

public interface ITransition {
	boolean evaluate(FSMContext context, IState fromState, IEvent event);
	void trigger(FSMContext context, IState fromState, IEvent event);
}