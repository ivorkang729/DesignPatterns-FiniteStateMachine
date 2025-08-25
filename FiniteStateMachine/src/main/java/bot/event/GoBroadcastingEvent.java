package bot.event;

import community.Member;
import community.Role;
import community.broadcast.WaterballCommunityGoBroadcastingEvent;
import fsm.Event;

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
