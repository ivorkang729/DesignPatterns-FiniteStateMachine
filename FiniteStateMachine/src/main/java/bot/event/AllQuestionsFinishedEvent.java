package bot.event;
import fsm.FSMEvent;

public class AllQuestionsFinishedEvent extends FSMEvent {
	private final static String EVENT_NAME = "all-questions-finished";

	public AllQuestionsFinishedEvent() {
		super(EVENT_NAME);
	}

}
