package bot.event;

import fsm.IEvent;
import waterballCommunity.Member;
import waterballCommunity.Role;
import waterballCommunity.broadcast.WaterballCommunitySpeakEvent;

public class SpeakEvent extends IEvent {
	private final static String EVENT_NAME = "speak";
	private Member speaker;
	private String content;

	public SpeakEvent(WaterballCommunitySpeakEvent event) {
		super(EVENT_NAME);
		this.speaker = event.getSpeaker();
		this.content = event.getContent();
	}
	
	public String getSpeakerId() {
		return speaker.getId();
	}

	public Role getSpeakerRole() {
		return speaker.getRole();
	}

	public String getSpeakContent() {
		return content;
	}
}
