package community;

import java.util.List;

public class Tag {
	private String id;

	public Tag(String id) {
		if (id == null || id.isEmpty() || id.length() >= 20) {
			throw new IllegalArgumentException("Tag id cannot be null or empty or longer than 20 characters");
		}
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "@" + id;
	}

	public static String toString(List<Tag> tags) {
		//我希望tag可以有順序
		return tags.stream().map(Tag::toString)
		//.sorted()
		.reduce((a, b) -> a + ", " + b).orElse("");
	}
}
