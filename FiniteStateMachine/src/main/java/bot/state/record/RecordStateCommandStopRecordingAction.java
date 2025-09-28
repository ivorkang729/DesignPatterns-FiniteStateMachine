package bot.state.record;

import bot.BaseBotCommandAction;
import bot.Bot;
import fsm.FSMEvent;
import fsm.FSMContext;
import fsm.IState;
import waterballCommunity.WaterballCommunity;

public class RecordStateCommandStopRecordingAction extends BaseBotCommandAction {

	public RecordStateCommandStopRecordingAction(Bot bot, WaterballCommunity waterballCommunity, int quotaCost) {
		super(bot, waterballCommunity, quotaCost);
	}

	@Override
	protected void extendAction(FSMContext context, IState fromState, FSMEvent event) {
		// 清除錄音者
		((RecordState)fromState).clearRecorder();
	}

}
