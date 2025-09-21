package waterballCommunity.forum;

import java.util.ArrayList;
import java.util.List;

import waterballCommunity.Tag;

public class Comment {
    private String authorId;
    private String referencePostId;
    private String content;
    private List<Tag> tags = new ArrayList<>();

    public Comment(String authorId, String referencePostId, String content) {
        this.authorId = authorId;
        this.referencePostId = referencePostId;
        this.content = content;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getReferencePostId() {
        return referencePostId;
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
        return String.format("🤖 comment in post %s: %s %s", referencePostId, content, Tag.toString(tags));
    }
}
