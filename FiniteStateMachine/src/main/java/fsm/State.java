package fsm;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class State {
	private static final Logger logger = LogManager.getLogger(State.class);
	
	private String stateName;
	private State parentState;
	private EntryAction entryStateAction;
	private ExitAction exitStateAction;
	private List<Transition> transitions = new ArrayList<>();

	public State(String name, EntryAction entryStateAction, ExitAction exitStateAction) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("State name cannot be null or empty");
		}
		if (entryStateAction == null) {
			throw new IllegalArgumentException("Entry action cannot be null");
		}
		if (exitStateAction == null) {
			throw new IllegalArgumentException("Exit action cannot be null");
		}
		this.stateName = name;
		this.entryStateAction = entryStateAction;
		this.exitStateAction = exitStateAction;
	}

	public void addTransition(Transition transition) {
		if (transition == null) {
			throw new IllegalArgumentException("Transition cannot be null");
		}
		this.transitions.add(transition);
	}

	public void setParentState(State parentState) {
		this.parentState = parentState;
	}

	public void entryState(FSMContext context) {
		logger.debug("Entering state: " + stateName);
		entryStateAction.execute(context, this);
	}

	public void handleEvent(Event event, FSMContext context) {
		for (Transition transition : transitions) {
			if (transition.evaluate(context, this, event)) {
				transition.trigger(context, this, event);
				return;	// 只會有一個 transition 被觸發
			}
		}

		// 如果沒有 transition 被觸發，則將事件向上冒泡
		if (parentState != null) {
			parentState.handleEvent(event, context);
		}
	}

	public void exitState(FSMContext context) {
		logger.debug("Exiting state: " + stateName);
		exitStateAction.execute(context, this);
	}

	public String getName() {
		return stateName;
	}
}
