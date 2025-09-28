package bot.state.record;

import java.util.Arrays;

import bot.BaseBotCommandGuard;
import bot.Bot;
import fsm.FSMEvent;
import fsm.FSMContext;
import fsm.IState;
import waterballCommunity.Role;
import waterballCommunity.WaterballCommunity;

public class RecordStateCommandStopRecordingGuard extends BaseBotCommandGuard {

	public RecordStateCommandStopRecordingGuard(Bot bot, WaterballCommunity waterballCommunity) {
		super(bot, waterballCommunity
				, "stop-recording", 0, Arrays.asList(Role.ADMIN, Role.MEMBER));
	}
	
	@Override
	protected boolean extraConditions(FSMContext context, IState fromState, FSMEvent event) {
		//只有錄音者方可使用此指令
		bot.event.NewMessageEvent newMsgEvent = (bot.event.NewMessageEvent)event;
		return newMsgEvent.getMessageAuthorId().equals(
				((RecordState)context.getState(RecordState.class.getSimpleName())).getRecorder().getId());	// 錄音者 ID
	}

}
