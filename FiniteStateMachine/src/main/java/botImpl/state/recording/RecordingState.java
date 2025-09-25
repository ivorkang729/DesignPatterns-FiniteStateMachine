package botImpl.state.recording;

import botBase.BaseBotState;
import botBase.event.SpeakEvent;
import botImpl.Bot;
import fsm.IEntryAction;
import fsm.IExitAction;
import waterballCommunity.chatroom.Message;

public class RecordingState extends BaseBotState {

	/** 錄音內容 */
	private StringBuilder recordingContent;
	
	public RecordingState(String stateName, Bot bot, IEntryAction entryStateAction, IExitAction exitStateAction) {
		super(bot, stateName, entryStateAction, exitStateAction);
		recordingContent = new StringBuilder();
	}

	/** 重置錄音內容 */
	@Override
	public void initState() {
		recordingContent = new StringBuilder();
	}

	/** 重播錄音內容 */
	public void replayRecordingContent(String recorderId) {
		Message msg = new Message(Bot.BOT_TAG
			, String.format("[Record Replay] %s", recordingContent.toString().replaceFirst(System.lineSeparator(), "")));
		msg.addTag(recorderId);
		bot.getWaterballCommunity().newMessage(msg);
	}

	@Override
	public void onSpeak(SpeakEvent speakEvent) {
		recordingContent.append(System.lineSeparator()).append(speakEvent.getSpeakContent());
	}
	
}