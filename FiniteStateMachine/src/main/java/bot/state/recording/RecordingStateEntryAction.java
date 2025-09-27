package bot.state.recording;

import fsm.FSMContext;
import fsm.IEntryAction;
import fsm.IState;

public class RecordingStateEntryAction implements IEntryAction {

	@Override
	public void execute(FSMContext context, IState state) {
		((RecordingState) state).initState();
	}

}
