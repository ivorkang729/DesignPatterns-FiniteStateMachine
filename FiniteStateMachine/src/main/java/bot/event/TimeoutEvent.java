package bot.event;
import fsm.FSMEvent;

public class TimeoutEvent extends FSMEvent {
	private final static String EVENT_NAME = "timeout";

	public TimeoutEvent() {
		super(EVENT_NAME);
	}

}
