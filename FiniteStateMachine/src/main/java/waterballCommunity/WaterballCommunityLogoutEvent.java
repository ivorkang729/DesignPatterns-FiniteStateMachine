package waterballCommunity;

public class WaterballCommunityLogoutEvent extends WaterballCommunityEvent {
	private final static String EVENT_NAME = "waterball-event-logout";
	private Member member;

	public WaterballCommunityLogoutEvent(Member member) {
		super(EVENT_NAME);
		this.member = member;
	}

	public Member getMember() {
		return member;
	}

	@Override
	public String toString() {
		return "WaterballCommunityLogoutEvent{" +
				"member=" + member +
				'}';
	}

}
