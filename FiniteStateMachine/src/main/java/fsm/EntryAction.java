package fsm;

@FunctionalInterface
public interface EntryAction {
	void execute(Context context, State state);
}
