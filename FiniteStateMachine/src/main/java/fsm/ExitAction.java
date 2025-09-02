package fsm;

@FunctionalInterface
public interface ExitAction {
	void execute(FSMContext context, State state);
}
