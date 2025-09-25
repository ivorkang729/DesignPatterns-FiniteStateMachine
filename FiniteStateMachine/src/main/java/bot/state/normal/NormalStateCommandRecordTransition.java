package bot.state.normal;

import java.util.Arrays;

import bot.BaseBotCommandGuard;
import bot.BaseBotCommandTransition;
import bot.Bot;
import bot.state.record.RecordState;
import waterballCommunity.Role;
import waterballCommunity.WaterballCommunity;

public class NormalStateCommandRecordTransition extends BaseBotCommandTransition{

	public NormalStateCommandRecordTransition(Bot bot, WaterballCommunity waterballCommunity) {
		super(new BaseBotCommandGuard(bot, waterballCommunity, "record", 3, Arrays.asList(Role.ADMIN, Role.MEMBER))
			, new NormalStateCommandRecordAction(bot, waterballCommunity, 3)
			, RecordState.class);
	}

}
