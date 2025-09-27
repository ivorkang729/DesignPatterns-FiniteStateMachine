package bot.state.thanksForJoining;

import java.util.Arrays;

import bot.BaseBotCommandGuard;
import bot.BaseBotCommandTransition;
import bot.Bot;
import bot.state.questioning.QuestioningState;
import waterballCommunity.Role;
import waterballCommunity.WaterballCommunity;

public class ThanksForJoiningStateCommandPlayAgainTransition extends BaseBotCommandTransition{

	public ThanksForJoiningStateCommandPlayAgainTransition(Bot bot, WaterballCommunity waterballCommunity) {
		super(new BaseBotCommandGuard(bot, waterballCommunity, "play again", 5, Arrays.asList(Role.ADMIN, Role.MEMBER))
			, new ThanksForJoiningStateCommandPlayAgainAction(bot, waterballCommunity, 5)
			, QuestioningState.class);
	}

}
