package fsm;

public class IEvent {
	private String name;
	
	public IEvent(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
