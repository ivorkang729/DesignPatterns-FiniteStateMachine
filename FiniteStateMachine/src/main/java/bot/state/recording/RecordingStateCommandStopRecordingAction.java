package bot.state.recording;

import bot.BaseBotCommandAction;
import bot.Bot;
import bot.state.record.RecordState;
import fsm.IEvent;
import fsm.FSMContext;
import fsm.IState;
import waterballCommunity.Member;
import waterballCommunity.WaterballCommunity;

public class RecordingStateCommandStopRecordingAction extends BaseBotCommandAction {

	public RecordingStateCommandStopRecordingAction(Bot bot, WaterballCommunity waterballCommunity, int quotaCost) {
		super(bot, waterballCommunity, quotaCost);
	}

	@Override
	protected void extendAction(FSMContext context, IState fromState, IEvent event) {
		// 輸出錄下的所有語音訊息、標記「錄音者」。
		Member recorder = ((RecordState)context.getState(RecordState.class.getSimpleName())).getRecorder();
		((RecordingState)fromState).replayRecordingContent(recorder.getId());
		
		// 清除錄音者
		((RecordState)context.getState(RecordState.class.getSimpleName())).clearRecorder();
	}

}
