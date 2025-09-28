package fsm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FSMTransition implements ITransition {
	private static final Logger logger = LogManager.getLogger(FSMTransition.class);
	
	private String name;
	private Class<? extends FSMEvent> eventClass;
	private IGuard guard;
	private IAction action;
	private Class<? extends FSMState> toStateClass;
	
	public FSMTransition(Class<? extends FSMEvent> eventClass, IGuard guard, IAction action, Class<? extends FSMState> toStateClass) {
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
	public boolean evaluate(FSMContext context, IState fromState, FSMEvent event) {
		return (this.eventClass.isInstance(event)
				&& this.guard.evaluate(context, fromState, event));
	}

	@Override
	public void trigger(FSMContext context, IState fromState, FSMEvent event) {
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