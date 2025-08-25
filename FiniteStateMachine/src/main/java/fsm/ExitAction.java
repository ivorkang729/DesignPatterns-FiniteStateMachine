package fsm;

@FunctionalInterface
public interface ExitAction {
	void execute(Context context, State state);
}
