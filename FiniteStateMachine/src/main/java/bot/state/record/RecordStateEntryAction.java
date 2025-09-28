package bot.state.record;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bot.state.defaultConversation.DefaultConversationState;
import bot.state.interacting.InteractingState;
import bot.state.recording.RecordingState;
import bot.state.waiting.WaitingState;
import fsm.FSMContext;
import fsm.IEntryAction;
import fsm.IState;
import waterballCommunity.WaterballCommunity;

public class RecordStateEntryAction implements IEntryAction {
	private static final Logger logger = LogManager.getLogger(RecordStateEntryAction.class);
	
	private final String NAME_RECORDING_STATE = RecordingState.class.getSimpleName();
	private final String NAME_WAITING_STATE = WaitingState.class.getSimpleName();
	
	private WaterballCommunity waterballCommunity;
	
	public RecordStateEntryAction(WaterballCommunity waterballCommunity) {
		this.waterballCommunity = waterballCommunity;
	}

	@Override
	public void execute(FSMContext context, IState state) {
		//如果已經有講者正在廣播，初始狀態為錄音中狀態，否則會直接進入等待狀態。
		if (waterballCommunity.isSomeoneBroadcasting()) {
			logger.debug("有人正在廣播，切換到錄音中狀態");
			IState newState = context.getState(NAME_RECORDING_STATE);
			context.transCurrentStateTo(newState);
		} else {
			logger.debug("沒有人正在廣播，切換到等待狀態");
			IState newState = context.getState(NAME_WAITING_STATE);
			context.transCurrentStateTo(newState);
		}
	}

}
