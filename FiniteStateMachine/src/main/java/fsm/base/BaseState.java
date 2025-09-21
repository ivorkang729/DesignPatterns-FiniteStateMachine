package fsm.base;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fsm.EntryAction;
import fsm.Event;
import fsm.ExitAction;
import fsm.FSMContext;
import fsm.State;
import fsm.Transition;

public abstract class BaseState implements State {
	private static final Logger logger = LogManager.getLogger(BaseState.class);
	
	private String stateName;
	private State parentState;
	private EntryAction entryStateAction;
	private ExitAction exitStateAction;
	private List<Transition> transitions = new ArrayList<>();

	public BaseState(String name, EntryAction entryStateAction, ExitAction exitStateAction) {
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
	
	@Override
	public String getName() {
		return stateName;
	}

	@Override
	public void addTransition(Transition transition) {
		if (transition == null) {
			throw new IllegalArgumentException("Transition cannot be null");
		}
		this.transitions.add(transition);
	}

	@Override
	public void setParentState(State parentState) {
		this.parentState = parentState;
	}
	
	@Override
	public void entryState(FSMContext context) {
		logger.debug("Entering state: " + stateName);
		entryStateAction.execute(context, this);
	}

	@Override
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

	@Override
	public void exitState(FSMContext context) {
		logger.debug("Exiting state: " + stateName);
		exitStateAction.execute(context, this);
	}
	
}
