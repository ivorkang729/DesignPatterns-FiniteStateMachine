package bot.state.record;

import bot.BaseBotState;
import bot.Bot;
import fsm.IEntryAction;
import fsm.IExitAction;
import waterballCommunity.Member;

public class RecordState extends BaseBotState {
	
	private Member recorder;

	public RecordState(String stateName, Bot bot, IEntryAction entryStateAction, IExitAction exitStateAction) {
		super(bot, stateName, entryStateAction, exitStateAction);
	}

	public void setRecorder(Member recorder) {
		this.recorder = recorder;
	}

	public Member getRecorder() {
		return recorder;
	}

	public void clearRecorder() {
		this.recorder = null;
	}
	
}