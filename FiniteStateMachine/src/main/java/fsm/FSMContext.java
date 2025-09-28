package fsm;

import java.util.HashMap;
import java.util.Map;

public class FSMContext {
	private IState currentState;
	private Map<String, IState> stateRegistry = new HashMap<>();
	
	public IState getCurrentState() {
		return currentState;
	}
	
	public void transCurrentStateTo(IState targetState) {
		if (this.currentState != null) {
			this.currentState.exitState(this);
		}
		this.currentState = targetState;
		this.currentState.entryState(this);
	}
	
	public void sendEventToCurrentState(FSMEvent event) {
		if (currentState == null) {
			throw new IllegalStateException("Current state is not set.");
		}
		currentState.handleEvent(event, this);
	}
	
	public void registerState(IState state) {
		stateRegistry.put(state.getName(), state);
	}

	public IState getState(String name) {
		IState result = stateRegistry.get(name);
		if (result == null) {
			throw new IllegalArgumentException("State with name " + name + " is not registered.");
		}
		return result;
	}
}

