package botImpl.state.knowledgeKing;

import java.util.Arrays;

import botBase.BaseBotCommandAction;
import botBase.BaseBotCommandGuard;
import botBase.BaseBotCommandTransition;
import botImpl.Bot;
import botImpl.state.normal.NormalState;
import waterballCommunity.Role;
import waterballCommunity.WaterballCommunity;

public class KnowledgeKingStateCommandKingStopTransition extends BaseBotCommandTransition{

	public KnowledgeKingStateCommandKingStopTransition(Bot bot, WaterballCommunity waterballCommunity) {
		super(new BaseBotCommandGuard(bot, waterballCommunity, "king-stop", 0, Arrays.asList(Role.ADMIN))
			, new BaseBotCommandAction(bot, waterballCommunity, 0)
			, NormalState.class);
	}
	

}
