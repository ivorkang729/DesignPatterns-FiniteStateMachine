package botBase.event;

import fsm.Event;
import waterballCommunity.Member;
import waterballCommunity.Role;
import waterballCommunity.broadcast.WaterballCommunityGoBroadcastingEvent;

public class GoBroadcastingEvent extends Event {
	private final static String EVENT_NAME = "go-broadcasting";
	private Member speaker;

	public GoBroadcastingEvent(WaterballCommunityGoBroadcastingEvent event) {
		super(EVENT_NAME);
		this.speaker = event.getSpeaker();
	}
	
	public String getSpeakerId() {
		return speaker.getId();
	}

	public Role getSpeakerRole() {
		return speaker.getRole();
	}

}
