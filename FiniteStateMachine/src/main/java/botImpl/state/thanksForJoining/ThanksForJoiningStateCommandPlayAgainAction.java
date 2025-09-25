package botImpl.state.thanksForJoining;

import java.util.ArrayList;

import botBase.BaseBotCommandAction;
import botImpl.Bot;
import fsm.IEvent;
import fsm.FSMContext;
import fsm.IState;
import waterballCommunity.WaterballCommunity;

public class ThanksForJoiningStateCommandPlayAgainAction extends BaseBotCommandAction {

	public ThanksForJoiningStateCommandPlayAgainAction(Bot bot, WaterballCommunity waterballCommunity, int quotaCost) {
		super(bot, waterballCommunity, quotaCost);
	}

	@Override
	protected void extendAction(FSMContext context, IState fromState, IEvent event) {
		bot.sendNewMessageToChatRoom("KnowledgeKing is gonna start again!", new ArrayList<>());
	}

}
