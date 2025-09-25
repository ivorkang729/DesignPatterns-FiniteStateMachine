package bot.event;

import fsm.IEvent;
import waterballCommunity.Member;
import waterballCommunity.Role;
import waterballCommunity.WaterballCommunityLoginEvent;

public class LoginEvent extends IEvent {
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
