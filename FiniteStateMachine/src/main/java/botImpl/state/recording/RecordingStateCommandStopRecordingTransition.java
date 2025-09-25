package botImpl.state.recording;

import botBase.BaseBotCommandTransition;
import botImpl.Bot;
import botImpl.state.normal.NormalState;
import waterballCommunity.WaterballCommunity;

public class RecordingStateCommandStopRecordingTransition extends BaseBotCommandTransition{

	public RecordingStateCommandStopRecordingTransition(Bot bot, WaterballCommunity waterballCommunity) {
		super(new RecordingStateCommandStopRecordingGuard(bot, waterballCommunity)
			, new RecordingStateCommandStopRecordingAction(bot, waterballCommunity, 0)
			, NormalState.class);
	}
	

}
