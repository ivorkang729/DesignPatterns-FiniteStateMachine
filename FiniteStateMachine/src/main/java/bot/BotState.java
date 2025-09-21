package bot;

import java.util.concurrent.TimeUnit;

import bot.event.NewMessageEvent;
import bot.event.NewPostEvent;
import bot.event.SpeakEvent;
import fsm.EntryAction;
import fsm.ExitAction;
import fsm.base.BaseState;

public abstract class BotState extends BaseState {
	
	protected Bot bot;

	public BotState(Bot bot, String stateName, EntryAction entryStateAction, ExitAction exitStateAction) {
		super(stateName, entryStateAction, exitStateAction);
		this.bot = bot;
	}
	
	public void initState() {
		// TODO Auto-generated method stub
	}
	
	public void onNewMessage(NewMessageEvent event) {
		// do nothing by default
	}
	
	public void onNewPost(NewPostEvent event) {
		// do nothing by default
	}

    public void onSpeak(SpeakEvent speakEvent) {
        // do nothing by default
    }

	public void increaseElapsedTime(int time, TimeUnit unit) {
		// do nothing by default
	}


}
