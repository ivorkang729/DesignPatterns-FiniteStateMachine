package fsm;

@FunctionalInterface
public interface IEntryAction {
	void execute(FSMContext context, IState state);
}
