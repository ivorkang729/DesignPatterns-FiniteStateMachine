package fsm;

public interface State {
	String getName();
	void setParentState(State parentState) ;
	void addTransition(Transition transition);
	
	void entryState(FSMContext context) ;
	void handleEvent(Event event, FSMContext context);
	void exitState(FSMContext context);
}
