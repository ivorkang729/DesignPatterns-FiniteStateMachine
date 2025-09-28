package bot.state.questioning;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fsm.FSMContext;
import fsm.IEntryAction;
import fsm.IState;

public class QuestioningStateEntryAction implements IEntryAction {
	private static final Logger logger = LogManager.getLogger(QuestioningStateEntryAction.class);
	
	public QuestioningStateEntryAction() {
	}

	@Override
	public void execute(FSMContext context, IState state) {
		((QuestioningState) state).initState();
		((QuestioningState) state).showNextQuestion();
	}

}
