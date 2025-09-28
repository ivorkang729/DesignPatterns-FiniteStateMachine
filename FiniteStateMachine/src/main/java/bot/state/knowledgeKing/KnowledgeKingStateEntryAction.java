package bot.state.knowledgeKing;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bot.Bot;
import bot.state.questioning.QuestioningState;
import bot.state.recording.RecordingState;
import bot.state.waiting.WaitingState;
import fsm.FSMContext;
import fsm.IEntryAction;
import fsm.IState;
import waterballCommunity.WaterballCommunity;

public class KnowledgeKingStateEntryAction implements IEntryAction {
	private static final Logger logger = LogManager.getLogger(KnowledgeKingStateEntryAction.class);
	
	private final String NAME_QUESTIONING_STATE = QuestioningState.class.getSimpleName();
	
	private Bot bot;
	
	public KnowledgeKingStateEntryAction(Bot bot) {
		this.bot = bot;
	}

	@Override
	public void execute(FSMContext context, IState state) {
		bot.sendNewMessageToChatRoom("KnowledgeKing is started!", new ArrayList<>());
		((KnowledgeKingState) state).initState();
		// 進入 QuestioningState
		IState newState = context.getState(NAME_QUESTIONING_STATE);
		context.transCurrentStateTo(newState);
	}

}
