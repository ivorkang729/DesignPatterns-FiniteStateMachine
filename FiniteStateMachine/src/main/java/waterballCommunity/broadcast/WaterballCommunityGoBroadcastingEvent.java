package waterballCommunity.broadcast;

import waterballCommunity.Member;
import waterballCommunity.WaterballCommunityEvent;

public class WaterballCommunityGoBroadcastingEvent extends WaterballCommunityEvent {
	private final static String EVENT_NAME = "waterball-event-go-broadcasting";
	private Member speaker;
	
	public WaterballCommunityGoBroadcastingEvent(Member speaker) {
		super(EVENT_NAME);
		this.speaker = speaker;
	}
	
	public Member getSpeaker() {
		return speaker;
	}
	
}
