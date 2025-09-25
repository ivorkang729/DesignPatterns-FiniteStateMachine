package bot;

import java.util.concurrent.TimeUnit;

import bot.event.NewMessageEvent;
import bot.event.NewPostEvent;
import bot.event.SpeakEvent;
import fsm.IEntryAction;
import fsm.IExitAction;
import fsm.FSMState;

public abstract class BaseBotState extends FSMState {
	
	protected Bot bot;

	public BaseBotState(Bot bot, String stateName, IEntryAction entryStateAction, IExitAction exitStateAction) {
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
