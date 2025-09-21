package waterballCommunity.chatroom;

import java.util.ArrayList;
import java.util.List;

import waterballCommunity.Member;
import waterballCommunity.Tag;

public class Message {
	private String authorId;
	private String content;
	private List<Tag> tags = new ArrayList<>();

	public Message(String authorId, String content) {
		this.authorId = authorId;
		this.content = content;
	}

	public Message(Member author, String content) {
		this.authorId = author.getId();
		this.content = content;
	}

	public String getAuthorId() {
		return authorId;
	}

	public String getContent() {
		return content;
	}

	public void addTag(Tag tag) {
		tags.add(tag);
	}

	public void addTag(String tagId) {
		tags.add(new Tag(tagId));
	}

	public void addTags(List<Tag> tags) {
		this.tags.addAll(tags);
	}

	public void addTagsByIds(List<String> tagIds) {
		for (String tagId : tagIds) {
			this.tags.add(new Tag(tagId));
		}
	}

	public List<Tag> getTags() {
		return tags;
	}

	@Override
	public String toString() {
		if ("botImpl".equals(authorId)) {
			return String.format("🤖: %s %s", content, Tag.toString(tags));
		} else {
			return String.format("💬 %s: %s %s", authorId, content, Tag.toString(tags));
		}
	}
}
