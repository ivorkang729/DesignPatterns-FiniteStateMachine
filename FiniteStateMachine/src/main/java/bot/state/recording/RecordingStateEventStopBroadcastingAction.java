package bot.state.recording;

import bot.AbstractBotAction;
import bot.Bot;
import bot.state.record.RecordState;
import fsm.IEvent;
import fsm.FSMContext;
import fsm.IState;
import waterballCommunity.Member;
import waterballCommunity.WaterballCommunity;

public class RecordingStateEventStopBroadcastingAction extends AbstractBotAction {
	
	public RecordingStateEventStopBroadcastingAction(Bot bot, WaterballCommunity waterballCommunity) {
		super(bot, waterballCommunity);
	}

	@Override
	public void execute(FSMContext context, IState fromState, IEvent event) {
		// 輸出錄下的所有語音訊息、標記「講者」。
		Member recorder = ((RecordState)context.getState(RecordState.class.getSimpleName())).getRecorder();
		((RecordingState)fromState).replayRecordingContent(recorder.getId());
	}

}
