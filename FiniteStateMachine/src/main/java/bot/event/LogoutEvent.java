package bot.event;

import fsm.IEvent;
import waterballCommunity.Member;
import waterballCommunity.WaterballCommunityLogoutEvent;

public class LogoutEvent extends IEvent {
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
