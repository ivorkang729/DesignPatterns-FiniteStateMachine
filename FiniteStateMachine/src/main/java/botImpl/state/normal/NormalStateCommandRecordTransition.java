package botImpl.state.normal;

import java.util.Arrays;

import botBase.BaseBotCommandGuard;
import botBase.BaseBotCommandTransition;
import botImpl.Bot;
import botImpl.state.record.RecordState;
import waterballCommunity.Role;
import waterballCommunity.WaterballCommunity;

public class NormalStateCommandRecordTransition extends BaseBotCommandTransition{

	public NormalStateCommandRecordTransition(Bot bot, WaterballCommunity waterballCommunity) {
		super(new BaseBotCommandGuard(bot, waterballCommunity, "record", 3, Arrays.asList(Role.ADMIN, Role.MEMBER))
			, new NormalStateCommandRecordAction(bot, waterballCommunity, 3)
			, RecordState.class);
	}

}
