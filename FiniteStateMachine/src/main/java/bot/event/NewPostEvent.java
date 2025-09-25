package bot.event;

import java.util.List;

import fsm.IEvent;
import waterballCommunity.Tag;
import waterballCommunity.forum.Post;
import waterballCommunity.forum.WaterballCommunityNewPostEvent;

public class NewPostEvent extends IEvent {
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
