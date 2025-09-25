package bot.state.normal;

import bot.BaseBotCommandAction;
import bot.Bot;
import bot.state.record.RecordState;
import fsm.IEvent;
import fsm.FSMContext;
import fsm.IState;
import waterballCommunity.Member;
import waterballCommunity.WaterballCommunity;

public class NormalStateCommandRecordAction extends BaseBotCommandAction {

	public NormalStateCommandRecordAction(Bot bot, WaterballCommunity waterballCommunity, int quotaCost) {
		super(bot, waterballCommunity, quotaCost);
	}

	@Override
	protected void extendAction(FSMContext context, IState fromState, IEvent event) {
		// 找出錄音者，並設定錄音者
		bot.event.NewMessageEvent newMessageEvent = (bot.event.NewMessageEvent)event;
		Member recorder = waterballCommunity.getMemberById(newMessageEvent.getMessageAuthorId());	//錄音者
		RecordState recStat = (RecordState)context.getState(RecordState.class.getSimpleName());
		recStat.setRecorder(recorder);
	}

}
