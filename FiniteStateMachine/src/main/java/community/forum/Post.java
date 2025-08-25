package community.forum;

import java.util.ArrayList;
import java.util.List;

import community.Tag;

public class Post {
    private String id;
    private String title;
    private String content;
    private String authorId;
    private List<Tag> tags = new ArrayList<>();

    public Post(String authorId, String title, String content) {
        this.authorId = authorId;
        this.title = title;
        this.content = content;
    }

    public Post(String id, String authorId, String title, String content, String... tagIds) {
        this.id = id;
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.addTagsByIds(List.of(tagIds));
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getTitle() {
        return title;
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

    public void addTags(String... tagIds) {
        for (String tagId : tagIds) {
            this.tags.add(new Tag(tagId));
        }
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
        return String.format("%s: 【%s】%s %s", authorId, title, content, Tag.toString(tags));
    }

}
