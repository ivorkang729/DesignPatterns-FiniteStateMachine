package fsm;

public interface IState {
	String getName();
	void setParentState(IState parentState) ;
	void addTransition(ITransition transition);
	
	void entryState(FSMContext context) ;
	void handleEvent(IEvent event, FSMContext context);
	void exitState(FSMContext context);
}
