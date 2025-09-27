package bot.state.normal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bot.state.defaultConversation.DefaultConversationState;
import bot.state.interacting.InteractingState;
import fsm.FSMContext;
import fsm.IEntryAction;
import fsm.IState;
import waterballCommunity.WaterballCommunity;

public class NormalStateEntryAction implements IEntryAction {
	private static final Logger logger = LogManager.getLogger(NormalStateEntryAction.class);
	
	private final String NAME_DEFAULT_CONVERSATION_STATE = DefaultConversationState.class.getSimpleName();
	private final String NAME_INTERACTING_STATE = InteractingState.class.getSimpleName();
	
	private WaterballCommunity waterballCommunity;
	
	public NormalStateEntryAction(WaterballCommunity waterballCommunity) {
		this.waterballCommunity = waterballCommunity;
	}

	@Override
	public void execute(FSMContext context, IState state) {
		if (waterballCommunity.getLoggedInMemberCount() < 10) {
			logger.debug("線上人數 < 10，轉換到預設對話狀態");
			IState newState = context.getState(NAME_DEFAULT_CONVERSATION_STATE);
			context.transCurrentStateTo(newState);
		} else {
			logger.debug("線上人數 >= 10，轉換到互動狀態");
			IState newState = context.getState(NAME_INTERACTING_STATE);
			context.transCurrentStateTo(newState);
		}
	}

}
