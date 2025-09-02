package fsm;

@FunctionalInterface
public interface EntryAction {
	void execute(FSMContext context, State state);
}
