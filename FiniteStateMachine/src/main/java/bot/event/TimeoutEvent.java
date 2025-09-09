package bot.event;
import fsm.Event;

public class TimeoutEvent extends Event {
	private final static String EVENT_NAME = "timeout";

	public TimeoutEvent() {
		super(EVENT_NAME);
	}

}
