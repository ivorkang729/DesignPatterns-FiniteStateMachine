package bot.state.normal;

import java.util.Arrays;

import bot.BaseBotCommandAction;
import bot.BaseBotCommandGuard;
import bot.BaseBotCommandTransition;
import bot.Bot;
import bot.state.knowledgeKing.KnowledgeKingState;
import waterballCommunity.Role;
import waterballCommunity.WaterballCommunity;

public class NormalStateCommandKingTransition extends BaseBotCommandTransition{

	public NormalStateCommandKingTransition(Bot bot, WaterballCommunity waterballCommunity) {
		super(new BaseBotCommandGuard(bot, waterballCommunity, "king", 5, Arrays.asList(Role.ADMIN))
			, new BaseBotCommandAction(bot, waterballCommunity, 5)
			, KnowledgeKingState.class);
	}
	

}
