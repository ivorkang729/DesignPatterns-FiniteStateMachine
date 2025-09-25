package bot.event;
import fsm.IEvent;

public class TimeoutEvent extends IEvent {
	private final static String EVENT_NAME = "timeout";

	public TimeoutEvent() {
		super(EVENT_NAME);
	}

}
