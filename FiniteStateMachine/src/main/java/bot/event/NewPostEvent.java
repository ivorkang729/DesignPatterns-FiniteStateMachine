package bot.event;

import java.util.List;

import community.Tag;
import community.forum.Post;
import community.forum.WaterballCommunityNewPostEvent;
import fsm.Event;

public class NewPostEvent extends Event {
	private final static String EVENT_NAME = "new-post";
	private Post post;

	public NewPostEvent(WaterballCommunityNewPostEvent event) {
		super(EVENT_NAME);
		this.post = event.getPost();
	}

	public String getPostId() {
		return post.getId();
	}

	public String getPostAuthorId() {
		return post.getAuthorId();
	}

	public String getPostContent() {
		return post.getContent();
	}

	public List<String> getPostTags() {
		return post.getTags().stream().map(Tag::getId).toList();
	}

}
