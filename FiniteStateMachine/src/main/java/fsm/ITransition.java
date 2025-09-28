package fsm;

public interface ITransition {
	boolean evaluate(FSMContext context, IState fromState, FSMEvent event);
	void trigger(FSMContext context, IState fromState, FSMEvent event);
}