package bot.state.record;

import bot.BaseBotCommandTransition;
import bot.Bot;
import bot.state.normal.NormalState;
import waterballCommunity.WaterballCommunity;

public class RecordStateCommandStopRecordingTransition extends BaseBotCommandTransition{

	public RecordStateCommandStopRecordingTransition(Bot bot, WaterballCommunity waterballCommunity) {
		super(new RecordStateCommandStopRecordingGuard(bot, waterballCommunity)
			, new RecordStateCommandStopRecordingAction(bot, waterballCommunity, 0)
			, NormalState.class);
	}
	

}
