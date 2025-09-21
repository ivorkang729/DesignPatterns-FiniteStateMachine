package botBase.event;

import fsm.Event;
import waterballCommunity.Member;
import waterballCommunity.Role;
import waterballCommunity.broadcast.WaterballCommunityStopBroadcastingEvent;

public class StopBroadcastingEvent extends Event {
	private final static String EVENT_NAME = "stop-broadcasting";
	private Member speaker;

	public StopBroadcastingEvent(WaterballCommunityStopBroadcastingEvent event) {
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
