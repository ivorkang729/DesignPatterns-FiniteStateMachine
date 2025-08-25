package fsm;

import java.util.HashMap;
import java.util.Map;

public class Context {
	private State currentState;
	private Map<String, State> registeredStates = new HashMap<>();
	
	public State getCurrentState() {
		return currentState;
	}
	
	public void setCurrentState(State targetState) {
		if (this.currentState != null) {
			this.currentState.exitState(this);
		}
		this.currentState = targetState;
		this.currentState.entryState(this);
	}
	
	public void sendEvent(Event event) {
		if (currentState == null) {
			throw new IllegalStateException("Current state is not set.");
		}
		currentState.handleEvent(event, this);
	}
	
	public void registerState(State state) {
		registeredStates.put(state.getName(), state);
	}

	public State getState(String name) {
		State result = registeredStates.get(name);
		if (result == null) {
			throw new IllegalArgumentException("State with name " + name + " is not registered.");
		}
		return result;
	}
}

