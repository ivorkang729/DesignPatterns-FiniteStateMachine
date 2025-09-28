package bot.state.thanksForJoining;

import java.util.ArrayList;

import bot.BaseBotCommandAction;
import bot.Bot;
import fsm.FSMEvent;
import fsm.FSMContext;
import fsm.IState;
import waterballCommunity.WaterballCommunity;

public class ThanksForJoiningStateCommandPlayAgainAction extends BaseBotCommandAction {

	public ThanksForJoiningStateCommandPlayAgainAction(Bot bot, WaterballCommunity waterballCommunity, int quotaCost) {
		super(bot, waterballCommunity, quotaCost);
	}

	@Override
	protected void extendAction(FSMContext context, IState fromState, FSMEvent event) {
		bot.sendNewMessageToChatRoom("KnowledgeKing is gonna start again!", new ArrayList<>());
	}

}
