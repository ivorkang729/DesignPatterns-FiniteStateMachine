package botImpl.state.record;

import botBase.BaseBotCommandTransition;
import botImpl.Bot;
import botImpl.state.normal.NormalState;
import waterballCommunity.WaterballCommunity;

public class RecordStateCommandStopRecordingTransition extends BaseBotCommandTransition{

	public RecordStateCommandStopRecordingTransition(Bot bot, WaterballCommunity waterballCommunity) {
		super(new RecordStateCommandStopRecordingGuard(bot, waterballCommunity)
			, new RecordStateCommandStopRecordingAction(bot, waterballCommunity, 0)
			, NormalState.class);
	}
	

}
