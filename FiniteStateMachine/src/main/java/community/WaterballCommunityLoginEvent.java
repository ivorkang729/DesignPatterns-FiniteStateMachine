package community;

public class WaterballCommunityLoginEvent extends WaterballCommunityEvent {
	private final static String EVENT_NAME = "waterball-event-login";
	private Member member;

	public WaterballCommunityLoginEvent(Member member) {
		super(EVENT_NAME);
		this.member = member;
	}

	public Member getMember() {
		return member;
	}

}
