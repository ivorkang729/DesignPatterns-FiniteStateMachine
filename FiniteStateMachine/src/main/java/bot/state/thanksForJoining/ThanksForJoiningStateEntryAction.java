package bot.state.thanksForJoining;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fsm.FSMContext;
import fsm.IEntryAction;
import fsm.IState;

public class ThanksForJoiningStateEntryAction implements IEntryAction {
	private static final Logger logger = LogManager.getLogger(ThanksForJoiningStateEntryAction.class);
	
	public ThanksForJoiningStateEntryAction() {
	}

	@Override
	public void execute(FSMContext context, IState state) {
		((ThanksForJoiningState) state).initState();
		((ThanksForJoiningState) state).winnerAnnouncement();
	}

}
