package community.broadcast;

import community.Member;
import community.WaterballCommunityEvent	;

public class WaterballCommunitySpeakEvent extends WaterballCommunityEvent {
	private final static String EVENT_NAME = "waterball-event-speak";
	private Member speaker;
	private String content;
	
	public WaterballCommunitySpeakEvent(Member speaker, String content) {
		super(EVENT_NAME);
		this.speaker = speaker;
		this.content = content;
	}
	
	public Member getSpeaker() {
		return speaker;
	}

	public String getContent() {
		return content;
	}
	
}
