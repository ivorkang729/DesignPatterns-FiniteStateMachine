package botImpl.state.record;

import botBase.BaseBotCommandAction;
import botImpl.Bot;
import fsm.Event;
import fsm.FSMContext;
import fsm.State;
import waterballCommunity.WaterballCommunity;

public class RecordStateCommandStopRecordingAction extends BaseBotCommandAction {

	public RecordStateCommandStopRecordingAction(Bot bot, WaterballCommunity waterballCommunity, int quotaCost) {
		super(bot, waterballCommunity, quotaCost);
	}

	@Override
	protected void extendAction(FSMContext context, State fromState, Event event) {
		// 清除錄音者
		((RecordState)fromState).clearRecorder();
	}

}
