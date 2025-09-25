package botBase;

import java.util.concurrent.TimeUnit;

import botBase.event.NewMessageEvent;
import botBase.event.NewPostEvent;
import botBase.event.SpeakEvent;
import botImpl.Bot;
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
