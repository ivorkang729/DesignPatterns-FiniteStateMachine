package botImpl.state;

import botBase.BotBaseState;
import botImpl.Bot;
import fsm.EntryAction;
import fsm.ExitAction;
import waterballCommunity.Member;

public class RecordState extends BotBaseState {
	
	private Member recorder;

	public RecordState(String stateName, Bot bot, EntryAction entryStateAction, ExitAction exitStateAction) {
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