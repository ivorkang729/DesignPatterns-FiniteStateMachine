package botImpl.state.thanksForJoining;

import java.util.ArrayList;

import botBase.BaseBotCommandAction;
import botImpl.Bot;
import fsm.Event;
import fsm.FSMContext;
import fsm.State;
import waterballCommunity.WaterballCommunity;

public class ThanksForJoiningStateCommandPlayAgainAction extends BaseBotCommandAction {

	public ThanksForJoiningStateCommandPlayAgainAction(Bot bot, WaterballCommunity waterballCommunity, int quotaCost) {
		super(bot, waterballCommunity, quotaCost);
	}

	@Override
	protected void extendAction(FSMContext context, State fromState, Event event) {
		bot.sendNewMessageToChatRoom("KnowledgeKing is gonna start again!", new ArrayList<>());
	}

}
