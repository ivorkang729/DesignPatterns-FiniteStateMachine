package botImpl.state.recording;

import botBase.BaseBotAction;
import botImpl.Bot;
import botImpl.state.record.RecordState;
import fsm.Event;
import fsm.FSMContext;
import fsm.State;
import waterballCommunity.Member;
import waterballCommunity.WaterballCommunity;

public class RecordingStateEventStopBroadcastingAction extends BaseBotAction {
	
	public RecordingStateEventStopBroadcastingAction(Bot bot, WaterballCommunity waterballCommunity) {
		super(bot, waterballCommunity);
	}

	@Override
	public void execute(FSMContext context, State fromState, Event event) {
		// 輸出錄下的所有語音訊息、標記「講者」。
		Member recorder = ((RecordState)context.getState(RecordState.class.getSimpleName())).getRecorder();
		((RecordingState)fromState).replayRecordingContent(recorder.getId());
	}

}
