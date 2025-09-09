package bot.state;

import bot.Bot;
import bot.BotState;
import bot.event.SpeakEvent;
import community.chatroom.Message;
import fsm.EntryAction;
import fsm.ExitAction;

public class RecordingState extends BotState {

	/** 錄音內容 */
	private StringBuilder recordingContent;
	
	public RecordingState(String stateName, Bot bot, EntryAction entryStateAction, ExitAction exitStateAction) {
		super(bot, stateName, entryStateAction, exitStateAction);
		recordingContent = new StringBuilder();
	}

	/** 重置錄音內容 */
	public void resetRecordingContent() {
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