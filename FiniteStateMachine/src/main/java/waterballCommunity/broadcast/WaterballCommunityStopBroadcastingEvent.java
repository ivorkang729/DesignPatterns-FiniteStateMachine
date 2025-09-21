package waterballCommunity.broadcast;

import waterballCommunity.Member;
import waterballCommunity.WaterballCommunityEvent;

public class WaterballCommunityStopBroadcastingEvent extends WaterballCommunityEvent {
	private final static String EVENT_NAME = "waterball-event-stop-broadcasting";
	private Member speaker;
	
	public WaterballCommunityStopBroadcastingEvent(Member speaker) {
		super(EVENT_NAME);
		this.speaker = speaker;
	}
	
	public Member getSpeaker() {
		return speaker;
	}
	
}
