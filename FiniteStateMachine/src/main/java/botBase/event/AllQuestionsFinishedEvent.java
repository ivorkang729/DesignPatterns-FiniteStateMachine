package botBase.event;
import fsm.Event;

public class AllQuestionsFinishedEvent extends Event {
	private final static String EVENT_NAME = "all-questions-finished";

	public AllQuestionsFinishedEvent() {
		super(EVENT_NAME);
	}

}
