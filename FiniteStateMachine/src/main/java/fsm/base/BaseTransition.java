package fsm.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fsm.Action;
import fsm.Event;
import fsm.FSMContext;
import fsm.Guard;
import fsm.State;
import fsm.Transition;

public class BaseTransition implements Transition {
	private static final Logger logger = LogManager.getLogger(BaseTransition.class);
	
	private String name;
	private Class<? extends Event> eventClass;
	private Guard guard;
	private Action action;
	private Class<? extends BaseState> toStateClass;
	
	public BaseTransition(Class<? extends Event> eventClass, Guard guard, Action action, Class<? extends BaseState> toStateClass) {
		if (eventClass == null) {
			throw new IllegalArgumentException("eventClass cannot be null");
		}
		if (guard == null) {
			throw new IllegalArgumentException("guard cannot be null");
		}
		if (action == null) {
			throw new IllegalArgumentException("action cannot be null");
		}
		this.name = this.getClass().getSimpleName();
		this.toStateClass = toStateClass;
		this.eventClass = eventClass;
		this.guard = guard;
		this.action = action;
	}
	
	@Override
	public boolean evaluate(FSMContext context, State fromState, Event event) {
		return (this.eventClass.isInstance(event)
				&& this.guard.evaluate(context, fromState, event));
	}

	@Override
	public void trigger(FSMContext context, State fromState, Event event) {
		logger.debug("Transition " + name + " triggered.");
		action.execute(context, fromState, event);
		if (toStateClass == null) {
			return;	// Internal transition, no state change
		}
		// Change the current state to the new state
		if (context.getState(toStateClass.getSimpleName()) != null) {
			context.transCurrentStateTo(context.getState(toStateClass.getSimpleName()));
		} else {
			throw new IllegalStateException("State " + toStateClass.getSimpleName() + " is not registered.");
		}
	}
}