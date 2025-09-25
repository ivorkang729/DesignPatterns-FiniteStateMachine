package bot.event;
import fsm.IEvent;

public class AllQuestionsFinishedEvent extends IEvent {
	private final static String EVENT_NAME = "all-questions-finished";

	public AllQuestionsFinishedEvent() {
		super(EVENT_NAME);
	}

}
