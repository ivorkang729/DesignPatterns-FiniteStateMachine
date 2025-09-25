package botImpl.state.thanksForJoining;

import java.util.Arrays;

import botBase.BaseBotCommandGuard;
import botBase.BaseBotCommandTransition;
import botImpl.Bot;
import botImpl.state.QuestioningState;
import waterballCommunity.Role;
import waterballCommunity.WaterballCommunity;

public class ThanksForJoiningStateCommandPlayAgainTransition extends BaseBotCommandTransition{

	public ThanksForJoiningStateCommandPlayAgainTransition(Bot bot, WaterballCommunity waterballCommunity) {
		super(new BaseBotCommandGuard(bot, waterballCommunity, "play again", 5, Arrays.asList(Role.ADMIN, Role.MEMBER))
			, new ThanksForJoiningStateCommandPlayAgainAction(bot, waterballCommunity, 5)
			, QuestioningState.class);
	}

}
