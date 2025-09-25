package botImpl.state.normal;

import java.util.Arrays;

import botBase.BaseBotCommandAction;
import botBase.BaseBotCommandGuard;
import botBase.BaseBotCommandTransition;
import botImpl.Bot;
import botImpl.state.knowledgeKing.KnowledgeKingState;
import waterballCommunity.Role;
import waterballCommunity.WaterballCommunity;

public class NormalStateCommandKingTransition extends BaseBotCommandTransition{

	public NormalStateCommandKingTransition(Bot bot, WaterballCommunity waterballCommunity) {
		super(new BaseBotCommandGuard(bot, waterballCommunity, "king", 5, Arrays.asList(Role.ADMIN))
			, new BaseBotCommandAction(bot, waterballCommunity, 5)
			, KnowledgeKingState.class);
	}
	

}
