package fsm;

@FunctionalInterface
public interface IAction {
	void execute(FSMContext context, IState fromState, FSMEvent event);
}
