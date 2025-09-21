package botBase;

import java.util.concurrent.TimeUnit;

import botBase.event.NewMessageEvent;
import botBase.event.NewPostEvent;
import botBase.event.SpeakEvent;
import botImpl.Bot;
import fsm.EntryAction;
import fsm.ExitAction;
import fsm.base.BaseState;

public abstract class BotBaseState extends BaseState {
	
	protected Bot bot;

	public BotBaseState(Bot bot, String stateName, EntryAction entryStateAction, ExitAction exitStateAction) {
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
