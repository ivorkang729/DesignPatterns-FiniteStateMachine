package bot.event;

import community.Member;
import community.Role;
import community.WaterballCommunityLoginEvent;
import fsm.Event;

public class LoginEvent extends Event {
	private final static String EVENT_NAME = "login";
	private Member member;

	public LoginEvent(WaterballCommunityLoginEvent event) {
		super(EVENT_NAME);
		this.member = event.getMember();
	}
	
	public String getLoginMemberId() {
		return member.getId();
	}
	
	public Role getLoginMemberRole() {
		return member.getRole();
	}

}
