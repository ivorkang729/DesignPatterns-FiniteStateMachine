package fsm;

@FunctionalInterface
public interface IExitAction {
	void execute(FSMContext context, IState state);
}
