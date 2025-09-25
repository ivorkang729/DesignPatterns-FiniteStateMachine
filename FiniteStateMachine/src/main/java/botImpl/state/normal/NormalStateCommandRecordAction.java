package botImpl.state.normal;

import botBase.BaseBotCommandAction;
import botImpl.Bot;
import botImpl.state.record.RecordState;
import fsm.Event;
import fsm.FSMContext;
import fsm.State;
import waterballCommunity.Member;
import waterballCommunity.WaterballCommunity;

public class NormalStateCommandRecordAction extends BaseBotCommandAction {

	public NormalStateCommandRecordAction(Bot bot, WaterballCommunity waterballCommunity, int quotaCost) {
		super(bot, waterballCommunity, quotaCost);
	}

	@Override
	protected void extendAction(FSMContext context, State fromState, Event event) {
		// 找出錄音者，並設定錄音者
		botBase.event.NewMessageEvent newMessageEvent = (botBase.event.NewMessageEvent)event;
		Member recorder = waterballCommunity.getMemberById(newMessageEvent.getMessageAuthorId());	//錄音者
		RecordState recStat = (RecordState)context.getState(RecordState.class.getSimpleName());
		recStat.setRecorder(recorder);
	}

}
