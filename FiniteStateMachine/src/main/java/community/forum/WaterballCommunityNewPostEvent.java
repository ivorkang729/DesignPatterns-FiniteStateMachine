package community.forum;

import community.WaterballCommunityEvent;

public class WaterballCommunityNewPostEvent extends WaterballCommunityEvent {
	private final static String EVENT_NAME = "waterball-event-new-post";
	private Post post;
	
	public WaterballCommunityNewPostEvent(Post post) {
		super(EVENT_NAME);
		this.post = post;
	}
	
	public Post getPost() {
		return post;
	}
	
}
