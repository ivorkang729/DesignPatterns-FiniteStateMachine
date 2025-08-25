package fsm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Transition {
	private static final Logger logger = LogManager.getLogger(Transition.class);
	
	private String name;
	private Class<? extends Event> eventClass;
	private Guard guard;
	private Action action;
	private Class<? extends State> toStateClass;
	
	public Transition(String name, Class<? extends Event> eventClass, Guard guard, Action action, Class<? extends State> toStateClass) {
		if (eventClass == null) {
			throw new IllegalArgumentException("eventClass cannot be null");
		}
		if (guard == null) {
			throw new IllegalArgumentException("guard cannot be null");
		}
		if (action == null) {
			throw new IllegalArgumentException("action cannot be null");
		}
		this.name = name;
		this.toStateClass = toStateClass;
		this.eventClass = eventClass;
		this.guard = guard;
		this.action = action;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean evaluate(Context context, State fromState, Event event) {
		return (this.eventClass.isInstance(event)
				&& this.guard.evaluate(context, fromState, event));
	}
	
	public void trigger(Context context, State fromState, Event event) {
		logger.debug("Transition " + name + " triggered.");
		action.execute(context, fromState, event);
		if (toStateClass == null) {
			return;	// Internal transition, no state change
		}
		// Change the current state to the new state
		if (context.getState(toStateClass.getSimpleName()) != null) {
			context.setCurrentState(context.getState(toStateClass.getSimpleName()));
		} else {
			throw new IllegalStateException("State " + toStateClass.getSimpleName() + " is not registered.");
		}
	}
}