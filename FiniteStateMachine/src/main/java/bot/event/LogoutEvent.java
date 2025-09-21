package bot.event;

import fsm.Event;
import waterballCommunity.Member;
import waterballCommunity.WaterballCommunityLogoutEvent;

public class LogoutEvent extends Event {
	private final static String EVENT_NAME = "logout";
	private Member member;

	public LogoutEvent(WaterballCommunityLogoutEvent event) {
		super(EVENT_NAME);
		this.member = event.getMember();
	}

	public String getLogoutMemberId() {
		return member.getId();
	}

}
