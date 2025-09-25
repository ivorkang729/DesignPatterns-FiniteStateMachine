package bot.state.recording;

import bot.BaseBotCommandTransition;
import bot.Bot;
import bot.state.normal.NormalState;
import waterballCommunity.WaterballCommunity;

public class RecordingStateCommandStopRecordingTransition extends BaseBotCommandTransition{

	public RecordingStateCommandStopRecordingTransition(Bot bot, WaterballCommunity waterballCommunity) {
		super(new RecordingStateCommandStopRecordingGuard(bot, waterballCommunity)
			, new RecordingStateCommandStopRecordingAction(bot, waterballCommunity, 0)
			, NormalState.class);
	}
	

}
